package cn.exrick.xboot.modules.indoor.basedata.service.impl;

import cn.exrick.xboot.modules.indoor.basedata.entity.IndoorStation;
import cn.exrick.xboot.modules.indoor.basedata.entity.MonitoringHost;
import cn.exrick.xboot.modules.indoor.basedata.mapper.IndoorStationMapper;
import cn.exrick.xboot.modules.indoor.basedata.mapper.MonitoringHostMapper;
import cn.exrick.xboot.modules.indoor.basedata.service.IIndoorStationService;
import cn.exrick.xboot.support.req.MybatiesPageServiceTemplate;
import cn.exrick.xboot.support.req.PageRequest;
import cn.exrick.xboot.support.retn.PageResult;
import cn.exrick.xboot.support.retn.Result;
import cn.exrick.xboot.support.retn.ResultCode;
import cn.exrick.xboot.support.utils.SpringBeanUtilsExt;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenchao
 */
@Service
public class IndoorStationServiceImpl extends ServiceImpl<IndoorStationMapper, IndoorStation> implements IIndoorStationService {

    @Autowired
    private MonitoringHostMapper monitoringHostMapper;
    @Override
    public PageResult<IndoorStation> getPageSiteInfo(PageRequest<IndoorStation> pageRequest) {
        MybatiesPageServiceTemplate<IndoorStation, IndoorStation> page = new MybatiesPageServiceTemplate<IndoorStation, IndoorStation>(this.baseMapper) {
            @Override
            protected Wrapper<IndoorStation> constructWrapper(IndoorStation indoorStation) {
                LambdaQueryWrapper<IndoorStation> lqw = new LambdaQueryWrapper<>();
                if (indoorStation == null) {
                    return lqw;
                }
                if (StringUtils.isNotBlank(indoorStation.getStationName())) {
                    lqw.like(IndoorStation::getStationName, indoorStation.getStationName());
                }
                return lqw;
            }
        };
        PageResult<IndoorStation> pageDataResult = page.doPagingQuery(pageRequest);
        return pageDataResult;
    }

    @Override
    public Result<IndoorStation> detailById(String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return Result.failed("id参数为空");
            }
            IndoorStation indoorStation = getById(id);
            if (ObjectUtil.isNotEmpty(indoorStation)) {
                return Result.succeed(indoorStation, "查询成功");
            } else {
                return Result.failed(null,"查询失败");
            }
        } catch (Exception e) {
            return Result.failed("未知异常:" + e.getMessage() );
        }
    }


    @Override
    public Result<Boolean> deleteById(String id) {
        try {
            if (StringUtils.isBlank(id)) {
                return Result.failed("id参数为空");
            }
            //站点下存在主机需要先删除主机才可以删除站点
            LambdaQueryWrapper<MonitoringHost> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(MonitoringHost::getPid,id);
            List<MonitoringHost> belongUnits = monitoringHostMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(belongUnits)) {
                boolean deleteById = removeById(id);
                if (deleteById) {
                    return Result.succeed("删除成功");
                } else {
                    return Result.failed("删除失败");
                }
            }else {
                return Result.failed(ResultCode.SF_BELONG_UNIT_DELETE_FILE.getCode(),ResultCode.SF_BELONG_UNIT_DELETE_FILE.getMessage());
            }
        } catch (Exception e) {
            return Result.failed("未知异常:" + e.getMessage());
        }
    }

    @Override
    public Result<Boolean> saveIndoorStation(IndoorStation indoorStation) {
        try {
            if (ObjectUtil.isEmpty(indoorStation)) {
                return Result.failed(false,"保存参数为空");
            }

            if (StringUtils.isNotEmpty(indoorStation.getStationId())) {
                return Result.failed(false,"保存操作检测到已经存在的ID");
            }

            String stationId = indoorStation.getStationId();
            String lineId = indoorStation.getLineId();

            LambdaQueryWrapper<IndoorStation> lqw = new LambdaQueryWrapper<>();
            lqw.eq(IndoorStation::getStationId,stationId);
            lqw.eq(IndoorStation::getLineId,lineId);

            IndoorStation dbIndoorStation = this.getOne(lqw);
            if(ObjectUtil.isNotEmpty(dbIndoorStation)){
                return Result.failed(false,"重复的室分站点信息");
            }
            boolean saveOrUpdate = save(indoorStation);
            if (saveOrUpdate) {
                return Result.succeed("保存成功");
            } else {
                return Result.failed("保存失败");
            }
        } catch (Exception e) {
            return Result.failed("未知异常:" + e.getMessage());
        }
    }


    @Override
    public Result<Boolean> updateIndoorStation(IndoorStation indoorStation) {
        try {
            if (ObjectUtil.isEmpty(indoorStation)) {
                return Result.failed(false,"更新的信息不存在");
            }
            if (StringUtils.isEmpty(indoorStation.getStationId())) {
                return Result.failed(false,"更新信息的ID不存在");
            }

            IndoorStation dbIndoorStation = getById(indoorStation);
            if(ObjectUtil.isEmpty(dbIndoorStation)){
                return Result.failed(false,"数据库中没有检索到ID对应的实体信息");
            }
            SpringBeanUtilsExt.copyProperties(indoorStation,dbIndoorStation);

            boolean saveFlag = save(dbIndoorStation);
            if (saveFlag) {
                return Result.succeed("保存成功");
            } else {
                return Result.failed("保存失败");
            }
        } catch (Exception e) {
            return Result.failed("未知异常:" + e.getMessage());
        }
    }
}
