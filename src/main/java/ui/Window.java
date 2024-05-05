package ui;

import models.Course;
import models.CourseComputerStudy;
import models.CourseMath;
import models.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Window extends JFrame {
    private final DefaultListModel<Course> courseListModel;
    private final DefaultListModel<Student> studentListModel;
    private final JList<Course> courses;
    private final JList<Student> students;

    public Window() {
        setTitle("Extract");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 400);
        setLocationRelativeTo(null);

        courseListModel = new DefaultListModel<>();
        studentListModel = new DefaultListModel<>();
        courses = new JList<>(courseListModel);
        students = new JList<>(studentListModel);

        JScrollPane courseScrollPane = new JScrollPane(courses);
        JScrollPane studentScrollPane = new JScrollPane(students);

        JButton PostCourseButton = new JButton("Add course");
        PostCourseButton.addActionListener(_ -> addCourse());

        JButton PostStudentButton = new JButton("Add student");
        PostStudentButton.addActionListener(_ -> addStudent());

        JButton PutStudentButton = new JButton("Change student speciality");
        PutStudentButton.addActionListener(_ -> changeSpeciality());

        JButton DeleteStudentButton = new JButton("Delete student");
        DeleteStudentButton.addActionListener(_ -> deleteStudent());

        JButton InvokeStudentButton = new JButton("Let student say moto");
        InvokeStudentButton.addActionListener(_ -> invokeStudent());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(PostCourseButton);
        buttonPanel.add(PostStudentButton);
        buttonPanel.add(PutStudentButton);
        buttonPanel.add(DeleteStudentButton);
        buttonPanel.add(InvokeStudentButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(studentScrollPane, BorderLayout.CENTER);
        getContentPane().add(courseScrollPane, BorderLayout.SOUTH);
        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveDataToFile();
            }
        });

        readDataFromFile();
    }

    private void addCourse() {
        String[] options = {"Computer study", "Math"};
        String selectedCourseType = (String) JOptionPane.showInputDialog(this, "Select course type",
                "Add course", JOptionPane.PLAIN_MESSAGE, null, options, "Computer study");

        Course newCourse = CourseData(selectedCourseType);

        if(newCourse != null){
            courseListModel.addElement(newCourse);
        }
    }
    private void addStudent() {
        List<Course> options = new ArrayList<>();

        for (int i = 0; i < courseListModel.size(); i++) {
            options.add(courseListModel.getElementAt(i));
        }

        List<String> universities = new ArrayList<>();

        for (Course course : options) {
            universities.add(course.getUniversity());
        }

        Set<String> set = new LinkedHashSet<>(universities);

        universities.clear();
        universities.addAll(set);

        String selectedUniversity = (String) JOptionPane.showInputDialog(this, "Select university",
                "Select course", JOptionPane.PLAIN_MESSAGE, null, universities.toArray(), universities.toArray()[0]);

        List<String> courseList = new ArrayList<>();

        for (Course course : options) {
            if (course.getUniversity().equals(selectedUniversity)) {
                courseList.add(course.speciality());
            }
        }

        String selectedCourse = (String) JOptionPane.showInputDialog(this, "Select course",
                "Select course", JOptionPane.PLAIN_MESSAGE, null, courseList.toArray(), courseList.toArray()[0]);

        Student newStudent = StudentData(selectedUniversity, selectedCourse);

        if(newStudent != null){
            studentListModel.addElement(newStudent);
        }
    }
    private void deleteStudent() {
        try {
            Student student = students.getSelectedValue();
            if (student == null) {
                JOptionPane.showMessageDialog(this, "Please select a student.",
                        "Change speciality", JOptionPane.WARNING_MESSAGE);
                return;
            }

            studentListModel.removeElement(student);

            JOptionPane.showMessageDialog(this, "Student successfully deleted.",
                    "Change speciality", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception _) {
            System.err.println("Something went wrong deleting student");
        }

        students.revalidate();
        students.repaint();
    }
    private void changeSpeciality() {
        Student student = students.getSelectedValue();
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Please select a student.",
                    "Change speciality", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Course> options = new ArrayList<>();

        for (int i = 0; i < courseListModel.size(); i++) {
            options.add(courseListModel.getElementAt(i));
        }

        List<String> universities = new ArrayList<>();

        for (Course course : options) {
            universities.add(course.getUniversity());
        }

        Set<String> set = new LinkedHashSet<>(universities);

        universities.clear();
        universities.addAll(set);

        String selectedUniversity = (String) JOptionPane.showInputDialog(this, "Select university",
                "Select course", JOptionPane.PLAIN_MESSAGE, null, universities.toArray(), universities.toArray()[0]);

        List<String> courseList = new ArrayList<>();

        for (Course course : options) {
            if (course.getUniversity().equals(selectedUniversity)) {
                courseList.add(course.speciality());
            }
        }

        String selectedCourse = (String) JOptionPane.showInputDialog(this, "Select course",
                "Select course", JOptionPane.PLAIN_MESSAGE, null, courseList.toArray(), courseList.toArray()[0]);

        Course newCourse = null;

        for (Course course : options) {
            if (course.getUniversity().equals(selectedUniversity) && course.speciality().equals(selectedCourse)) {
                newCourse = course;
            }
        }

        student.setCourse(newCourse);

        students.revalidate();
        students.repaint();
    }
    private void invokeStudent() {
        try {
            Student student = students.getSelectedValue();

            if (student == null) {
                JOptionPane.showMessageDialog(this, "Please select a student.",
                        "Change speciality", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, student.getCourse().moto(),
                    "Change speciality", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception _) {
            System.err.println("Something went wrong while student saying moto");
        }
    }

    private Course CourseData(String courseType) {
        String university;

        university = JOptionPane.showInputDialog(this, "Enter university: ");
        if (university == null) {
            JOptionPane.showMessageDialog(this, "University cannot be unspecified.", "Add course", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        for (int i = 0; i < courseListModel.size(); i++) {
            if(courseListModel.getElementAt(i).speciality().equals(courseType) && courseListModel.getElementAt(i).getUniversity().equals(university)){
                JOptionPane.showMessageDialog(this, "Such speciality already exists for given university.", "Add course", JOptionPane.WARNING_MESSAGE);
                return null;
            }
        }

        boolean online;
        Object[] options1 = {true, false};

        try {
            online = (boolean) JOptionPane.showInputDialog(this, "Online?",
                    null, JOptionPane.QUESTION_MESSAGE, null, options1, false);
        } catch (Exception _) {
            JOptionPane.showMessageDialog(this, "Format cannot be specified.", "Add course", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String durationString;
        int duration;

        do {
            durationString = JOptionPane.showInputDialog(this, "Enter amount of years:");
            if (durationString == null || durationString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hours cannot be unspecified.", "Add course", JOptionPane.WARNING_MESSAGE);
                return null;
            } else {
                duration = Integer.parseInt(durationString);
            }
        } while (duration <= 0 || duration > 4);

        String subjects;

        subjects = JOptionPane.showInputDialog(this, "Enter subjects: ");
        if (subjects == null || subjects.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Subjects cannot be unspecified.", "Add course", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        switch (courseType) {
            case "Computer study":
                boolean deviceProvided;

                try {
                    deviceProvided = (boolean) JOptionPane.showInputDialog(this, "Are devices provided",
                            null, JOptionPane.QUESTION_MESSAGE, null, options1, false);
                } catch (Exception _) {
                    JOptionPane.showMessageDialog(this, "Providing of devices cannot be specified.", "Add course", JOptionPane.WARNING_MESSAGE);
                    return null;
                }
                return new CourseComputerStudy(university, online, duration, subjects, deviceProvided);

            case "Math":
                boolean computerScience;

                try {
                    computerScience = (boolean) JOptionPane.showInputDialog(this, "Is computer science included",
                            null, JOptionPane.QUESTION_MESSAGE, null, options1, false);
                } catch (Exception _) {
                    JOptionPane.showMessageDialog(this, "Computer science includence cannot be specified.", "Add course", JOptionPane.WARNING_MESSAGE);
                    return null;
                }
                return new CourseMath(university, online, duration, subjects, computerScience);
        }

        return null;
    }
    private Student StudentData(String university, String courseType) {
        String name;

        name = JOptionPane.showInputDialog(this, "Enter name: ");
        if (name == null) {
            JOptionPane.showMessageDialog(this, "Name cannot be unspecified.", "Add course", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        Course course = null;

        for (int i = 0; i < courseListModel.size(); i++) {
            if (courseListModel.getElementAt(i).toString().split(",")[0].equals(courseType)
                    && courseListModel.getElementAt(i).toString().split(",")[1].equals(university)) {
                course = courseListModel.getElementAt(i);
                break;
            }
        }

        if (course == null) {
            JOptionPane.showMessageDialog(this, "Course cannot be unspecified.", "Add course", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return new Student(name, course);

    }

    private void saveDataToFile() {

        String filenameCourses = "courses.txt";
        String filenameStudents = "students.txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filenameCourses));

            for (int i = 0; i < courseListModel.size(); i++) {
                writer.write(courseListModel.getElementAt(i).toString());
                writer.newLine();
            }

            writer.close();
            System.out.println(STR."Data saved to file \{filenameCourses}");
        } catch (IOException e) {
            System.err.println(STR."An error occurred while saving data to file\{filenameCourses}");
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filenameStudents));

            for (int i = 0; i < studentListModel.size(); i++) {
                writer.write(studentListModel.getElementAt(i).toString());
                writer.newLine();
            }

            writer.close();
            System.out.println(STR."Data saved to file \{filenameStudents}");
        } catch (IOException e) {
            System.err.println(STR."An error occurred while saving data to file\{filenameStudents}");
        }
    }
    private void readDataFromFile() {
        courseListModel.clear();
        studentListModel.clear();

        String filenameCourses = "courses.txt";
        String filenameStudent = "students.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filenameCourses))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String university = parts[1];
                boolean online = Boolean.parseBoolean(parts[2]);
                int duration = Integer.parseInt(parts[3]);
                String subjects = parts[4];
                Course course;

                switch (parts[0]) {
                    case "Computer study":
                        boolean devicesProvided = Boolean.parseBoolean(parts[5]);
                        course = new CourseComputerStudy(university, online, duration, subjects, devicesProvided);
                        courseListModel.addElement(course);
                        break;
                    case "Math":
                        boolean computerScience = Boolean.parseBoolean(parts[5]);
                        course = new CourseMath(university, online, duration, subjects, computerScience);
                        courseListModel.addElement(course);
                        break;
                }
            }

            System.out.println(STR."Data loaded from file \{filenameCourses}");

            courses.revalidate();
            courses.repaint();
        } catch (IOException e) {
            System.err.println(STR."An error occured while loading data from file \{filenameCourses}");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filenameStudent))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String name = parts[0];
                String speciality = parts[1];
                String university = parts[2];
                Course course;

                for (int i = 0; i < courseListModel.size(); i++) {
                    if (courseListModel.getElementAt(i).speciality().equals(speciality) && courseListModel.getElementAt(i).getUniversity().equals(university)) {
                        course = courseListModel.getElementAt(i);
                        studentListModel.addElement(new Student(name, course));
                        break;
                    }
                }
            }
            System.out.println(STR."Data loaded from file \{filenameStudent}");
            students.revalidate();
            students.repaint();
        } catch (IOException e) {
            System.err.println(STR."An error occured while loading data from file \{filenameStudent}");
        }
    }
}
