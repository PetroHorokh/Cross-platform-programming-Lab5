package models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Student{
    private String name;
    private Course course;

    public Student(String name, Course course){
        this.name = name;
        this.course = course;
    }

    @Override
    public String toString() {
        return STR."\{getName()}\{','}\{course.speciality()}\{','}\{course.getUniversity()}";
    }
}
