package cn.exrick.xboot.modules.indoor.topo.enums;

public enum DeviceType {
	
	//监控主机
	HOST("监控主机", 1),
	
	//合路器
	HLQ("合路器", 3),
	
	//耦合器
	OHQ_5db("耦合器5db", 11),
	OHQ_6db("耦合器6db", 12),
	OHQ_7db("耦合器7db", 13),
	OHQ_10db("耦合器10db", 14),
	OHQ_15db("耦合器15db", 15),
	OHQ_20db("耦合器20db", 16),
	OHQ_25db("耦合器25db", 17),
	OHQ_30db("耦合器30db", 18),
	OHQ_35db("耦合器35db", 19),
	OHQ_40db("耦合器40db", 110),
	
	//负载
	FZ("负载", 4000),
	
	//功分器
	GFQ_2db("二功分", 21),
	GFQ_3db("三功分", 22),
	GFQ_4db("四功分", 23),
	
	//天线
	TX_DXXD("定向吸顶天线", 10),
	TX_DSZQ("对数周期天线", 1001),
	TX_BG("壁挂天线", 1002),
	TX_QXXD("全向吸顶天线", 1003),
	TX_BZ("板状天线", 1004),
	TX_QT("其他天线", 1005);
	
	
	String deviceTypeName;
	
	int deviceTypeCode;

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public int getDeviceTypeCode() {
		return deviceTypeCode;
	}

	public void setDeviceTypeCode(int deviceTypeCode) {
		this.deviceTypeCode = deviceTypeCode;
	}

	private DeviceType(String deviceTypeName, int deviceTypeCode) {
		this.deviceTypeName = deviceTypeName;
		this.deviceTypeCode = deviceTypeCode;
	}
	
	
	
}
