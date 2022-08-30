package cn.exrick.xboot.modules.indoor.statistic.vo;

import lombok.Data;

@Data
public class AllStationStatisticVO {
	
	private String lineId;
	
	private String lineName;
	
	private String stationId;
	
	private String stationName;
	
	private int typeId;
	
	private String typeName;
	
	private int count;

}
