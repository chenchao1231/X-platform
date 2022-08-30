package cn.exrick.xboot.modules.indoor.basedata.service.impl;

import cn.exrick.xboot.modules.indoor.basedata.entity.MetroStation;
import cn.exrick.xboot.modules.indoor.basedata.mapper.MetroStationMapper;
import cn.exrick.xboot.modules.indoor.basedata.service.IMetroStationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author chenchao
 */
@Service
public class MetroStationServiceImpl extends ServiceImpl<MetroStationMapper, MetroStation> implements IMetroStationService {

    @Override
    public List<Map<String,Object>> listStationIdAndStationNameAndSeq(String lineId) {
        return baseMapper.listStationIdAndStationNameAndSeq(lineId);
    }
}
