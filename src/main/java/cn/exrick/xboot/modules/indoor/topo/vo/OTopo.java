package cn.exrick.xboot.modules.indoor.topo.vo;

import java.util.List;

import lombok.Data;

@Data
public class OTopo {
	
	private String hostNum;
	
	private String pid;
	
	private List<OTopoNodes> nodes;

	private List<OTopoLinks> links;
	
	private int translateX = 50;
	
	private int translateY = 50;
}




