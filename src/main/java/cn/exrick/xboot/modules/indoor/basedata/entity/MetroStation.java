package cn.exrick.xboot.modules.indoor.basedata.entity;

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
@Table(name = "t_biz_indoor_metro_station")
@TableName("t_biz_indoor_metro_station")
public class MetroStation extends XbootBaseEntity {

    private String stationId;

    private String stationName;

    private String lineId;

    private String lineName;

    private String seq;

    private String stationType;

}
