package cn.exrick.xboot.modules.indoor.mqtt.messager.manager.impl;

import cn.exrick.xboot.modules.indoor.mqtt.messager.AbstractPublishMessage;
import cn.exrick.xboot.modules.indoor.mqtt.messager.SubscribeMessage;
import cn.exrick.xboot.modules.indoor.mqtt.messager.manager.IndoorDistributionMessageManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月11日-14:46:34
 */
@Component
public class DefaultIndoorDistributionMessageManager implements IndoorDistributionMessageManager, BeanPostProcessor {

    private final Map<String, SubscribeMessage> storeSubscribe = new ConcurrentHashMap<>();
    private final Map<String, AbstractPublishMessage> storePublish = new ConcurrentHashMap<>();

    @Override
    public SubscribeMessage getSubscribeMessage(String id) {
        return storeSubscribe.get(id);
    }

    @Override
    public AbstractPublishMessage getPublishMessage(String id) {
        return storePublish.get(id);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof SubscribeMessage) {
            SubscribeMessage message = (SubscribeMessage) bean;
            storeSubscribe.put(message.getId(), message);
        }
        if (bean instanceof AbstractPublishMessage) {
            AbstractPublishMessage message = (AbstractPublishMessage) bean;
            storePublish.put(message.getId(), message);
        }
        return bean;
    }
}
