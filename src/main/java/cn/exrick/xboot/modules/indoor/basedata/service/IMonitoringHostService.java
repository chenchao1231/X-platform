package cn.exrick.xboot.modules.indoor.basedata.service;

import cn.exrick.xboot.support.req.PageRequest;
import cn.exrick.xboot.support.retn.PageResult;
import cn.exrick.xboot.support.retn.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.exrick.xboot.modules.indoor.basedata.entity.MonitoringHost;

import java.util.List;

/**
 * 监测主机接口
 * @author Chenchao
 */
public interface IMonitoringHostService extends IService<MonitoringHost> {

    PageResult<MonitoringHost> pageByConditions(PageRequest<MonitoringHost> pageRequest);

    /**
     * 根据监控主机编号查询监控主机实体信息
     * @param hostNumber 监控主机编号
     * @return 监控主机实体信息
     */
    MonitoringHost getByHostNumber(String hostNumber);


    /**
     * 保存监控主机
     * @param host 监控主机实体对象
     * @return 保存结果boolean
     */
    public Result<Boolean> saveMonitorHost(MonitoringHost host);

    /**
     * 根据监控主机自身主键ID删除
     * @param hostNumber 监控主机编号
     * @return 是否删除成功
     */
    public Result<Boolean> deleteMonitorHostByHostNumber(String hostNumber);

}