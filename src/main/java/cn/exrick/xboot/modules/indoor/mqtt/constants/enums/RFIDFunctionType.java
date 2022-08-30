package cn.exrick.xboot.modules.indoor.mqtt.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月08日-10:53:02
 */
@Getter
@AllArgsConstructor
public enum RFIDFunctionType {
    FAULTDIAGNOSIS("10", "0400", "01040000"),
    ACCURATEREADING("11", "0400", "01040000"),
    STATUSREPORT("12", "0e00", ""),
    LOSSREPORT("13", "0e00", ""),
    ;

    private String code;
    private String dataLength;
    private String data;
}
