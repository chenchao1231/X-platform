package cn.exrick.xboot.modules.indoor.basedata.service;

import cn.exrick.xboot.modules.indoor.basedata.entity.Antenna;
import cn.exrick.xboot.support.retn.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author chenchao
 */
public interface IAntennaService extends IService<Antenna> {

    /**
     * 根据监控主机编号删除记录
     * @param hostNumber 监控主机编号
     * @return 是否成功
     */
    public Result<Boolean> removeByHostNumber(String hostNumber);
}
