package biz.alarm.controller;

import cn.hutool.core.map.MapUtil;
import com.key.win.biz.alarm.impl.D3SystemRedisTopicListenerCallBack;
import com.key.win.common.ret.Result;
import com.key.win.config.BeanConfig;
import com.key.win.init.D3Data;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/d3/alarm/*")
public class AlarmController {

  @Resource private D3SystemRedisTopicListenerCallBack d3SystemRedisTopicListenerCallBack;

  @Resource private BeanConfig beanConfig;

  @GetMapping("/deviceTypes")
  private Result<Map<String, String>> getDeviceTypeMaps() {
    return Result.succeed(beanConfig.getTypes(), "设备类型获取成功!");
  }

  @GetMapping("/mapping2")
  private Result<Object> getMapping() {
    return Result.succeed(beanConfig.getMapping(), "设备映射类型获取成功!");
  }

  /**
   * @param param {"stationCode":"x","deviceTypeCode":"x"}
   * @return
   */
  @PostMapping("/list")
  public Result<Object> deviceAlarmListByStaCodeAndDeviceTypeCode(
      @RequestBody Map<String, String> param) {

    String stationCode = MapUtil.getStr(param, "stationCode");
    String deviceTypeCode = MapUtil.getStr(param, "deviceTypeCode");
    stationCode = D3Data.getStaId2StaCodeMap().get(stationCode);
    Map<String, Map<String, List<Map<Object, Object>>>> res =
        d3SystemRedisTopicListenerCallBack.deviceAlarmListByStaCodeAndDevTypeCode(
            stationCode, deviceTypeCode);
    return Result.succeed(res, "车站告警列表获取完毕!");
  }

  /**
   * 设备告警列表概况
   *
   * @param param {"stationCode":"x","deviceTypeCode":"x"}
   * @return 设备编号：设备名称
   */
  @PostMapping("/list/overview")
  public Result<Object> deviceAlarmListOverviewByStaCodeAndDevTypeCode(
      @RequestBody Map<String, String> param) {
    String stationCode = MapUtil.getStr(param, "stationCode");
    String deviceTypeCode = MapUtil.getStr(param, "deviceTypeCode");
    stationCode = D3Data.getStaId2StaCodeMap().get(stationCode);
    Map<String, String> res =
        d3SystemRedisTopicListenerCallBack.deviceAlarmListOverview(stationCode, deviceTypeCode);
    return Result.succeed(res, "车站告警概况信息获取完毕!");
  }

  /**
   * 获取具体设备的告警详情
   *
   * @param param {"stationCode":"x","deviceTypeCode":"x","deviceCode":"x"}
   * @return 设备编号：设备名称：[设备属性列表]
   */
  @PostMapping("/details")
  public Result<Object> deviceAlarmDetailsByStaCodeAndDeviceTypeCodeAndDeviceCode(
      @RequestBody Map<String, String> param) {

    String stationCode = MapUtil.getStr(param, "stationCode");
    String deviceTypeCode = MapUtil.getStr(param, "deviceTypeCode");
    String deviceCode = MapUtil.getStr(param, "deviceCode");
    stationCode = D3Data.getStaId2StaCodeMap().get(stationCode);
    Map<String, Map<String, List<Map<Object, Object>>>> res =
        d3SystemRedisTopicListenerCallBack.deviceAlarmDetails(
            stationCode, deviceTypeCode, deviceCode);
    return Result.succeed(res, "设备详情获取完毕!");
  }

  /**
   * 全站概况信息
   *
   * @param param {"stationCode":"数字编号"}
   * @return 设备编号：设备名称：[设备属性列表]
   */
  @PostMapping("/alarms")
  public Result<Object> stationAlarmInfo(@RequestBody Map<String, String> param) {
    //从请求参数中获取stationCode 传入的 数字编号
    String stationCode = MapUtil.getStr(param, "stationCode");

    // 将数字编号转换成英文站名称
    stationCode = D3Data.getStaId2StaCodeMap().get(stationCode);
    //将英文站名称，传入后台接口
    Map<String, Map<String, String>> res =
        d3SystemRedisTopicListenerCallBack.deviceAlarmListOverview(stationCode);
    return Result.succeed(res, "全站设备告警信息概况获取完毕!");
  }
}
