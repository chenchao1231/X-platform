package cn.exrick.xboot.modules.indoor.mqtt.messager;

/**
 * description
 *
 * @author 蔺扬
 * createTime 2022年7月20日-17:48:13
 */
public interface CommonMqttMessage extends CommonMessage{
    default String getProtocol() {
        return "MQTT";
    }
}
