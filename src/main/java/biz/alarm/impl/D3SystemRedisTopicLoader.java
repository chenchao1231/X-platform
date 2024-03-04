package biz.alarm.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.key.win.biz.core.model.QDeviceTypeProperty;
import com.key.win.biz.core.service.IDeviceTypePropertyService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.key.win.redis.topic.keys.IRedisTopicLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class D3SystemRedisTopicLoader implements IRedisTopicLoader {

  @Resource private IDeviceTypePropertyService deviceTypePropertyService;

  /**
   * 从数据库或其他地方加载所有的topic字符串信息
   *
   * @return
   */
  @Override
  public List<String> loadTopicKeys() {
    LambdaQueryWrapper<QDeviceTypeProperty> lqw = new LambdaQueryWrapper<>();
    lqw.eq(QDeviceTypeProperty::isAlarmProp, true);
    lqw.eq(QDeviceTypeProperty::isEnable, true);
    List<QDeviceTypeProperty> deviceTypePropertyList = this.deviceTypePropertyService.list(lqw);

    ArrayList<String> topicStringList = new ArrayList<>();
    String key_prefix = "__keyspace@*__:d3data:*:QR:";
    deviceTypePropertyList.forEach(
        x -> {
          String k = x.getDeviceTypeCode() + ":*." + x.getPropCode() ;
          k = key_prefix + k;
          log.info(k);
          topicStringList.add(k);
        });
    return topicStringList;
  }
}
