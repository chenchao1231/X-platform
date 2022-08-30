package cn.exrick.xboot.modules.indoor.mqtt.messager;

import lombok.Getter;
import lombok.Setter;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月22日-13:56:20
 */
public abstract class AbstractPublishMessage implements PublishMessage {

    @Getter
    @Setter
    private String deviceId;

    @Setter
    private String value;

    @Setter
    private String antennaId;
    
    @Setter
    private String probeId;
}
