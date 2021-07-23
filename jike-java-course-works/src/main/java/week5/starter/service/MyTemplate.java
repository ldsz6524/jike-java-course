package week5.starter.service;


import lombok.RequiredArgsConstructor;
import week5.starter.entity.Klass;
import week5.starter.entity.School;
import week5.starter.entity.Student;

/**
 * @author yangbiao
 * @date 2021/4/16
 */
@RequiredArgsConstructor
public class MyTemplate {
    private final Klass klass;
    private final School school;
    private final Student student;

    public String getYb() {
        return "Hello Yb";
    }

    /**
     * 获取ID
     *
     * @return
     */
    public int getId() {
        return student.getId();
    }

    /**
     * 获取名称
     *
     * @return
     */
    public String getName() {
        return student.getName();
    }

    /**
     * 获取Bean的名称
     *
     * @return
     */
    public String getBeanName() {
        return student.getBeanName();
    }

    public void print() {
        student.print();
    }

    public void ding() {
        school.ding();
    }

    public void dong() {
        klass.dong();
    }

}
