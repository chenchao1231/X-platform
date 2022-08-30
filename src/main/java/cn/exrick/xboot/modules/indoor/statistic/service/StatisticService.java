package cn.exrick.xboot.modules.indoor.statistic.service;

import cn.exrick.xboot.modules.indoor.statistic.mapper.TopoStatisticMapper;
import cn.exrick.xboot.modules.indoor.statistic.vo.AllStationStatisticVO;
import cn.exrick.xboot.modules.indoor.statistic.vo.StatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticService {

	@Autowired
	private TopoStatisticMapper topoStatisticMapper;

	public Map<String, Map<String, List<AllStationStatisticVO>>> allStationStatistic() {
		List<AllStationStatisticVO> allData = topoStatisticMapper.allStationStatistic();
		Map<String, Map<String, List<AllStationStatisticVO>>> collect = allData.stream().collect(Collectors.groupingBy(
				AllStationStatisticVO::getLineId, LinkedHashMap::new,
				Collectors.groupingBy(AllStationStatisticVO::getStationId, LinkedHashMap::new, Collectors.toList())));
		return collect;
	}


	/**
	 * 根据车站统计资产信息
	 * @param stationId
	 */
	public List<StatisticVO> statisticByStation(String stationId) {
		List<StatisticVO> topoStatistic = topoStatisticMapper.topoStatistic(stationId);
		return topoStatistic;
	}

}
