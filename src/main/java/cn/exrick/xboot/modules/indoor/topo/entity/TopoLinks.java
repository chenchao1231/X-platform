package cn.exrick.xboot.modules.indoor.topo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "t_biz_indoor_topo_links")
@TableName("sf_topo_links")
public class TopoLinks {
	
	@Id
	@TableId
	private String id;
	
	private String hostNumber;
	
	private int linktype ;
	
	private int srcId;
	
	private int destId;

}
