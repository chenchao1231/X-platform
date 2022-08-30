package cn.exrick.xboot.modules.indoor.basedata.service.impl;

import cn.exrick.xboot.modules.indoor.basedata.entity.MetroLine;
import cn.exrick.xboot.modules.indoor.basedata.entity.MetroStation;
import cn.exrick.xboot.modules.indoor.basedata.mapper.MetroLineMapper;
import cn.exrick.xboot.modules.indoor.basedata.service.IMetroLineService;
import cn.exrick.xboot.modules.indoor.basedata.service.IMetroStationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chenchao
 */
@Service
public class MetroLineServiceImpl extends ServiceImpl<MetroLineMapper, MetroLine> implements IMetroLineService {

    @Autowired
    private IMetroStationService metroStationService;

    @Override
    public List<MetroStation> getStationsByLineId(String lineId) {
        LambdaQueryWrapper<MetroStation> lqwStation = new LambdaQueryWrapper<>();
        lqwStation.eq(MetroStation::getLineId,lineId);
        List<MetroStation> stationList = metroStationService.list(lqwStation);
        return stationList;
    }

    @Override
    public List<Map<String,Object>> queryLineStationTree() {
        List<Map<String,Object>> nodes = new ArrayList<>();

        LambdaQueryWrapper<MetroLine> lqwLine = new LambdaQueryWrapper<>();
        lqwLine.eq(MetroLine::getLineOperationStat,true);
        lqwLine.orderByAsc(MetroLine::getLineId);
        List<MetroLine> metroLines = baseMapper.selectList(lqwLine);
        for(MetroLine line: metroLines) {
            Map<String,Object> node = new LinkedHashMap<>();
            List<Map<String, Object>> stationList = metroStationService.listStationIdAndStationNameAndSeq(line.getLineId());
            node.put("lineId", line.getLineId());
            node.put("lineName" , line.getLineName());
            node.put("children" , stationList);
            nodes.add(node);
        }
        return nodes;
    }

}
