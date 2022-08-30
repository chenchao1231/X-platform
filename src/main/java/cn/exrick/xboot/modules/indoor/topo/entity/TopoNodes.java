package cn.exrick.xboot.modules.indoor.topo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_biz_indoor_topo_node")
@Entity(name = "t_biz_indoor_topo_node")
public class TopoNodes {
	
	@Id
	@TableId
	private String id;
	
	private String hostNumber;
	
	private int nodeId;
	
	private int type;
	
	private int x;
	
	private int y;
	
	private String name;
	
	private double loss;
	
	private String rfidlabel;
	
	private int state;
	
	private String lineId;
	
	private String lineName;
	
	private String stationId;
	
	private String stationName;

}
