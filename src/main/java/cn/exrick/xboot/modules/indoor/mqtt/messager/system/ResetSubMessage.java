package cn.exrick.xboot.modules.indoor.mqtt.messager.system;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.SystemFunctionType;
import cn.exrick.xboot.modules.indoor.mqtt.messager.SubscribeMessage;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 蔺扬
 * createTime 2022年7月18日-13:28:12
 */
@Component
public class ResetSubMessage implements SubscribeMessage {

    @Override
    public String parseData(String message) {
        return "";
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
