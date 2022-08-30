package cn.exrick.xboot.modules.indoor.mqtt.messager.packer;

import java.util.Map;

/**
 * description
 *
 * @author 蔺扬
 * createTime 2022年7月19日-16:14:34
 */
public interface MqttMessagePacker extends NetworkMessagePacker{
    
    /**
     * "Given a message ID, return a Mono that emits the message ID, or an error if the message ID is
     * null."
     * 
     * The function is a bit more complicated than that, but that's the gist of it
     * 
     * @param messageId The message id of the message to be packed.
     * @return A Mono<String>
     */
    String packer(String messageId);

    /**
     * It sets the message parameter.
     * 
     * @param map The map that contains the parameters to be passed to the message.
     */
    void setMessageParameter(Map<String, Object> map);
}
