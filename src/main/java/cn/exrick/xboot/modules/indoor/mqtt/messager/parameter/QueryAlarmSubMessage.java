package cn.exrick.xboot.modules.indoor.mqtt.messager.parameter;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.ParameterFunctionType;
import cn.exrick.xboot.modules.indoor.mqtt.messager.SubscribeMessage;
import cn.exrick.xboot.modules.indoor.mqtt.utils.ByteUtil;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月18日-15:16:11
 */
@Component
public class QueryAlarmSubMessage implements SubscribeMessage {

    @Override
    public String parseData(String message) {
        String allData = SubscribeMessage.super.parseData(message);
        String reverseHex = ByteUtil.reverseHex(allData);
        String binaryString = Integer.toBinaryString(Integer.parseInt(reverseHex, 16));
        JSONObject res = new JSONObject();
        res.put("alarmStatus", binaryString);
        return res.toJSONString();
    }

    @Override
    public String getType() {
        return CommandsType.PARAMETER.getCode();
    }

    @Override
    public String getFunctionCode() {
        return ParameterFunctionType.QUERYALARM.getCode();
    }
}
