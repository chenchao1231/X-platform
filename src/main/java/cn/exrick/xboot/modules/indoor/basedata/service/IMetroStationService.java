package cn.exrick.xboot.modules.indoor.basedata.service;

import cn.exrick.xboot.modules.indoor.basedata.entity.MetroStation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author chenchao
 */
public interface IMetroStationService extends IService<MetroStation> {

    /**
     * 查询车站ID & 车站名称 & 排序 ，其他非字段不查询
     * @param lineId 线路编号
     * @return 车站列表结合
     */
    public List<Map<String,Object>> listStationIdAndStationNameAndSeq(String lineId) ;
}
