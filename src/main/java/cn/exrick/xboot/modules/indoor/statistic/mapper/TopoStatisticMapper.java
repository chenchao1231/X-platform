package cn.exrick.xboot.modules.indoor.statistic.mapper;

import cn.exrick.xboot.modules.indoor.statistic.vo.AllStationStatisticVO;
import cn.exrick.xboot.modules.indoor.statistic.vo.StatisticVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenchao
 */
@Mapper
public interface TopoStatisticMapper {


	/**
	 * 按车站统计器件个数
	 * @param stationId
	 * @return
	 */
	public List<StatisticVO> topoStatistic(@Param("stationId") String stationId);
	
	/**
	 * 资产统计资源信息
	 * @return
	 */
	public List<AllStationStatisticVO> allStationStatistic();

}
