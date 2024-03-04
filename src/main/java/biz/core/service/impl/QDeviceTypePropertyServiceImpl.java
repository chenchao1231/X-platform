package biz.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.biz.core.dao.QDeviceTypePropertyDao;
import com.key.win.biz.core.model.QDeviceTypeProperty;
import com.key.win.biz.core.service.IDeviceTypePropertyService;
import org.springframework.stereotype.Service;

@Service
public class QDeviceTypePropertyServiceImpl
    extends ServiceImpl<QDeviceTypePropertyDao, QDeviceTypeProperty>
    implements IDeviceTypePropertyService {}
