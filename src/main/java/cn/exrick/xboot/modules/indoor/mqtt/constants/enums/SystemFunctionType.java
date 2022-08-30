package cn.exrick.xboot.modules.indoor.mqtt.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月07日-17:59:44
 */
@Getter
@AllArgsConstructor
public enum SystemFunctionType {
    HEARTBEAT("01", "0400", "00000000"),
    RESET("02", "0000", ""),
    ;

    private String code;
    private String dataLength;
    private String data;
}
