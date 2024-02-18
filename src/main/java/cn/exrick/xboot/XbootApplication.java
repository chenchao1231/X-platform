package cn.exrick.xboot;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Exrickx
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableAdminServer
@MapperScan({"cn.exrick.xboot.**.mapper","cn.exrick.xboot.**.dao"})
public class XbootApplication {
    public static void main(String[] args) {
        System.out.print("更新日期:20240218");
        SpringApplication.run(XbootApplication.class, args);
    }
}
