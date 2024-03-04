package biz.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.biz.core.dao.QDeviceInfoDao;
import com.key.win.biz.core.model.QDeviceInfo;
import com.key.win.biz.core.service.IDeviceInfoService;
import org.springframework.stereotype.Service;

@Service
public class QDeviceInfoServiceImpl extends ServiceImpl<QDeviceInfoDao, QDeviceInfo>
    implements IDeviceInfoService {}
