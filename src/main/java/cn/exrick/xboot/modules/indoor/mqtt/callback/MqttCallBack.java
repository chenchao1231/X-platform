package cn.exrick.xboot.modules.indoor.mqtt.callback;

import cn.exrick.xboot.common.utils.SpringUtils;
import cn.exrick.xboot.modules.indoor.basedata.entity.MonitoringHost;
import cn.exrick.xboot.modules.indoor.basedata.service.IMonitoringHostService;
import cn.exrick.xboot.modules.indoor.mqtt.instance.MqttSubClient;
import cn.exrick.xboot.modules.indoor.mqtt.messager.parser.IndoorDistributionMessageParser;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author chenchao
 */
@Slf4j
public class MqttCallBack implements MqttCallbackExtended {

	private static final String SYS_TOPIC_ON_LINE = "$SYS/brokers/+/clients/+/connected";
	private static final String SYS_TOPIC_OFF_LINE = "$SYS/brokers/+/clients/+/disconnected";

	private IndoorDistributionMessageParser indoorDistributionMessageParser;

	public MqttCallBack (IndoorDistributionMessageParser indoorDistributionMessageParser){
		this.indoorDistributionMessageParser = indoorDistributionMessageParser;
	}

	public MqttCallBack(){
		super();
	}
	@Override
	public void connectionLost(Throwable throwable) {
		log.warn("发生异常,断开连接:{}",throwable.getMessage());
		throwable.printStackTrace();
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		indoorDistributionMessageParser.parse(topic,message);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	}

	@Override
	public void connectComplete(boolean reconnect, String serverUrl) {
		MqttSubClient.getInstance().subscribe(SYS_TOPIC_OFF_LINE, 0);
		log.info("设备[离线×]监听主题[{}]订阅完毕.",SYS_TOPIC_OFF_LINE);
		MqttSubClient.getInstance().subscribe(SYS_TOPIC_ON_LINE, 0);
		log.info("设备[上线√]监听主题[{}]订阅完毕.",SYS_TOPIC_ON_LINE);

		IMonitoringHostService monitoringHostService = SpringUtils.getBean(IMonitoringHostService.class);
		List<MonitoringHost> list = monitoringHostService.list();
		log.info("开始订阅监控主机业务主题:>>>");
		for (MonitoringHost host : list) {
			String hostNumber = host.getHostNumber();
			String topic = "reader_" + hostNumber;
			MqttSubClient.getInstance().subscribe(topic, 0);
			log.warn("订阅主题:{} 完毕", topic);
		}
		MqttSubClient.getInstance().subscribe("reader_11112222",0);
		log.info("监控主机业务主题订阅完毕!!!");
	}
}