package cn.exrick.xboot.modules.indoor.mqtt.messager.system;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.SystemFunctionType;
import cn.exrick.xboot.modules.indoor.mqtt.messager.SubscribeMessage;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月15日-15:22:06
 */
@Component
public class HeartbeatSubMessage implements SubscribeMessage {

    @Override
    public String parseResult(String message) {
        return "FF";
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
