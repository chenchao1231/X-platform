package cn.exrick.xboot.modules.indoor.dbconfig.enums;

/**
 * @author chenchao
 */

public enum MqDbConfigStatus {
	/**
	 * 启用
	 */
	ENABLED(1,"启用"),

	/**
	 * 禁用
	 */
	DISABLED(0,"禁用"); 
	
	private int statusCode;
	private String statusDesc;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	private MqDbConfigStatus(int statusCode, String statusDesc) {
		this.statusCode = statusCode;
		this.statusDesc = statusDesc;
	}
}
