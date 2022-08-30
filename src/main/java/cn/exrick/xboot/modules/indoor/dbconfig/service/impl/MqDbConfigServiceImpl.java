package cn.exrick.xboot.modules.indoor.dbconfig.service.impl;

import cn.exrick.xboot.modules.indoor.dbconfig.mapper.MqDbConfigMapper;
import cn.exrick.xboot.modules.indoor.dbconfig.service.IMqDbConfigService;
import cn.exrick.xboot.modules.indoor.dbconfig.entity.MqDbConfig;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author chenchao
 */
@Service
public class MqDbConfigServiceImpl extends ServiceImpl<MqDbConfigMapper, MqDbConfig> implements IMqDbConfigService {
}
