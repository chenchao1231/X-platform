package com.key.win.biz.alarm.controller;

import com.key.win.biz.alarm.service.AlarmExportService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alarm/export/*")
public class AlarmExportExcelController {

  @Autowired private AlarmExportService alarmExportService;

  private HttpServletResponse getResponse(HttpServletResponse response, String fileName)
      throws UnsupportedEncodingException {
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String filename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + filename + ".xlsx");
    return response;
  }

  @GetMapping("/rt")
  public void readTime(HttpServletResponse response) throws IOException {
    getResponse(response, "实时告警导出");
    // 0 实时告警，1 确认告警，2 清除告警（历史告警）
    String status = "0";
    alarmExportService.alarmExport(response, status);
  }

  @GetMapping("/his/all")
  public void hisAll(HttpServletResponse response) throws IOException {
    getResponse(response, "历史告警导出");
    // 0 实时告警，1 确认告警，2 清除告警（历史告警）, 3.所有历史告警导出
    String status = "3";
    alarmExportService.alarmExport(response, status);
  }
  @GetMapping("/his/cfm")
  public void confirm(HttpServletResponse response) throws IOException {
    getResponse(response, "确认告警导出");
    // 0 实时告警，1 确认告警，2 清除告警（历史告警）
    String status = "1";
    alarmExportService.alarmExport(response, status);
  }

  @GetMapping("/his/cls")
  public void clear(HttpServletResponse response) throws IOException {
    getResponse(response, "清除告警导出");
    // 0 实时告警，1 确认告警，2 清除告警（历史告警）
    String status = "2";
    alarmExportService.alarmExport(response, status);
  }
}
