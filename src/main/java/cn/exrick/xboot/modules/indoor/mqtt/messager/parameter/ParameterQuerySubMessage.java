package cn.exrick.xboot.modules.indoor.mqtt.messager.parameter;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.ParameterFunctionType;
import cn.exrick.xboot.modules.indoor.mqtt.messager.SubscribeMessage;
import cn.exrick.xboot.modules.indoor.mqtt.utils.ByteUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月19日-13:42:19
 */
@Component
public class ParameterQuerySubMessage implements SubscribeMessage {

    public String getType() {
        return CommandsType.PARAMETER.getCode();
    }

    public String getFunctionCode() {
        return ParameterFunctionType.QUERYPARAMETER.getCode();
    }

    @Override
    public String parseData(String message) {
        String dataUnit = SubscribeMessage.super.parseData(message);
        String oId = getParameterOid(dataUnit);
        String data = getMonitorData(dataUnit);
        JSONObject jsonObject = new JSONObject();
        switch (oId) {
            case "a000":
            case "a100":
            case "a200":
            case "a300":
                jsonObject.put(oId, data);
                break;
            case "a800":
                List<String> strs = new ArrayList<>();
                StringBuilder ipSbuffer = new StringBuilder();
                while (data.length() > 0) {
                    String substring = data.substring(0, 2);
                    strs.add(substring);
                    data = data.substring(2);
                }
                for (String asc : strs) {
                    char c = (char) Integer.parseInt(asc, 16);
                    if ("\u0000".equals(String.valueOf(c))) {
                        continue;
                    }
                    ipSbuffer.append(c);
                }
                jsonObject.put(oId, ipSbuffer.toString());
                break;
            case "a900":
                String portHex = ByteUtil.reverseHex(data);
                int port = Integer.parseInt(portHex, 16);
                jsonObject.put(oId, String.valueOf(port));
                break;
            case "b001":
            case "b101":
            case "b201":
                jsonObject.put(oId, getFrequencyQueryResult(data));
                break;
            case "b301":
            case "ba01":
            case "bb01":
            case "b901":
                jsonObject.put(oId, getPowerQueryResult(data));
                break;
            case "b401":
            case "b801":
                jsonObject.put(oId, getSwitchQueryResult(data));
                break;
            case "b501":
            case "b601":
                jsonObject.put(oId, getTimeQueryResult(data));
                break;
            case "b701":
                int numbers = Integer.parseInt(data, 16);
                jsonObject.put(oId, String.valueOf(numbers));
                break;
            default:
                jsonObject.put(oId, data);
                break;
        }
        return JSON.toJSONString(jsonObject);
    }

}
