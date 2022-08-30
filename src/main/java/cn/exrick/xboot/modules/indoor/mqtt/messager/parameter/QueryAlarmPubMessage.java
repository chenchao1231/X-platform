package cn.exrick.xboot.modules.indoor.mqtt.messager.parameter;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.ParameterFunctionType;
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
 *         createTime 2022年7月08日-15:33:36
 */
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class QueryAlarmPubMessage extends AbstractPublishMessage {

    private String deviceId;

    @Override
    public String getDataLength() {
        return ParameterFunctionType.QUERYALARM.getLength();
    }

    @Override
    public String getData() {
        return "";
    }

    @Override
    public String getType() {
        return CommandsType.PARAMETER.getCode();
    }

    @Override
    public String getFunctionCode() {
        return ParameterFunctionType.QUERYALARM.getCode();
    }

}
