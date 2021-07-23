package week5.starter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import week5.starter.config.MyConfig;
import week5.starter.entity.Klass;
import week5.starter.entity.School;
import week5.starter.entity.Student;
import week5.starter.service.MyTemplate;

import java.util.Arrays;

/**
 * @author yangbiao
 * @date 2021/4/16
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(MyConfig.class)
@ConditionalOnClass({MyConfig.class, Student.class})
@ConditionalOnProperty(prefix = "my", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MyAutoConfiguration {

    /**
     * 获取Student
     *
     * @param myConfig
     * @return
     */
    @Bean("student100")
    @ConditionalOnBean(MyConfig.class)
    public Student student(MyConfig myConfig) {
        Student student = new Student();
        student.setId(myConfig.getId());
        student.setName(myConfig.getName());
        student.setBeanName(myConfig.getBeanName());
        return student;
    }

    /**
     * 获取Klass
     *
     * @param myConfig
     * @return
     */
    @Bean
    @ConditionalOnBean(Student.class)
    public Klass klass(MyConfig myConfig) {
        Klass klass = new Klass();
        log.info("beanName把配置文件里配置的名称替换为创建bean时的真实名称了");
        Student student = student(myConfig);
        klass.setStudents(Arrays.asList(student));
        return klass;
    }

    /**
     * 获取School
     *
     * @return
     */
    @Bean
    @ConditionalOnBean(Klass.class)
    public School school() {
        return new School();
    }

    /**
     * 注意因为需要对应的bean,所以需要先让其创建
     *
     * @param myConfig
     * @return
     */
    @Bean
    @ConditionalOnBean(School.class)
    public MyTemplate myTemplate(MyConfig myConfig) {
        log.info("MyConfig={}", myConfig);
        return new MyTemplate(klass(myConfig), school(), student(myConfig));
    }

}
