package cn.exrick.xboot.modules.indoor.mqtt.messager;

import cn.exrick.xboot.modules.indoor.mqtt.constants.IndoorMessageConstants;
import cn.exrick.xboot.modules.indoor.mqtt.utils.ByteUtil;
import org.thymeleaf.util.StringUtils;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月15日-16:57:05
 */
public interface PublishMessage {

    /**
     * It returns the start flag.
     * 
     * @return The return type is a String.
     */
    default String getStart() {
        return IndoorMessageConstants.START_FLAG;
    }

    /**
     * It returns a string
     * 
     * @return The method is returning the value of the constant END_FLAG.
     */
    default String getEnd() {
        return IndoorMessageConstants.END_FLAG;
    }

    /**
     * Returns the device ID
     * 
     * @return The device ID.
     */
    public String getDeviceId();

    /**
     * Returns the length of the data in the packet
     * 
     * @return The length of the data.
     */
    public String getDataLength();

    /**
     * This function returns a String.
     * 
     * @return The data of the node.
     */
    public String getData();

    /**
     * This function returns the type of the message
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
        return IndoorMessageConstants.DOWN_FLAG;
    }

    /**
     * If the function is not implemented in the class, then the default
     * implementation is used.
     * 
     * @return FF
     */
    default String getResult() {
        return "FF";
    }

    /**
     * It encodes the message body.
     * 
     * @return The return value is a String.
     */
    default String encode() {
        String messageBody = ByteUtil.reverseHex(getDeviceId()) + getType() + getUpDown()
                + getFunctionCode()
                + getResult() + getDataLength()
                + getData();
        String crc = ByteUtil.crcAlgo(messageBody);
        return getStart() + messageBody + crc + getEnd();
    }

    /**
     * It takes a string, converts it to a byte, converts the byte to a hex string,
     * and if the hex
     * string is only one character long, it adds a zero to the beginning of the
     * string
     * 
     * @param powerData The power data to be parsed.
     * @return A String
     */
    default String parsePowerMessage(String powerData) {
        byte[] byteArray = { Byte.parseByte(powerData) };
        String res = ByteUtil.bytesToHexString(byteArray);
        if (res.length() == 1) {
            res = "0" + res;
        }
        return res;
    }

    /**
     * It takes a string of a number, converts it to an integer, converts that
     * integer to a hexadecimal
     * string, reverses the hexadecimal string, and returns the reversed hexadecimal
     * string
     * 
     * @param frequencyData The frequency value that you want to set.
     * @return A String
     */
    default String parseFrequencyMessage(String frequencyData) {
        int intData = (int) (Double.parseDouble(frequencyData) * 100);
        String frequency = ByteUtil.decimalToHexadecimal(intData);
        int num = 8 - frequency.length();
        frequency = StringUtils.repeat("0",num) + frequency;
        return ByteUtil.reverseHex(frequency);
    }
}
