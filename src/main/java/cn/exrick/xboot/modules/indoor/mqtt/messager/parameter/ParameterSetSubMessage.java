package cn.exrick.xboot.modules.indoor.mqtt.messager.parameter;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.ParameterFunctionType;
import cn.exrick.xboot.modules.indoor.mqtt.messager.SubscribeMessage;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月18日-17:36:13
 */
@Component
public class ParameterSetSubMessage implements SubscribeMessage {

    public String getType() {
        return CommandsType.PARAMETER.getCode();
    }

    public String getFunctionCode() {
        return ParameterFunctionType.SETPARAMETER.getCode();
    }

    @Override
    public String parseData(String message) {
        String dataUnit = SubscribeMessage.super.parseData(message);
        String oId = getParameterOid(dataUnit);
        String data = getMonitorData(dataUnit);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(oId, data);
        return JSON.toJSONString(jsonObject);
    }

}
