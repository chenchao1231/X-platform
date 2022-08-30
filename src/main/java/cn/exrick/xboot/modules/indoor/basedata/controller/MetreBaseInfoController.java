package cn.exrick.xboot.modules.indoor.basedata.controller;

import cn.exrick.xboot.common.redis.RedisTemplateHelper;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.indoor.basedata.entity.MetroLine;
import cn.exrick.xboot.modules.indoor.basedata.entity.MetroStation;
import cn.exrick.xboot.modules.indoor.basedata.service.IMetroLineService;
import cn.exrick.xboot.modules.indoor.basedata.service.IMetroStationService;
import cn.exrick.xboot.modules.indoor.support.CacheKeyStore;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author chenchao
 */
@RestController
@RequestMapping("/indoor/basedata/lineStation/*")
public class MetreBaseInfoController {

    @Autowired
    private IMetroLineService metroLineService;
    @Autowired
    private IMetroStationService metroStationService;
    @Autowired
    private RedisTemplateHelper redisTemplateHelper;
    private Object lockObj = new Object();


    @ApiOperation(value = "查询所有的线路信息")
    @PostMapping("/allLines")
    public Result<List<MetroLine>> listLines(){
        List<MetroLine> lineList = metroLineService.list();
        return ResultUtil.data(lineList,"获取线路列表信息完毕");
    }

    @ApiOperation(value = "根据线路编号查询所有的车站信息")
    @PostMapping("/stationListByLineId/{lineId}")
    public Result<List<MetroStation>> listStationListByLineId(@PathVariable String lineId){
        List<MetroStation> stations = metroLineService.getStationsByLineId(lineId);
        return ResultUtil.data(stations,"获取站点信息完毕");
    }

    @ApiOperation(value = "返回线路和站点的树结构")
    @PostMapping("/tree")
    public Object lineStationTree(){
        if(redisTemplateHelper.hasKey(CacheKeyStore.LINE_STATION_TREE_CACHE_KEY)){
            String treeMapString = redisTemplateHelper.get(CacheKeyStore.LINE_STATION_TREE_CACHE_KEY);
            return JSONArray.parseArray(treeMapString);
        }
        synchronized (lockObj) {
            if(redisTemplateHelper.hasKey(CacheKeyStore.LINE_STATION_TREE_CACHE_KEY)){
                String treeMapString = redisTemplateHelper.get(CacheKeyStore.LINE_STATION_TREE_CACHE_KEY);
                return ResultUtil.data(treeMapString);
            }
            List<Map<String, Object>> treeNodes = metroLineService.queryLineStationTree();
            String result = JSON.toJSONString(treeNodes);
            redisTemplateHelper.set(CacheKeyStore.LINE_STATION_TREE_CACHE_KEY , result ,30 , TimeUnit.SECONDS);
            return treeNodes;
        }
    }

}
