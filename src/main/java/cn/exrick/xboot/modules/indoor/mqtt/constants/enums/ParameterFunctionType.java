package cn.exrick.xboot.modules.indoor.mqtt.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月08日-10:54:04
 */
@Getter
@AllArgsConstructor
public enum ParameterFunctionType {
    QUERYPROBE("01", "0000"),
    SETPROBE("02", "0d00"),
    DELETEPROBE("06", "0d00"),
    QUERYALARM("08", "0000"),
    QUERYPARAMETER("09", ""),
    SETPARAMETER("0a", ""),
    SOFTVERSION("a000", "1700"),
    DEVICETYPE("a100", "1700"),
    DEVICESERIAL("a200", "1700"),
    DEVICETIME("a300", "0a00"),
    DEVICEADDRESS("a800", "1700"),
    DEVICEPORT("a900", "0500"),
    STARTFREQUENCY("b001", "0700"),
    ENDFREQUENCY("b101", "0700"),
    FREQUENCYSTEP("b201", "0500"),
    TRANSMITTEDPOWER("b301", "0400"),
    FAULTDIAGNOSISSWITCH("b401", "0400"),
    FAULTDIAGNOSISTIME("b501", "0500"),
    FAULTDIAGNOSISPERIOD("b601", "0500"),
    FAULTDIAGNOSISNUMBER("b701", "0400"),
    ACCURATEREADINGSWICH("b801", "0400"),
    ACCURATEREADINGCORRECTION("b901", "0400"),
    STARTPOWER("ba01", "0400"),
    ENDPOWER("bb01", "0400"),
    ;

    private String code;
    private String length;

    public static String getDataLength(String code) {
        return Arrays.asList(ParameterFunctionType.values()).stream()
                .filter(t -> code.equals(t.getCode()))
                .map(ParameterFunctionType::getLength)
                .findFirst()
                .orElse(null);
    }

    public static String getData(String code) {
        String[] res = new String[1];
        res[0] = getDataLength(code).substring(0, 2) + code;
        switch (code) {
            case "a300":
                res[0] = res[0] + StringUtils.repeat("0",14);
                break;
            case "a900":
            case "b201":
            case "b501":
            case "b601":
                res[0] = res[0] + StringUtils.repeat("0",4);
                break;
            case "b001":
            case "b101":
                res[0] = res[0] + StringUtils.repeat("0",8);
                break;
            case "b301":
            case "b401":
            case "b701":
            case "b801":
            case "b901":
            case "ba01":
            case "bb01":
                res[0] = res[0] + StringUtils.repeat("0",2);
                break;
            default:
                res[0] = res[0] + StringUtils.repeat("0",40);
                break;
        }
        return res[0];
    }
}
