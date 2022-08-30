package cn.exrick.xboot.modules.indoor.mqtt.messager.packer;

import cn.exrick.xboot.modules.indoor.mqtt.messager.AbstractPublishMessage;
import cn.exrick.xboot.modules.indoor.mqtt.messager.manager.IndoorDistributionMessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月19日-16:20:49
 */
@Component
public class IndoorDistributionMessagePacker implements MqttMessagePacker {

    private Map<String, Object> parameter;

    private static final String DEVICE_ID = "deviceId";

    private static final String ANTENNA_ID = "antennaId";

    private static final String PROBE_ID = "probeId";

    private static final String VALUE = "value";

    @Autowired
    private IndoorDistributionMessageManager messageManager;

    @Override
    public String getPackerId() {
        return "indoor_distribution_mqtt";
    }

    @Override
    public String packer(String messageId) {
        AbstractPublishMessage pm = messageManager.getPublishMessage(messageId);
        String tmp = null;
        if (parameter.containsKey(DEVICE_ID) && !parameter.get(DEVICE_ID).equals("")
                && parameter.get(DEVICE_ID) != null) {
            tmp = parameter.get(DEVICE_ID).toString();
            pm.setDeviceId(tmp);
        }
        if (parameter.containsKey(ANTENNA_ID) && !parameter.get(ANTENNA_ID).equals("")
                && parameter.get(ANTENNA_ID) != null) {
            tmp = parameter.get(ANTENNA_ID).toString();
            pm.setAntennaId(tmp);
        }
        if (parameter.containsKey(PROBE_ID) && !parameter.get(PROBE_ID).equals("")
                && parameter.get(PROBE_ID) != null) {
            tmp = parameter.get(PROBE_ID).toString();
            pm.setProbeId(tmp);
        }
        if (parameter.containsKey(VALUE) && !parameter.get(VALUE).equals("")
                && parameter.get(VALUE) != null) {
            tmp = parameter.get(VALUE).toString();
            pm.setAntennaId(tmp);
        }
        return pm.encode();
    }

    @Override
    public void setMessageParameter(Map<String, Object> map) {
        this.parameter.putAll(map);
    }

}
