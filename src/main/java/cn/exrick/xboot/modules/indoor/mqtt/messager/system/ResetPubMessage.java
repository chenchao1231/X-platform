package cn.exrick.xboot.modules.indoor.mqtt.messager.system;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.SystemFunctionType;
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
 *         createTime 2022年7月08日-11:34:56
 */
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ResetPubMessage extends AbstractPublishMessage {

    private String deviceId;

    @Override
    public String getDataLength() {
        return SystemFunctionType.RESET.getDataLength();
    }

    @Override
    public String getData() {
        return SystemFunctionType.RESET.getData();
    }

    @Override
    public String getType() {
        return CommandsType.SYSTEM.getCode();
    }

    @Override
    public String getFunctionCode() {
        return SystemFunctionType.RESET.getCode();
    }

}
