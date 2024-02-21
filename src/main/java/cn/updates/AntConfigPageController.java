package com.key.win.biz.alarm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.key.win.biz.baseInfo.model.BelongUnit;
import com.key.win.biz.baseInfo.model.SiteInfo;
import com.key.win.biz.baseInfo.service.BaseLineService;
import com.key.win.biz.baseInfo.service.BelongUnitService;
import com.key.win.biz.baseInfo.service.SiteInfoService;
import com.key.win.biz.topo.model.TopoNodes;
import com.key.win.biz.topo.service.TopoService;
import com.key.win.common.web.Result;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/antConfig/*")
public class AntConfigPageController {

  @Resource private SiteInfoService siteInfoService;
  @Resource private BelongUnitService hostService;
  @Resource private TopoService topoService;
  @Resource private BaseLineService baseLineService;

  @PostMapping("/sfStationListApi")
  public Result<List<SiteInfo>> sfStationListApi() {
    List<SiteInfo> siteInfoList = new ArrayList<>();
    LambdaQueryWrapper<SiteInfo> lqwSite = new LambdaQueryWrapper<>();
    List<String> operationLineCodeList = baseLineService.operationLineList();
    if (operationLineCodeList.size() != 0) {
      lqwSite.in(SiteInfo::getLineCode, operationLineCodeList);
      lqwSite.orderByAsc(SiteInfo::getStationCode);
      siteInfoList = siteInfoService.list(lqwSite);
    }
    return Result.succeed(siteInfoList, "室分站点数据加载完毕");
  }

  @PostMapping("/sfHostListApi/{stationCode}")
  public Result<List<BelongUnit>> sfHostListApi(@PathVariable("stationCode") String stationCode) {
    LambdaQueryWrapper<BelongUnit> lqwHost = new LambdaQueryWrapper<>();
    lqwHost.orderByAsc(BelongUnit::getHostNum);
    lqwHost.eq(BelongUnit::getStationId, stationCode);
    List<BelongUnit> hostList = hostService.list(lqwHost);
    return Result.succeed(hostList, "监控主机加载完毕");
  }

  @PostMapping("/sfAntListApi/{hostNumber}")
  public Result<List<TopoNodes>> sfAntListApi(@PathVariable("hostNumber") String hostNumber) {
    List<TopoNodes> antNodes = topoService.getAntNodesByHostNum(hostNumber, false);
    return Result.succeed(antNodes, "添加列表加载完毕");
  }

  @PostMapping("/antExecute")
  public Result<Boolean> antEnable(@RequestBody TopoNodes antNode) {
    Assert.notNull(antNode);
    boolean flag = topoService.antFlagExecute(antNode);
    return Result.succeed(flag, "天线配置更新完毕!");
  }
}
