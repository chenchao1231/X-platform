package biz.alarm.job;//package com.key.win.biz.alarm.job;
//
//import cn.hutool.core.map.MapUtil;
//import com.alibaba.fastjson.JSON;
//import com.key.win.biz.alarm.impl.D3SystemRedisTopicListenerCallBack;
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//
//import com.key.win.ws.WebSocketServer;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@Component
//public class AlarmJob {
//
//  @Resource private D3SystemRedisTopicListenerCallBack d3SystemRedisTopicListenerCallBack;
//
//  @Resource private WebSocketServer webSocketServer;
//
//  /**
//   * 设备告警列表概况
//   *
//   * @return 设备编号：设备名称
//   */
//  @XxlJob(value = "deviceAlarmListOverViewJob")
//  public void deviceAlarmListOverviewByStaCodeAndDevTypeCode() {
//      WebSocketServer.getOnlineStation()
//        .forEach(
//            x -> {
//              String stationCode = x;
//              Map<String, Map<String, String>> result =
//                  d3SystemRedisTopicListenerCallBack.deviceAlarmListOverview(stationCode);
//              String jsonResult = JSON.toJSONString(result);
//              WebSocketServer.sendMessage(jsonResult, x);
//            });
//  }
//}
