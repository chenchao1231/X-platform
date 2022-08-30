package cn.exrick.xboot.modules.indoor.statistic.controller;

import cn.exrick.xboot.modules.indoor.statistic.service.StatisticService;
import cn.exrick.xboot.modules.indoor.statistic.vo.AllStationStatisticVO;
import cn.exrick.xboot.modules.indoor.statistic.vo.StatisticVO;
import cn.exrick.xboot.support.retn.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author chenchao
 */
@RestController
@RequestMapping("/xboot/statistic/*")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @PostMapping("/byStation/{stationId}")
    public Result<List<StatisticVO>> statisticByStation(@PathVariable("stationId") String stationId){
        List<StatisticVO> statisticData = statisticService.statisticByStation(stationId);
        return Result.succeed(statisticData, "数据获取完毕");
    }

    @PostMapping("/all")
    public Result<Map<String, Map<String, List<AllStationStatisticVO>>>> allStationStatistic(){
        Map<String, Map<String, List<AllStationStatisticVO>>> data = statisticService.allStationStatistic();
        return Result.succeed(data,"统计数据获取完毕");
    }
}
