package cn.exrick.xboot.modules.indoor.basedata.entity;


import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author chenchao
 */
@Data
@TableName("t_biz_indoor_antenna")
public class Antenna extends XbootBaseEntity {


    /**
     * 监控主机中存入的探针序号
     * 该序号与topo图中的 ANT（X）保持对应
     * 根据序号来绑定探针与天线的对应关系
     */
    private String sid;

    /**
     * 探针编号:24位随机编号
     */
    private String code;

    /**
     * 原始探针编号,对于未满24位编号需要用0填充的编号,此时的编号存储还暂未填充0时的原始手动录入探针编号
     * eg: 21072814  原始编号
     * 000000000000000021072814 填充后的
     * 那么此oCoding 代表的则是 原始的21072814.
     * 对于000000000000000021072814 这个编号则是保存在 coding 属性中
     */
    private String oCode;

    /**
     * 监控主机编号
     * eg:88883333
     */
    private String hostNumber;

    /**
     * 天线状态
     */
    private String probeStatus;

    /**
     * 路损值
     */
    private int lost;
}
