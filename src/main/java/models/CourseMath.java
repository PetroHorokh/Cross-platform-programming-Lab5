package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseMath extends Course {

    private boolean computerScience;

    public CourseMath(String university,
                      boolean online,
                      int duration,
                      String subjects,
                      boolean computerScience)
    {
        super(university, online, duration, subjects);
        this.computerScience = computerScience;
    }

    @Override
    public String speciality() {
        return "Math";
    }

    @Override
    public String toString() {
        return STR."\{speciality()},\{getUniversity()},\{isOnline()},\{getDuration()},\{getSubjects()},\{isComputerScience()}";
    }

    @Override
    public String moto() {
        return "1 + 1 is enough. So lets do even more!";
    }
}
