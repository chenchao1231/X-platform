package cn.exrick.xboot.modules.indoor.mqtt.emqx;

import cn.exrick.xboot.modules.indoor.dbconfig.entity.MqDbConfig;
import cn.exrick.xboot.modules.indoor.dbconfig.enums.MqDbConfigStatus;
import cn.exrick.xboot.modules.indoor.dbconfig.service.IMqDbConfigService;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EmqxHttpHelper implements InitializingBean {
    private static final String lookup_a_client_in_the_cluster = "/api/v4/clients/";

    @Autowired
    private IMqDbConfigService mqDbConfigService;

    private static String emqBaseUrl = "";

    private static void setEmqBaseUrl(String emqBaseUrl) {
        EmqxHttpHelper.emqBaseUrl = emqBaseUrl;
    }

    public static String getEmqBaseUrl() {
        return emqBaseUrl;
    }

    /**
     * 查看设备是否在线
     * @param clientId
     * @return
     */
    public boolean clientIsOnline(String clientId) {

        String uname = "admin";
        String pwd = "public";
        String up = uname + ":" + pwd;
        byte[] encodeBase64 = Base64.encodeBase64(up.getBytes());
        String base64UserMsg = new String(encodeBase64);
        String authorization = "Basic "+base64UserMsg;
        String contentType = "application/x-www-form-urlencoded";

        String resourceUrl = lookup_a_client_in_the_cluster + clientId;
        String url = emqBaseUrl + resourceUrl;

        HttpRequest httpRequest = new HttpRequest(url);
        httpRequest.header(Header.AUTHORIZATION, authorization);
        httpRequest.header(Header.CONTENT_TYPE , contentType);
        HttpResponse response = httpRequest.execute();
        if(response.getStatus() == HttpStatus.HTTP_OK) {
            String rsponseString = response.body();
            Map rsponseMap = JSON.parseObject(rsponseString, Map.class);
            Integer code = MapUtil.getInt(rsponseMap, "code");

            String dataString = MapUtil.getStr(rsponseMap, "data");
            List<Map> dataList = JSON.parseArray(dataString, Map.class);

            if(dataList.size() == 0) {
                return false;
            }

            if(dataList.size() == 1) {
                Boolean isConnected = MapUtil.getBool(dataList.get(0), "connected");
                return isConnected;
            }
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LambdaQueryWrapper<MqDbConfig> lqwMqDbConfig = new LambdaQueryWrapper<>();
        lqwMqDbConfig.eq(MqDbConfig::getEnable, MqDbConfigStatus.ENABLED.getStatusCode());
        MqDbConfig dbConfigBean = mqDbConfigService.getOne(lqwMqDbConfig);
        setEmqBaseUrl(dbConfigBean.getUrl().replace("tcp://", "http://") + ":" + "18083");
        log.info("Emqx_API服务地址为:{}",getEmqBaseUrl() + lookup_a_client_in_the_cluster);
    }



}
