package biz.alarm.impl;

import cn.hutool.core.date.DateUtil;
import com.key.win.config.BeanConfig;
import com.key.win.init.D3Data;
import com.key.win.biz.core.model.QDeviceInfo;
import com.key.win.biz.core.vo.DeviceTypePropValVO;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import com.key.win.redis.topic.notify.IRedisTopicNotify;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class D3SystemRedisTopicListenerCallBack implements IRedisTopicNotify {
  @Resource private StringRedisTemplate stringRedisTemplate;

  @Resource private BeanConfig beanConfig;

  /**
   * @param stationCode 车站编号
   * @param deviceTypeCode 设备类型code
   * @return 设备编号：设备名称：[设备属性列表]
   */
  public Map<String, Map<String, List<Map<Object, Object>>>> deviceAlarmListByStaCodeAndDevTypeCode(
      String stationCode, String deviceTypeCode) {
    String kk = "ALARM:" + stationCode + ":" + deviceTypeCode + "*:";
    Set<String> keys = stringRedisTemplate.keys(kk + "*");
    List<String> collect =
        keys.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    //    设备编号：设备名称：[{设备属性列表}]
    Map<String, Map<String, List<Map<Object, Object>>>> map = new LinkedHashMap<>();
    collect.forEach(
        x -> {
          String[] arr = x.split(":");
          String deviceCode = arr[3];

          String deviceName = D3Data.getCode2nameDeviceInfoMap().get(deviceCode);
          boolean containsKey = map.containsKey(deviceCode);
          Map<Object, Object> deviceProp = stringRedisTemplate.opsForHash().entries(x);

          if (!containsKey) {
            Map<String, List<Map<Object, Object>>> deviceMap = new LinkedHashMap<>();
            List<Map<Object, Object>> devicePropList = new ArrayList<>();
            devicePropList.add(deviceProp);
            deviceMap.put(deviceName, devicePropList);
            map.put(deviceCode, deviceMap);
          } else {
            map.get(deviceCode).get(deviceName).add(deviceProp);
          }
        });
    return map;
  }

  /**
   * 设备告警列表（只有设备编号和设备名称）
   *
   * @param stationCode 车站code
   * @param deviceTypeCode 设备类型code
   * @return 设备code：设备名称
   */
  public Map<String, String> deviceAlarmListOverview(String stationCode, String deviceTypeCode) {
    String kk = "ALARM:" + stationCode + ":" + deviceTypeCode + "*:";
    Set<String> keys = stringRedisTemplate.keys(kk + "*");
    List<String> collect =
        keys.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    //    设备编号：设备名称
    Map<String, String> map = new LinkedHashMap<>();
    collect.forEach(
        x -> {
          String[] arr = x.split(":");
          String deviceCode = arr[3];
          String deviceName = D3Data.getCode2nameDeviceInfoMap().get(deviceCode);
          map.put(deviceCode, deviceName);
        });
    return map;
  }

  /**
   * 所有设备类型的设备告警列表（只有设备编号和设备名称）
   *
   * @param stationCode 车站code
   * @return 设备code：设备名称
   */
  public Map<String, Map<String, String>> deviceAlarmListOverview(String stationCode) {
    String kk = "ALARM:" + stationCode + ":";
    Set<String> keys = stringRedisTemplate.keys(kk + "*");
    List<String> collect =
        keys.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());

    //    设备类型：设备编号：设备名称
    Map<String, Map<String, String>> map = new HashMap<>();

    collect.forEach(
        x -> {
          String[] arr = x.split(":");
          String deviceTypeCode = arr[2];
          String deviceCode = arr[3];
          String deviceName = D3Data.getCode2nameDeviceInfoMap().get(deviceCode);

          /** 设备类型的基础类 比如：TVM下的 TVM01 TVM02,此时获取的是TVM基础属性 */
          String deviceTypeCodeBase = beanConfig.getMapping().get(deviceTypeCode);
          if (!StringUtils.isBlank(deviceTypeCodeBase)) {
            // 如果没有映射到的基础属性，那么就使用自身的属性即可
            deviceTypeCode = deviceTypeCodeBase;
          }
          Map<String, String> deviceTypeMap = null;
          boolean hasDeviceTypeCode = map.containsKey(deviceTypeCode);
          if (hasDeviceTypeCode) {
            map.get(deviceTypeCode).put(deviceCode, deviceName);
          } else {
            deviceTypeMap = new LinkedHashMap<>();
            deviceTypeMap.put(deviceCode, deviceName);
            map.put(deviceTypeCode, deviceTypeMap);
          }
        });
    return map;
  }

  public Map<String, Map<String, List<Map<Object, Object>>>> deviceAlarmDetails(
      String stationCode, String deviceTypeCode, String deviceCode) {

    String kk = "ALARM:" + stationCode + ":" + deviceTypeCode + "*:" + deviceCode + ":";
    Set<String> keys = stringRedisTemplate.keys(kk + "*");
    //    设备编号：设备名称：[设备属性列表]
    Map<String, Map<String, List<Map<Object, Object>>>> map = new HashMap<>();
    keys.forEach(
        x -> {
          String deviceName = D3Data.getCode2nameDeviceInfoMap().get(deviceCode);
          boolean containsKey = map.containsKey(deviceCode);
          Map<Object, Object> deviceProp = stringRedisTemplate.opsForHash().entries(x);

          if (!containsKey) {
            Map<String, List<Map<Object, Object>>> deviceMap = new HashMap<>();
            List<Map<Object, Object>> devicePropList = new ArrayList<>();
            devicePropList.add(deviceProp);
            deviceMap.put(deviceName, devicePropList);
            map.put(deviceCode, deviceMap);
          } else {
            map.get(deviceCode).get(deviceName).add(deviceProp);
          }
        });
    return map;
  }

  @Override
  public void callback(String message) {
    String key = message;
    String value = stringRedisTemplate.opsForValue().get(key);

    String[] keySplit = key.split(":");
    String deviceTypeCode = keySplit[3];
    String deviceCodeWithAttr = keySplit[4];
    String[] _s = deviceCodeWithAttr.split("\\.");
    String propCode = _s[_s.length - 1];
    _s = Arrays.copyOfRange(_s, 0, _s.length - 1);
    String deviceCode = String.join(".", _s);

    String _k = deviceTypeCode + ":" + propCode + ":" + value;

    DeviceTypePropValVO vo = D3Data.getDeviceTypePropValVOMap().get(_k);
    QDeviceInfo device = D3Data.getDeviceCodeKeyDeviceInfoMap().get(deviceCode);

    String deviceName = device.getDeviceName();
    String dbDeviceCode = device.getDeviceCode();
    if (vo == null) {
      log.error("{}${}${}", deviceName, dbDeviceCode, _k);
      return;
    }
    String dbPropCode = vo.getPropCode();
    String propCnName = vo.getPropCnName();

    String space = device.getSpace();
    String position = device.getPosition();
    String alarmLevel = vo.getAlarmLevel();
    String alarmContent = vo.getValueDesc();
    String stationCode = device.getStationCode();
    String scadaNode = device.getScadaNode();

    // 告警设备的redis——key
    String kk =
        "ALARM:" + stationCode + ":" + deviceTypeCode + ":" + dbDeviceCode + ":" + dbPropCode;
    // 设备编号
    String kk2 = stationCode + ":" + deviceTypeCode + ":" + dbDeviceCode;
    // 从缓存中尝试取出当前告警点的存储的属性map
    Map<Object, Object> _map = stringRedisTemplate.opsForHash().entries(kk);

    if (vo.isBreakdown()) {
      if (_map.isEmpty()) {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("deviceName", deviceName);
        m.put("deviceCode", dbDeviceCode);
        m.put("propCode", dbPropCode);
        m.put("propCnName", propCnName);
        m.put("space", space);
        m.put("position", position);
        m.put("alarmLevel", alarmLevel);
        m.put("alarmContent", alarmContent);
        m.put("scadaNode", scadaNode);
        String now = DateUtil.now();
        m.put("alarmTime", now);
        stringRedisTemplate.opsForHash().putAll(kk, m);
        log.info(
            "车站：{}，设备名称：{}，设备编号：{}，属性CODE：{}，属性名称：{}，告警内容：{}，告警等级：{}，告警时间：{}",
            stationCode,
            deviceName,
            deviceCode,
            dbPropCode,
            propCnName,
            alarmContent,
            alarmLevel,
            now);
      } else {
        log.info("设备名称：{}，设备编号：{}，属性CODE：{} @@ 告警+1", deviceName, deviceCode, dbPropCode);
      }
    } else {
      if (!_map.isEmpty()) {
        log.info(
            "车站：{}-设备名称：{}-设备编号：{}-属性CODE：{}（{}）恢复正常，删除当前告警点。",
            stationCode,
            deviceName,
            deviceCode,
            dbPropCode,
            propCnName,
            alarmContent);
        stringRedisTemplate.opsForHash().putAll(kk, new LinkedHashMap<Object, Object>());
      }
    }
  }
}
