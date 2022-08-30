package cn.exrick.xboot.modules.indoor.mqtt.messager;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * description
 *
 * @author 蔺扬
 * createTime 2022年7月18日-11:07:16
 */
@Getter
@Setter
@Builder
public class SubscribeMessageBody {

    private String topic;

    private String deviceId;

    private String messageType;

    private String functionCode;

    private String dataLength;

    private String result;

    private String data;
}
