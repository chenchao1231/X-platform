package cn.exrick.xboot.modules.indoor.basedata.service;

import cn.exrick.xboot.modules.indoor.basedata.entity.MetroLine;
import cn.exrick.xboot.modules.indoor.basedata.entity.MetroStation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author chenchao
 */
public interface IMetroLineService extends IService<MetroLine> {

    /**
     * 根据线路编号查询车站列表
     * @param lineId 线路编号 如:01 02 03
     * @return 车站列表
     */
    public List<MetroStation> getStationsByLineId(String lineId);


    /**
     * 返回线路与站点的树节点
     * @return elementUi 所需要的tree结构
     */
    public List<Map<String,Object>> queryLineStationTree();

}


