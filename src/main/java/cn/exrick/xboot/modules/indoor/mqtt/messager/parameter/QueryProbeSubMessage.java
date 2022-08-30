package cn.exrick.xboot.modules.indoor.mqtt.messager.parameter;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.ParameterFunctionType;
import cn.exrick.xboot.modules.indoor.mqtt.messager.SubscribeMessage;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月18日-14:58:09
 */
@Component
public class QueryProbeSubMessage implements SubscribeMessage {

    @Override
    public String parseData(String message) {
        String allData = SubscribeMessage.super.parseData(message);
        int length = allData.length();
        JSONObject res = new JSONObject();
        res.put("antennaId", allData.substring(0, 2));
        res.put("probeId", allData.substring(2, length - 2));
        return res.toJSONString();
    }

    @Override
    public String getType() {
        return CommandsType.PARAMETER.getCode();
    }

    @Override
    public String getFunctionCode() {
        return ParameterFunctionType.QUERYPROBE.getCode();
    }

}
