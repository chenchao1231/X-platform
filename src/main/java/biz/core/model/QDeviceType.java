package biz.core.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName("q_device_type")
public class QDeviceType implements Serializable {
  private String id;
  private String typeCode;
  private String typeCnName;
  private String pid;
  private boolean isDir;
  private String attr1;
  private String attr2;
  private String attr3;
  private String attr4;
  private String attr5;
}
