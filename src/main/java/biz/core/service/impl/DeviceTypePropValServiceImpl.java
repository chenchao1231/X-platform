package biz.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.key.win.biz.core.dao.DeviceTypePropValVoDao;
import com.key.win.biz.core.service.IDeviceTypePropValService;
import com.key.win.biz.core.vo.DeviceTypePropValVO;
import org.springframework.stereotype.Service;

@Service
public class DeviceTypePropValServiceImpl
    extends ServiceImpl<DeviceTypePropValVoDao, DeviceTypePropValVO>
    implements IDeviceTypePropValService {}
