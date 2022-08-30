package cn.exrick.xboot.modules.indoor.mqtt.messager.parameter;

import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.CommandsType;
import cn.exrick.xboot.modules.indoor.mqtt.constants.enums.ParameterFunctionType;
import cn.exrick.xboot.modules.indoor.mqtt.messager.AbstractPublishMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月08日-14:03:14
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SetProbePubMessage extends AbstractPublishMessage {

    private String deviceId;

    private String antennaId;

    private String probeId;

    @Override
    public String getDataLength() {
        return ParameterFunctionType.SETPROBE.getLength();
    }

    @Override
    public String getData() {
        String res = getProbeId();
        if (getProbeId().length() < 24) {
            res = getProbeId() + StringUtils.repeat("0",24 - getProbeId().length());
        }
        return getAntennaId() + res;
    }

    @Override
    public String getType() {
        return CommandsType.PARAMETER.getCode();
    }

    @Override
    public String getFunctionCode() {
        return ParameterFunctionType.SETPROBE.getCode();
    }

}
