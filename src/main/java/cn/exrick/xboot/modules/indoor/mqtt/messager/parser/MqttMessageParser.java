package cn.exrick.xboot.modules.indoor.mqtt.messager.parser;

import com.alibaba.fastjson2.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author chenchao
 */
public interface MqttMessageParser {

    /**
     * 解析
     * @param topic 主题
     * @param message MQ传入的对象
     * @return 解析后的信息
     */
    JSONObject parse(String topic, MqttMessage message);
}
