package cn.exrick.xboot.modules.indoor.mqtt.messager.packer;

/**
 * description
 *
 * @author 蔺扬
 * createTime 2022年7月19日-16:13:53
 */
public interface NetworkMessagePacker {
    
    /**
     * Returns the packer id
     * 
     * @return The packerId is being returned.
     */
    String getPackerId();

    
}
