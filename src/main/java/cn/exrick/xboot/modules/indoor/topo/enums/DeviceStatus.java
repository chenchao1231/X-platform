package cn.exrick.xboot.modules.indoor.topo.enums;

public enum DeviceStatus {
	
	green("绿色",1),
	gray("灰色",3),
	yellow("黄色",2),
	red("红色",4);
	
	
	private String stateName;
	
	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	private DeviceStatus(String stateName, int state) {
		this.stateName = stateName;
		this.state = state;
	}

}
