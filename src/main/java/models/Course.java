package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.List;

@Getter
@Setter
public abstract class Course{
    private String university;
    private boolean online;
    private int duration;
    private String subjects;

    public Course(String university,
                  boolean online,
                  int duration,
                  String subjects) {
        this.online = online;
        this.duration = duration;
        this.subjects = subjects;
        this.university = university;
    }

    public abstract String speciality();

    public abstract String toString();

    public abstract String moto();
}

