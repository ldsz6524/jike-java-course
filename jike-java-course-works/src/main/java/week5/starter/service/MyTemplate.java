package week5.starter.service;


import lombok.RequiredArgsConstructor;
import week5.starter.entity.Klass;
import week5.starter.entity.School;
import week5.starter.entity.Student;


@RequiredArgsConstructor
public class MyTemplate {
    private final Klass klass;
    private final School school;
    private final Student student;

    public String getYb() {
        return "Hello Yb";
    }


    public int getId() {
        return student.getId();
    }


    public String getName() {
        return student.getName();
    }


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
