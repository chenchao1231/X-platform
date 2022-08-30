package cn.exrick.xboot.modules.indoor.basedata.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Chenchao
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_biz_indoor_monitoring_host")
@TableName("t_biz_indoor_monitoring_host")
@ApiModel(value = "监测主机")
public class MonitoringHost extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 室分站点ID
     */
    private String pid;
    /**
     * 监控主机编号
     */
    private String hostNumber;

    private String lineId;

    private String lineName;

    private String stationId;

    private String stationName;

    private String ip;

    private String port;

    /**
     * 安装位置
     */
    private String installPosition;


}