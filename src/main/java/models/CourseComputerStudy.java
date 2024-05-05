package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseComputerStudy extends Course {

    private boolean devicesProvided;

    public CourseComputerStudy(String university,
                               boolean online,
                               int duration,
                               String subjects,
                               boolean devicesProvided)
    {
        super(university, online, duration, subjects);
        this.devicesProvided = devicesProvided;
    }

    @Override
    public String moto() {
        return "Computers are fiends";
    }

    @Override
    public String speciality() {
        return "Computer study";
    }

    @Override
    public String toString() {
        return STR."\{speciality()},\{getUniversity()},\{isOnline()},\{getDuration()},\{getSubjects()},\{isDevicesProvided()}";
    }
}
