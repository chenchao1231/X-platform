package biz.core.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName("device_type_prop_val_vo")
public class DeviceTypePropValVO implements Serializable {

  private String id;
  private String deviceTypeCode;
  private String deviceTypeCnName;
  private String propCode;
  private String propCnName;

  @TableField("is_alarm_prop")
  private boolean alarmProp;

  private boolean flag;
  private boolean enable;

  /** 故障 true 故障 false 正常 */
  private boolean breakdown;

  private String value;
  private String valueDesc;
  private String alarmLevel;
}
