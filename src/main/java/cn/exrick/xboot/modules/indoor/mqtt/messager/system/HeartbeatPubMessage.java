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
 *         createTime 2022年7月08日-09:46:58
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class HeartbeatPubMessage extends AbstractPublishMessage {

    private String deviceId;

    @Override
    public String getResult() {
        return "01";
    }

    @Override
    public String getDataLength() {
        return SystemFunctionType.HEARTBEAT.getDataLength();
    }

    @Override
    public String getData() {
        return SystemFunctionType.HEARTBEAT.getData();
    }

    @Override
    public String getFunctionCode() {
        return SystemFunctionType.HEARTBEAT.getCode();
    }

    @Override
    public String getType() {
        return CommandsType.SYSTEM.getCode();
    }
    
}
