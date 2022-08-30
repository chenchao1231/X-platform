package cn.exrick.xboot.modules.indoor.topo.service;

import cn.exrick.xboot.modules.indoor.basedata.entity.Antenna;
import cn.exrick.xboot.modules.indoor.basedata.entity.MonitoringHost;
import cn.exrick.xboot.modules.indoor.basedata.service.IAntennaService;
import cn.exrick.xboot.modules.indoor.basedata.service.IMonitoringHostService;
import cn.exrick.xboot.modules.indoor.topo.entity.TopoLinks;
import cn.exrick.xboot.modules.indoor.topo.entity.TopoNodes;
import cn.exrick.xboot.modules.indoor.topo.enums.DeviceStatus;
import cn.exrick.xboot.support.retn.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopoService {

	@Autowired
	private TopoNodesService topoNodesService;
	@Autowired
	private TopoLinksService topoLinksService;
	@Autowired
	private IMonitoringHostService monitoringHostService;
	@Autowired
	private IAntennaService antennaService;

	private static final String Probe_status_ok = "01";
	private static final String Probe_status_err = "00";
	
	
	@Transactional
	public Boolean saveOrUpdateTopo(String hostNumber, List<TopoNodes> topoNodes, List<TopoLinks> topoLinks) {
		
		LambdaQueryWrapper<MonitoringHost> lqwUnit = new LambdaQueryWrapper<MonitoringHost>();
		lqwUnit.eq(MonitoringHost::getHostNumber, hostNumber);
		MonitoringHost host = monitoringHostService.getByHostNumber(hostNumber);
		
		for(TopoNodes node : topoNodes) {
			node.setLineId(host.getLineId());
			node.setLineName(host.getLineName());
			node.setStationId(host.getStationId());
			node.setStationName(host.getStationName());
		}
		try {
			LambdaQueryWrapper<TopoNodes> lqwNodes = new LambdaQueryWrapper<TopoNodes>();
			lqwNodes.eq(TopoNodes::getHostNumber, hostNumber);
			topoNodesService.remove(lqwNodes);
			
			LambdaQueryWrapper<TopoLinks> lqwLinks = new LambdaQueryWrapper<TopoLinks>();
			lqwLinks.eq(TopoLinks::getHostNumber, hostNumber);
			topoLinksService.remove(lqwLinks);
			
			topoNodesService.saveBatch(topoNodes);
			topoLinksService.saveBatch(topoLinks);
			return Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
	
	public Map<String, Object> getTopo(String hostNumber) {
		LambdaQueryWrapper<TopoNodes> lqwNodes = new LambdaQueryWrapper<TopoNodes>();
		lqwNodes.eq(TopoNodes::getHostNumber, hostNumber);
		List<TopoNodes> nodes = topoNodesService.list(lqwNodes);
		
		if(nodes.size() <= 0) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("nodes", new ArrayList<TopoNodes>());
			result.put("links", new ArrayList<TopoLinks>());
			return result;
		}
		
		LambdaQueryWrapper<Antenna> lqwAnt = new LambdaQueryWrapper<Antenna>();
		lqwAnt.eq(Antenna::getHostNumber, hostNumber);
		List<Antenna> antList = antennaService.list(lqwAnt);
		
		for(Antenna ant : antList) {
			for(TopoNodes node : nodes) {
				boolean matched = false;
				if(ant.getCode().toLowerCase().equals( StringUtils.defaultIfBlank(node.getRfidlabel(), "").toLowerCase())) {
					matched = true;
				}else if(StringUtils.isNotBlank(node.getName()) && node.getName().endsWith(ant.getSid())) {
					node.setRfidlabel(ant.getCode());
					matched = true;
				}
				if(matched) {
					if(ant.getProbeStatus().equals(Probe_status_err)) {
						node.setState(DeviceStatus.red.getState());
					}else if(ant.getProbeStatus().equals(Probe_status_ok)) {
						node.setState(DeviceStatus.green.getState());
					}else {
						node.setState(DeviceStatus.gray.getState());
					}
					break;
				}
			}
		}
		
		LambdaQueryWrapper<TopoLinks> lqwLinks = new LambdaQueryWrapper<TopoLinks>();
		lqwLinks.eq(TopoLinks::getHostNumber, hostNumber);
		List<TopoLinks> links = topoLinksService.list(lqwLinks);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("nodes", nodes);
		result.put("links", links);
		
		return result;
	}

	/**
	 * 根据监控主机编号删除topo信息
	 * @param hostNumber 监控主机编号
	 * @return 是否删除成功
	 */
	public Result<Boolean> removeTopoByHostNumber(String hostNumber){
		LambdaQueryWrapper<TopoNodes> lqwNodes = new LambdaQueryWrapper<>();
		lqwNodes.eq(TopoNodes::getHostNumber,hostNumber);
		boolean a = topoNodesService.remove(lqwNodes);

		LambdaQueryWrapper<TopoLinks> lqwLinks = new LambdaQueryWrapper<>();
		lqwLinks.eq(TopoLinks::getHostNumber,hostNumber);
		boolean b = topoLinksService.remove(lqwLinks);

		if( a && b){
			return Result.succeed(true,"删除成功");
		}
		return Result.failed(false,"删除失败");
	}
	
}
