package cn.exrick.xboot.modules.indoor.mqtt.messager.manager;

import cn.exrick.xboot.modules.indoor.mqtt.messager.AbstractPublishMessage;
import cn.exrick.xboot.modules.indoor.mqtt.messager.SubscribeMessage;

/**
 * description
 *
 * @author 蔺扬
 *         createTime 2022年7月11日-14:43:01
 */
public interface IndoorDistributionMessageManager {

    /**
     * 获取发布消息执行者
     * @param id
     * @return
     */
    AbstractPublishMessage getPublishMessage(String id);

    /**
     *
     * @param id
     * @return
     */
    SubscribeMessage getSubscribeMessage(String id);
}
