package cn.exrick.xboot.modules.indoor.basedata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.exrick.xboot.modules.indoor.basedata.entity.MonitoringHost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 监测主机数据处理层
 * @author Chenchao
 */
@Mapper
public interface MonitoringHostMapper extends BaseMapper<MonitoringHost> {

}