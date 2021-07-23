package week5;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author:
 * @create:
 * @other:
 **/
public class TestSpringBean {
    @Bean
    public People people() {
        People people = new People();
        people.setId(11);
        people.setAge(22);
        people.setName("John");
        return people;
    }

    @Setter
    @Getter
    @ToString
    public class People {
        private int id;
        private int age;
        private String name;
    }
}
