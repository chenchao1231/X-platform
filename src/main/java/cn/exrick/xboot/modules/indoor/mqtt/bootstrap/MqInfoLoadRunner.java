package cn.exrick.xboot.modules.indoor.mqtt.bootstrap;

import cn.exrick.xboot.common.utils.SpringUtils;
import cn.exrick.xboot.modules.indoor.mqtt.instance.MqttPushClient;
import cn.exrick.xboot.modules.indoor.mqtt.instance.MqttSubClient;
import cn.exrick.xboot.modules.indoor.dbconfig.entity.MqDbConfig;
import cn.exrick.xboot.modules.indoor.dbconfig.enums.MqDbConfigStatus;
import cn.exrick.xboot.modules.indoor.dbconfig.service.IMqDbConfigService;
import cn.exrick.xboot.modules.indoor.mqtt.messager.parser.IndoorDistributionMessageParser;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author chenchao
 */
@Order(1)
@Component
public class MqInfoLoadRunner implements ApplicationRunner{

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //读取MQ连接配置信息
        IMqDbConfigService mqDbConfigService = SpringUtils.getBean(IMqDbConfigService.class);
        IndoorDistributionMessageParser parser = SpringUtils.getBean(IndoorDistributionMessageParser.class);
        LambdaQueryWrapper<MqDbConfig> lqw = new LambdaQueryWrapper<MqDbConfig>();
        lqw.eq(MqDbConfig::getEnable, MqDbConfigStatus.ENABLED.getStatusCode());
        MqDbConfig dbConfig = mqDbConfigService.getOne(lqw,true);
        if(ObjectUtil.isNotEmpty(dbConfig)){
            MqttSubClient.getInstance(dbConfig.getUrl(),dbConfig.getPort(),parser);
            MqttPushClient.getInstance(dbConfig.getUrl(),dbConfig.getPort());
        }
    }
}

