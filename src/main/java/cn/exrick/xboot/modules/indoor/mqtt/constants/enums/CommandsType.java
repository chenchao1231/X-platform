package cn.exrick.xboot.modules.indoor.mqtt.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description
 *
 * @author 蔺扬
 * createTime 2022年7月07日-17:30:46
 */
@Getter
@AllArgsConstructor
public enum CommandsType {
    SYSTEM("01"),
    RFID("02"),
    PARAMETER("04")
    ;

    private String code;
    
}
