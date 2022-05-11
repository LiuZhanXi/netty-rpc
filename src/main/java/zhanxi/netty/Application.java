package zhanxi.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Classname Application
 * @Description 启动类
 * @Date 2022/5/9 17:43
 * @Email zhanxi.liu@seaboxdata.com
 * @Author liuzhanxi
 */
@SpringBootApplication(scanBasePackages = {"zhanxi.netty"})
@EnableConfigurationProperties
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}
