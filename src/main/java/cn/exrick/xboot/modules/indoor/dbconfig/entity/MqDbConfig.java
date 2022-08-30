package cn.exrick.xboot.modules.indoor.dbconfig.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chenchao
 */
@Data
@Entity
@TableName("t_biz_indoor_db_config_mqtt")
@Table(name="t_biz_indoor_db_config_mqtt")
public class MqDbConfig extends XbootBaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String url ;
	private int port;
	private int enable;
}
