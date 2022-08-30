package cn.exrick.xboot.modules.indoor.topo.vo;

import lombok.Data;

@Data
public class OTopoNodes{
	
	private int id;
	
	private int type;
	
	private int x;
	
	private int y;
	
	private String name;
	
	private double loss;
	
	private String rfidlabel;
	
}