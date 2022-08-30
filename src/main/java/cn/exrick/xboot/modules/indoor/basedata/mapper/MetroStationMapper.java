package cn.exrick.xboot.modules.indoor.basedata.mapper;

import cn.exrick.xboot.modules.indoor.basedata.entity.MetroStation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author chenchao
 */
@Mapper
public interface MetroStationMapper extends BaseMapper<MetroStation> {

    /**
     * 查询车站ID & 车站名称 & 排序 ，其他非字段不查询
     * @param lineId 线路编号
     * @return 车站列表结合
     */
    @Select("select station_id,station_name,seq from t_biz_indoor_metro_station station where 1=1 and del_flag = 0 and station.line_id = #{lineId} order by seq asc")
    public List<Map<String,Object>> listStationIdAndStationNameAndSeq(String lineId);
}
