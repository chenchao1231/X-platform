package cn.exrick.xboot.modules.indoor.basedata.service;

import cn.exrick.xboot.modules.indoor.basedata.entity.IndoorStation;
import cn.exrick.xboot.support.req.PageRequest;
import cn.exrick.xboot.support.retn.PageResult;
import cn.exrick.xboot.support.retn.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author chenchao
 */
public interface IIndoorStationService extends IService<IndoorStation> {

    /**
     * 分页查询室分站点列表
     * @param pageRequest 室分站点前端传入的参数
     * @return 分页列表
     */
    PageResult<IndoorStation> getPageSiteInfo(PageRequest<IndoorStation> pageRequest);

    /**
     * 查询当前室分站点详细信息
     * @param id 当前室分站点的主键ID
     * @return 室分站点Detail
     */
    Result<IndoorStation> detailById(String id);

    /**
     * 删除室分站点
     * 当室分站点下存在监控主机则阻止删除,需要手动清空监控主机信息才能删除室分站点
     * @param id 当前室分站点的主键ID
     * @return 是否删除成功
     */
    Result<Boolean> deleteById(String id);

    /**
     * 新增室分站点
     * @param indoorStation 室分站点信息
     * @return 是否保存成功
     */
    Result<Boolean> saveIndoorStation(IndoorStation indoorStation);
    /**
     * 更新室分站点
     * @param indoorStation 室分站点信息
     * @return 是否更新成功
     */
    Result<Boolean> updateIndoorStation(IndoorStation indoorStation);
}
