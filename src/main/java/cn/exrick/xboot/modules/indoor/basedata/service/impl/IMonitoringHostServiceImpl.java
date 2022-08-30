package cn.exrick.xboot.modules.indoor.basedata.service.impl;

import cn.exrick.xboot.modules.indoor.basedata.entity.MonitoringHost;
import cn.exrick.xboot.modules.indoor.basedata.mapper.MonitoringHostMapper;
import cn.exrick.xboot.modules.indoor.basedata.service.IAntennaService;
import cn.exrick.xboot.modules.indoor.basedata.service.IMonitoringHostService;
import cn.exrick.xboot.modules.indoor.topo.service.TopoService;
import cn.exrick.xboot.support.req.MybatiesPageServiceTemplate;
import cn.exrick.xboot.support.req.PageRequest;
import cn.exrick.xboot.support.retn.PageResult;
import cn.exrick.xboot.support.retn.Result;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 监测主机接口实现
 * @author Chenchao
 */
@Slf4j
@Service
@Transactional
public class IMonitoringHostServiceImpl extends ServiceImpl<MonitoringHostMapper, MonitoringHost> implements IMonitoringHostService {

    @Autowired
    private IAntennaService antennaService;
    @Autowired
    private TopoService topoService;

    @Override
    public PageResult<MonitoringHost> pageByConditions(PageRequest<MonitoringHost> pageRequest) {
        MybatiesPageServiceTemplate<MonitoringHost, MonitoringHost> page = new MybatiesPageServiceTemplate<MonitoringHost, MonitoringHost>(
                this.baseMapper) {
            @Override
            protected Wrapper<MonitoringHost> constructWrapper(MonitoringHost host) {
                LambdaQueryWrapper<MonitoringHost> lqw = new LambdaQueryWrapper<MonitoringHost>();
                if (host == null || StringUtils.isBlank(host.getStationId())) {
                    return lqw;
                }
                lqw.eq(MonitoringHost::getStationId, host.getStationId());

                if (StringUtils.isNotBlank(host.getHostNumber())) {
                    lqw.like(MonitoringHost::getHostNumber, host.getHostNumber());
                }
                lqw.orderByDesc(MonitoringHost::getCreateTime);
                return lqw;
            }
        };
        PageResult<MonitoringHost> pageData = page.doPagingQuery(pageRequest);
        return pageData;
    }

    @Override
    public MonitoringHost getByHostNumber(String hostNumber) {
        LambdaQueryWrapper<MonitoringHost> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MonitoringHost::getHostNumber,hostNumber);
        return baseMapper.selectOne(lqw);
    }

    @Override
    public Result<Boolean> saveMonitorHost(MonitoringHost host) {
        int insertCount = baseMapper.insert(host);
        if(insertCount == 1){
            return Result.succeed(Boolean.TRUE,"保存成功");
        }else {
            return Result.failed(Boolean.FALSE,"保存失败");
        }
    }

    @Override
    public Result<Boolean> deleteMonitorHostByHostNumber(String hostNumber) {
        MonitoringHost dbHost = this.getByHostNumber(hostNumber);
        if(ObjectUtil.isNotEmpty(dbHost)){
            // 删除当前的监控主机信息
            LambdaQueryWrapper<MonitoringHost> lqw = new LambdaQueryWrapper<>();
            lqw.eq(MonitoringHost::getHostNumber,hostNumber);
            baseMapper.deleteById(dbHost);
            // 删除当前监控主机下的天线信息
            antennaService.removeByHostNumber(hostNumber);
            // 删除TOPO信息
            topoService.removeTopoByHostNumber(hostNumber);
            return Result.succeed(Boolean.TRUE ,"删除成功");
        }else{
            return Result.failed(Boolean.FALSE,"删除目标数据不存在");
        }
    }
}

