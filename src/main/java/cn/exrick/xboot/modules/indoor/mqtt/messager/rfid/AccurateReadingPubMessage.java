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
 *         createTime 2022年7月08日-13:46:49
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AccurateReadingPubMessage extends AbstractPublishMessage {

    private String deviceId;

    @Override
    public String getData() {
        return RFIDFunctionType.ACCURATEREADING.getData();
    }

    @Override
    public String getDataLength() {
        return RFIDFunctionType.ACCURATEREADING.getDataLength();
    }

    @Override
    public String getType() {
        return CommandsType.RFID.getCode();
    }

    @Override
    public String getFunctionCode() {
        return RFIDFunctionType.ACCURATEREADING.getCode();
    }
}
