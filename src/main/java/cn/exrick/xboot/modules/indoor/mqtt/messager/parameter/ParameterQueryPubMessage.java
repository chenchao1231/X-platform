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
 *         createTime 2022年7月19日-13:22:59
 */
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ParameterQueryPubMessage extends AbstractPublishMessage {

    private String deviceId;

    private String oId;

    public String getType() {
        return CommandsType.PARAMETER.getCode();
    }

    public String getFunctionCode() {
        return ParameterFunctionType.QUERYPARAMETER.getCode();
    }

    @Override
    public String getDataLength() {
        return ParameterFunctionType.getDataLength(getOId());
    }

    @Override
    public String getData() {
        return ParameterFunctionType.getData(getOId());
    }
}
