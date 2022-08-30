package cn.exrick.xboot.modules.indoor.basedata.service.impl;

import cn.exrick.xboot.modules.indoor.basedata.entity.Antenna;
import cn.exrick.xboot.modules.indoor.basedata.mapper.AntennaMapper;
import cn.exrick.xboot.modules.indoor.basedata.service.IAntennaService;
import cn.exrick.xboot.support.retn.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author chenchao
 */
@Service
public class AntennaServiceImpl extends ServiceImpl<AntennaMapper, Antenna> implements IAntennaService {

    @Override
    public Result<Boolean> removeByHostNumber(String hostNumber) {
        if(StringUtils.isNotBlank(hostNumber)){
            LambdaQueryWrapper<Antenna> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Antenna::getHostNumber,hostNumber);
            boolean remove = this.remove(lqw);
            return Result.succeed(remove,"删除成功");
        }else{
            return Result.failed(false,"监控主机编号不能为空");
        }
    }
}
