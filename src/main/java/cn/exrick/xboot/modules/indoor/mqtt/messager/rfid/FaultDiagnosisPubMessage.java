package cn.exrick.xboot.modules.indoor.mqtt.messager.rfid;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.RFIDFunctionType;
import cn.exrick.xboot.modules.indoor.mqtt.messager.AbstractPublishMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月08日-13:35:17
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class FaultDiagnosisPubMessage extends AbstractPublishMessage {

    private String deviceId;

    @Override
    public String getData() {
        return RFIDFunctionType.FAULTDIAGNOSIS.getData();
    }

    @Override
    public String getDataLength() {
        return RFIDFunctionType.FAULTDIAGNOSIS.getDataLength();
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
