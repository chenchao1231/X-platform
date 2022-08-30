package cn.exrick.xboot.modules.indoor.basedata.controller;

import cn.exrick.xboot.modules.indoor.basedata.entity.Antenna;
import cn.exrick.xboot.modules.indoor.basedata.entity.MonitoringHost;
import cn.exrick.xboot.modules.indoor.basedata.service.IAntennaService;
import cn.exrick.xboot.modules.indoor.basedata.service.IMonitoringHostService;
import cn.exrick.xboot.modules.indoor.topo.service.TopoService;
import cn.exrick.xboot.support.req.PageRequest;
import cn.exrick.xboot.support.retn.PageResult;
import cn.exrick.xboot.support.retn.Result;
import cn.exrick.xboot.support.retn.ResultCode;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Chenchao
 */
@Slf4j
@RestController
@Api(tags = "监测主机管理接口")
@RequestMapping("/indoor/basedata/monitoringHost")
public class MonitoringHostController {

    @Autowired
    private IMonitoringHostService monitoringHostService;
    @Autowired
    private IAntennaService antennaService;
    @Autowired
    private TopoService topoService;

    @PostMapping(value = "/get/{id}")
    @ApiOperation(value = "通过id获取监控主机信息")
    public Result<MonitoringHost> get(@PathVariable String id) {
        MonitoringHost monitoringHost = monitoringHostService.getById(id);
        if(ObjectUtil.isNotEmpty(monitoringHost)){
            return Result.succeed(monitoringHost,"获取结果完毕");
        }else {
            return  Result.failed(ResultCode.SF_HOST_NOT_EXIST.getCode(),ResultCode.SF_HOST_NOT_EXIST.getMessage());
        }
    }


    @RequestMapping(value = "/pageMonitorHost", method = RequestMethod.POST)
    @ApiOperation(value = "根据分页条件获取监控主机列表,!!!车站编号必须参数,否则返回空数组")
    public PageResult<MonitoringHost> getByPage(@RequestBody PageRequest<MonitoringHost> pageRequest) {
        if(ObjectUtil.isNotEmpty(pageRequest.getT()) && StringUtils.isNotBlank(pageRequest.getT().getStationId())) {
            PageResult<MonitoringHost> pageData = monitoringHostService.pageByConditions(pageRequest);
            return pageData;
        }
        return new PageResult<MonitoringHost>();
    }


    /**
     * 保存监控主机信息
     * @param host 监控主机实体信息
     * @return 是否保存成功
     */
    @PostMapping("/save")
    public Result<Boolean> saveMonitorHost(@RequestBody MonitoringHost host) {
        String hostNumber = host.getHostNumber();
        MonitoringHost dbHost = monitoringHostService.getByHostNumber(host.getHostNumber());
        if(ObjectUtil.isNotEmpty(dbHost)){
            return Result.failed("监控主机编号重复");
        }
        return monitoringHostService.saveMonitorHost(host);
    }


    /**
     * 根据监控主机编号删除
     * @param hostNumber 监控主机编号
     * @return 是否删除成功
     */
    @PostMapping("/delete/byHostNumber/{hostNumber}")
    public Result<Boolean> deleteMonitorHostByHostNumber(@PathVariable String hostNumber){
        return monitoringHostService.deleteMonitorHostByHostNumber(hostNumber);
    }


    /**
     * 根据监控主机编号获取录入的探针列表
     * @param hostNumber 监控主机编号
     * @return 监控主机中的探针列表
     */
    @PostMapping("/listAntenna/{hostNumber}")
    public Result<List<Antenna>> listAntennaByHostNumber(@PathVariable String hostNumber){
        LambdaQueryWrapper<Antenna> lqwAnt = new LambdaQueryWrapper<>();
        lqwAnt.eq(Antenna::getHostNumber,hostNumber);

        List<Antenna> antennaList = this.antennaService.list(lqwAnt);
        return Result.succeed(antennaList,"获取探针编号列表完毕");
    }

    /**
     * 获取TOPO图信息
     * @param hostNumber 监控主机编号
     * @return topo渲染的节点信息
     */
    @PostMapping("/getTopo/{hostNumber}")
    public Result<Map<String,Object>> getTopo(@PathVariable String hostNumber){
        Map<String, Object> topo = topoService.getTopo(hostNumber);
        return Result.succeed(topo,"topo图获取完毕");
    }

}
