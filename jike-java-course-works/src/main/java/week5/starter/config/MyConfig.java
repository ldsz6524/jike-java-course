package week5.starter.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangbiao
 * @date 2021/4/16
 */
@Setter
@Getter
@ToString
@Configuration
@ConfigurationProperties(prefix = "my")
public class MyConfig {
//    private Properties props = new Properties();
    private int id;
    private String name;
    private String beanName;
}
