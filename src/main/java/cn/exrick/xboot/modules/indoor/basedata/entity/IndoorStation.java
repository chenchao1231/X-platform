package cn.exrick.xboot.modules.indoor.basedata.entity;


import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chenchao
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_biz_indoor_station")
@TableName("t_biz_indoor_station")
@ApiModel(value = "室分站点信息")
public class IndoorStation extends XbootBaseEntity {

    private String stationId;

    private String stationName;

    private String lineId;

    private String lineName;

    private String remark;
}
