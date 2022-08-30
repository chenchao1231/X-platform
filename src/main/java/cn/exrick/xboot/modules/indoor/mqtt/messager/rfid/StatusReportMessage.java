package cn.exrick.xboot.modules.indoor.mqtt.messager.rfid;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.RFIDFunctionType;
import cn.exrick.xboot.modules.indoor.mqtt.messager.SubscribeMessage;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月18日-13:57:10
 */
@Component
public class StatusReportMessage implements SubscribeMessage {

    @Override
    public String parseData(String message) {
        String allData = SubscribeMessage.super.parseData(message);
        int length = allData.length();
        JSONObject res = new JSONObject();
        res.put("antennaId", allData.substring(0, 2));
        res.put("probeId", allData.substring(2, length - 2));
        res.put("probeStatus", allData.substring(length - 2).equals("00") ? "fault" : "normal");
        return res.toJSONString();
    }

    @Override
    public String getType() {
        return CommandsType.RFID.getCode();
    }

    @Override
    public String getFunctionCode() {
        return RFIDFunctionType.STATUSREPORT.getCode();
    }

}
