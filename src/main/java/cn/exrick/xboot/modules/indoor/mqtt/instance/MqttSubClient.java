package cn.exrick.xboot.modules.indoor.mqtt.instance;

import cn.exrick.xboot.modules.indoor.mqtt.callback.MqttCallBack;
import cn.exrick.xboot.modules.indoor.mqtt.messager.parser.IndoorDistributionMessageParser;
import cn.hutool.core.lang.UUID;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenchao
 */
public class MqttSubClient {

    private static final Logger log = LoggerFactory.getLogger(MqttSubClient.class);
    public static String url = "";
    public static int port = 0;
    public static String MQTT_USERNAME = "admin";
    public static String MQTT_PASSWORD = "public";
    public static String client_name = "IAS_SUB_" + UUID.randomUUID().toString().replace("-", "");
    public static int MQTT_TIMEOUT = 10;
    public static int MQTT_KEEPALIVE = 10;
    private MqttClient client;
    private static volatile MqttSubClient mqttClient = null;

    private static IndoorDistributionMessageParser parser;

    public static void setParser(IndoorDistributionMessageParser indoorDistributionMessageParser) {
        parser = indoorDistributionMessageParser;
    }

    public static MqttSubClient getInstance(String url , int port , IndoorDistributionMessageParser parser) {
        setParser(parser);
    	boolean flag = false; 
    	log.debug("11.MqttSubClient获取MQTT实例.....");;
        if(mqttClient == null) {
        	log.debug("22.MqttSubClient 为 null,开始尝试建立连接.....");;
            synchronized (MqttSubClient.class) {
                if(mqttClient == null) {
                	flag = true;
                    mqttClient = new MqttSubClient(url,port);
                    log.debug("33.MqttSubClient 创建成功");;
                }
            }
        }
        if(flag) {
        	log.debug("44.返回MqttSubClient实例");;
        }else {
        	log.debug("44-4.获取MqttSubClient实例成功.");;
        }
        return mqttClient;
    }
    
    
    public static MqttSubClient getInstance() {
    	boolean flag = false; 
    	log.debug("11.MqttSubClient获取MQTT实例.....");;
        if(mqttClient == null) {
        	log.debug("22.MqttSubClient 为 null,开始尝试建立连接.....");;
            synchronized (MqttSubClient.class) {
                if(mqttClient == null) {
                	flag = true;
                    mqttClient = new MqttSubClient();
                    log.debug("33.MqttSubClient 创建成功");;
                }
            }
        }
        if(flag) {
        	log.debug("44.返回MqttSubClient实例");;
        }else {
        	log.debug("44-4.获取MqttSubClient实例成功.");;
        }
        return mqttClient;
    }
    
    private MqttSubClient(String url , int port) {
    	MqttSubClient.url = url;
    	MqttSubClient.port = port;
    	connect(MqttSubClient.url , MqttSubClient.port);
    }
    
    private MqttSubClient() {
        connect();
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
            try {
                client.setCallback(new MqttCallBack(parser));
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
        	client = new MqttClient(url + ":" + port, client_name, new MemoryPersistence());
            MqttConnectOptions option = new MqttConnectOptions();
            option.setCleanSession(true);
            option.setUserName(MQTT_USERNAME);
            option.setPassword(MQTT_PASSWORD.toCharArray());
            option.setConnectionTimeout(MQTT_TIMEOUT);
            option.setKeepAliveInterval(MQTT_KEEPALIVE);
            option.setAutomaticReconnect(true);
            
            //设置遗嘱消息
            try {
                client.setCallback(new MqttCallBack(parser));
                client.connect(option);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 订阅某个主题 qos默认为0
     * @param topic 主题
     */
    public void subscribe(String topic) {
        subscribe(topic, 1);
    }
    
    public void unSubscribe(String topic) {
    	try {
			client.unsubscribe(topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }
    /**
     * 订阅某个主题
     * @param topic 主题
     * @param qos 握手级别
     */
    public void subscribe(String topic, int qos) {
        try {
            client.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}