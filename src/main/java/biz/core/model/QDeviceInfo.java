package biz.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@TableName("q_device_info")
public class QDeviceInfo {

  @TableId(type = IdType.ASSIGN_UUID)
  private String id;

  private String deviceCode;
  private String deviceName;

  private String position;
  private String space;

  private String deviceTypeCode;

  private String lineCode;
  private String stationCode;

  private String scadaNode;
}
