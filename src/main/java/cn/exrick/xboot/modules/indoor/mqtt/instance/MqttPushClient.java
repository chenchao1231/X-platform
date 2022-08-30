package cn.exrick.xboot.modules.indoor.mqtt.instance;

import cn.exrick.xboot.modules.indoor.mqtt.utils.ByteUtil;
import cn.hutool.core.lang.UUID;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenchao
 */
public class MqttPushClient {

    private static final Logger log = LoggerFactory.getLogger(MqttPushClient.class);
    public static String url = "";
    public static int port = 0;
    public static String MQTT_USERNAME = "admin";
    public static String MQTT_PASSWORD = "public";
    public static String client_name = "IAS_PUB_" + UUID.randomUUID().toString().replace("-", "");
    public static int MQTT_TIMEOUT = 0;
    public static int MQTT_KEEPALIVE = 30;
    private MqttClient client;
    private static volatile MqttPushClient mqttClient = null;

    public static MqttPushClient getInstance() {
    	boolean flag = false; 
    	log.debug("1.获取MQTT实例.....");
        if(mqttClient == null) {
        	log.debug("2.mqttClient 为 null,开始尝试建立连接.....");;
            synchronized (MqttPushClient.class) {
                if(mqttClient == null) {
                	flag = true;
                    mqttClient = new MqttPushClient();
                    log.debug("3.mqttClient 创建成功");;
                }
            }
        }
        if(flag) {
        	log.debug("4.返回Client实例");;
        }else {
        	log.debug("4-4.获取client实例成功.");;
        }
        return mqttClient;
    }
    
    public static MqttPushClient getInstance(String url , int port) {
    	boolean flag = false; 
    	log.debug("1.获取MQTT实例.....");
        if(mqttClient == null) {
        	log.debug("2.mqttClient 为 null,开始尝试建立连接.....");;
            synchronized (MqttPushClient.class) {
                if(mqttClient == null) {
                	flag = true;
                    mqttClient = new MqttPushClient(url,port);
                    log.debug("3.mqttClient 创建成功");;
                }
            }
        }
        if(flag) {
        	log.debug("4.返回Client实例");;
        }else {
        	log.debug("4-4.获取client实例成功.");;
        }
        return mqttClient;
    }

    private MqttPushClient() {
        connect();
    }
    
    private MqttPushClient(String url , int port) {
    	MqttPushClient.url = url;
    	MqttPushClient.port = port;
        connect(url,port);
    }

    private void connect() {
        try {
            client = new MqttClient(url + ":" + port, client_name, new MemoryPersistence());
            MqttConnectOptions option = new MqttConnectOptions();
            option.setCleanSession(true);
            option.setUserName(MQTT_USERNAME);
            option.setPassword(MQTT_PASSWORD.toCharArray());
            option.setConnectionTimeout(MQTT_TIMEOUT);
            option.setKeepAliveInterval(MQTT_KEEPALIVE);
            option.setAutomaticReconnect(true);
            //设置遗嘱消息
            option.setWill("will_topic", "close".getBytes(), 1, true);
            try {
                client.connect(option);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void connect(String url ,int port) {
        try {
        	client = new MqttClient(url + ":" + port, client_name , new MemoryPersistence());
            MqttConnectOptions option = new MqttConnectOptions();
            option.setCleanSession(true);
            option.setUserName(MQTT_USERNAME);
            option.setPassword(MQTT_PASSWORD.toCharArray());
            option.setConnectionTimeout(MQTT_TIMEOUT);
            option.setKeepAliveInterval(MQTT_KEEPALIVE);
            option.setAutomaticReconnect(true);
            try {
                client.connect(option);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发布主题，用于通知<br>
     * 默认qos为1 非持久化
     * @param topic 主题
     * @param data 发送的数据包
     */
    public void publish(String topic, String data) {
        publish(1, false ,topic, data );
    }
    /**
     * 发布
     * @param topic 主题
     * @param data 数据包
     * @param qos 握手级别，默认0
     */
    public void publish(int qos, boolean retained ,String topic, String data ) {
    	if(StringUtils.isBlank(data)) {
    		log.error("当前报文存在问题,忽略本次发送");
    		return;
    	}
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        byte[] dataByte = ByteUtil.hexStringToBytes(data);
        message.setPayload(dataByte);
        MqttTopic mqttTopic = client.getTopic(topic);
        if(null == mqttTopic) {
            log.error("Topic Not Exist");
        }
        MqttDeliveryToken token;
        try {
        	log.debug("即将执行发布指令:{},deviceId:{}",data , topic);
            token = mqttTopic.publish(message);
            log.debug("发布完毕,线程睡眠10s");
            Thread.sleep(1);
            log.debug("线程恢复");
            token.waitForCompletion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 不用等待
     * @param qos 握手级别 默认0
     * @param retained ==
     * @param topic 主题
     * @param data 数据包
     */
    public void publishNoWait(int qos, boolean retained ,String topic, String data , long wait) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        byte[] dataByte = ByteUtil.hexStringToBytes(data);
        message.setPayload(dataByte);
        MqttTopic mqttTopic = client.getTopic(topic);
        if(null == mqttTopic) {
            log.error("Topic Not Exist");
        }
        MqttDeliveryToken token;
        try {
        	log.debug("即将执行发布指令:{},deviceId:{}",data , topic);
            token = mqttTopic.publish(message);
            if(wait <=0) {
            	wait = 100;
            }
            log.debug("发布完毕,线程睡眠{}s",wait/1000);
            Thread.sleep(wait);
            log.debug("线程恢复");
            token.waitForCompletion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}