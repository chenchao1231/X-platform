package cn.exrick.xboot.modules.indoor.mqtt.messager.rfid;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.RFIDFunctionType;
import cn.exrick.xboot.modules.indoor.mqtt.messager.SubscribeMessage;
import cn.exrick.xboot.modules.indoor.mqtt.utils.ByteUtil;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月18日-13:53:12
 */
@Component
public class FaultDiagnosisSubMessage implements SubscribeMessage {

    @Override
    public String parseData(String message) {
        return ByteUtil.reverseHex(SubscribeMessage.super.parseData(message));
    }

    @Override
    public String getType() {
        return CommandsType.RFID.getCode();
    }

    @Override
    public String getFunctionCode() {
        return RFIDFunctionType.FAULTDIAGNOSIS.getCode();
    }

}
