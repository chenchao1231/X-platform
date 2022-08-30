package cn.exrick.xboot.modules.indoor.basedata.controller;

import cn.exrick.xboot.modules.indoor.basedata.entity.IndoorStation;
import cn.exrick.xboot.modules.indoor.basedata.service.IIndoorStationService;
import cn.exrick.xboot.support.req.PageRequest;
import cn.exrick.xboot.support.retn.PageResult;
import cn.exrick.xboot.support.retn.Result;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Chenchao
 */
@Slf4j
@RestController
@Api(tags = "室分站点接口")
@RequestMapping("/indoor/basedata/indoorStations/*")
public class IndoorStationController {

    @Autowired
    private IIndoorStationService indoorStationService;

    /**
     * 分页查询室分站点列表
     * @param pageRequest 前台传入的分页参数
     * @return 分页室分站点列表
     */
    @PostMapping("/queryPageInfo")
    public PageResult<IndoorStation> pageSiteInfo(@RequestBody PageRequest<IndoorStation> pageRequest) {
        return indoorStationService.getPageSiteInfo(pageRequest);
    }

    /**
     * 查询室分站点详情
     * @param id 室分站点主键ID
     * @return 室分站点detail
     */
    @PostMapping("/details/{id}")
    public Result<IndoorStation> queryById(@PathVariable("id") String id) {
        return indoorStationService.detailById(id);
    }

    /**
     * 更新或者保存信息
     * @param indoorStation 更新或者保存的传入对象
     * @return 是否执行成功
     */
    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdateSiteInfo(@RequestBody IndoorStation indoorStation) {
        if(ObjectUtil.isNotEmpty(indoorStation)){
            if(StringUtils.isNotBlank(indoorStation.getId())){
                return indoorStationService.updateIndoorStation(indoorStation);
            }else {
                return indoorStationService.saveIndoorStation(indoorStation);
            }
        }
        return Result.failed(false,"传入的信息不存在.");
    }

    /**
     * 删除室分站点信息
     * @param id 主键ID
     * @return 是否删除成功
     */
    @PostMapping("/deleteById/{id}")
    public Result<Boolean> deleteSiteInfo(@PathVariable("id") String id) {
        return indoorStationService.deleteById(id);
    }

}
