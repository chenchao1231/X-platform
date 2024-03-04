package biz.core.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName("q_device_property")
public class QDeviceTypeProperty implements Serializable {

  private String id;
  private String deviceTypeCode;
  private String deviceTypeCnName;
  private String propCode;
  private String propCnName;
  @TableField("is_alarm_prop")
  private boolean alarmProp;
  private boolean enable;
}
