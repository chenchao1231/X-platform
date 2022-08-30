package cn.exrick.xboot.modules.indoor.mqtt.messager;

import cn.exrick.xboot.modules.indoor.mqtt.constants.IndoorMessageConstants;
import cn.exrick.xboot.modules.indoor.mqtt.utils.ByteUtil;
import com.alibaba.fastjson2.JSONObject;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月15日-17:10:17
 */
public interface SubscribeMessage {

    /**
     * It returns the type of the message.
     *
     * @return The type of the message.
     */
    String getType();

    /**
     * It returns the function code of the message type.
     *
     * @return The function code is being returned.
     */
    String getFunctionCode();

    /**
     * This function returns the message type and function code concatenated
     * together.
     *
     * @return The message type and function code.
     */
    default String getId() {
        return getType() + getFunctionCode();
    }

    /**
     * It returns a string that is either "UP" or "DOWN" depending on the value of a
     * constant
     *
     * @return The default value of the method.
     */
    default String getUpDown() {
        return IndoorMessageConstants.UP_FLAG;
    }

    /**
     * A function that parses the data in the message.
     *
     * @param message The message received by the device
     * @param topic   The topic of the message
     * @return A Mono<JSONObject>
     */
    default JSONObject decode(String message, String topic) {
        SubscribeMessageBody body = SubscribeMessageBody.builder()
                .topic(topic)
                .deviceId(parseDeviceId(message))
                .messageType(getType())
                .functionCode(getFunctionCode())
                .dataLength(parseDataLength(message))
                .result(parseResult(message))
                .data(parseData(message))
                .build();
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(body));
        return jsonObject;
    }

    default String parseResult(String message) {
        String result = message.substring(16, 18);
        if (result.equals("01")) {
            return "normal";
        }
        if (result.equals("02")) {
            return "system busy";
        }
        if (result.equals("03")) {
            return "device fault";
        }
        return "ff";
    }

    default String parseDeviceId(String message) {
        return ByteUtil.reverseHex(message.substring(2, 10));
    }

    default String parseDataLength(String message) {
        return ByteUtil.reverseHex(message.substring(18, 22));
    }

    default String parseData(String message) {
        return message.substring(22, message.length() - 6);
    }

    default String getParameterOid(String dataUnit){
        return dataUnit.substring(2,6);
    }

    default String getMonitorData(String dataUnit) {
        return dataUnit.substring(6);
    }

    default String getFrequencyQueryResult(String data) {
        String mhzHex = ByteUtil.reverseHex(data);
        double hmz = Integer.parseInt(mhzHex, 16);
        return String.valueOf(hmz / 100);
    }

    default String getPowerQueryResult(String data) {
        Byte result = (byte) Integer.parseInt(data, 16);
        return String.valueOf(result);
    }

    default String getTimeQueryResult(String data) {
        String result = ByteUtil.reverseHex(data);
        String hour = result.substring(0, 2);
        String mins = result.substring(2, 4);
        return hour + ":" + mins;
    }

    default String getSwitchQueryResult(String data) {
        int result = Integer.parseInt(data, 16);
        boolean flag = result == 1;
        return String.valueOf(flag);
    }
}
