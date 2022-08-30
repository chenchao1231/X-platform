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
@Table(name = "t_biz_indoor_metro_line")
@TableName("t_biz_indoor_metro_line")
public class MetroLine extends XbootBaseEntity {

    private String lineId;

    private String lineName;

    private Boolean lineOperationStat = Boolean.TRUE;
}
