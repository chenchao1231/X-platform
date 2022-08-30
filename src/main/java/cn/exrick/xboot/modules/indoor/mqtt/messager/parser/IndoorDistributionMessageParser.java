package cn.exrick.xboot.modules.indoor.mqtt.messager.parser;

import cn.exrick.xboot.modules.indoor.mqtt.messager.manager.IndoorDistributionMessageManager;
import cn.exrick.xboot.modules.indoor.mqtt.utils.ByteUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月13日-17:50:41
 */

@Slf4j
@Component
public class IndoorDistributionMessageParser implements MqttMessageParser {

    private static final String SYSTEM_COMMAND_PREFIX = "$SYS/brokers/";
    private static final String SYSTEM_DISCONNET = "/disconnected";
    private static final String SYSTEM_CONNET = "/connected";
    private static final String COMMAND_PREFIX = "reader_";

    @Autowired
    private IndoorDistributionMessageManager messageManager;

    @Override
    public JSONObject parse(String topic, MqttMessage message) {
        log.debug("接收到消息:{}",topic);
        byte[] data = message.getPayload();
        if (topic.startsWith(SYSTEM_COMMAND_PREFIX)) {
            if(topic.endsWith(SYSTEM_DISCONNET)){
                log.debug("设备断开连接 , {}" ,topic);
            }else if(topic.endsWith(SYSTEM_CONNET)){
                log.debug("设备连接 , {}" ,topic);
            }
            String payload = ByteUtil.bytesToHexString(data);
            JSONObject res = new JSONObject();
            res.put("topic", topic);
            res.put("result",payload);
            return res;
        } else if (topic.startsWith(COMMAND_PREFIX)) {
            String[] messages = new String[1];
            messages[0] = ByteUtil.toHexString(data);
            if (messages[0].contains("5e5d")) {
                messages[0] = messages[0].replace("5e5d", "5e");
            }
            if (messages[0].contains("5e7d")) {
                messages[0] = messages[0].replace("5e7d", "7e");
            }
            String type = messages[0].substring(10, 12);
            String functionCode = messages[0].substring(14, 16);
            String messageId = type + functionCode;
            boolean flag = "01".equals(messages[0].substring(12, 14));
            if(Boolean.TRUE.equals(flag)){
                JSONObject decode = messageManager.getSubscribeMessage(messageId).decode(messages[0], topic);
                log.warn(JSONObject.toJSONString(decode));
                return decode;
            }else{
                return null;
            }
        } else {
            log.debug("Can't recognize this topic!");
            return null;
        }
    }
}
