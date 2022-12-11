package com.javacadets.rohan.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javacadets.rohan.constants.RohanStatus;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
public class Classs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long classId;
    private double quizPercentage;
    private double exercisePercentage;
    private double projectPercentage;
    private double attendancePercentage;
    private int batch;
    private Date startDate;
    private Date endDate;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    Course course;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    SME sme;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "course_class_enroll",joinColumns = @JoinColumn(name="class_id", referencedColumnName = "classId"), inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "userId"))
    private Set<Student> students;

    @OneToMany(mappedBy = "classs", fetch = FetchType.LAZY)
    private Set<Quiz> quizzes;

    public Classs() {}

    public Classs(Course course, double quizPercentage, double exercisePercentage, double projectPercentage, double attendancePercentage, Date startDate, Date endDate, SME sme) {
        this.quizPercentage = quizPercentage;
        this.exercisePercentage = exercisePercentage;
        this.projectPercentage = projectPercentage;
        this.attendancePercentage = attendancePercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.course = course;
        this.sme = sme;
        if(course.getClasses().isEmpty()) {
            this.batch = 1;
        } else {
            this.batch = course.getClasses().size()+1;
        }
        this.status = RohanStatus.ACTIVATED;
    }

    public long getClassId() {
        return classId;
    }

    public double getQuizPercentage() {
        return quizPercentage;
    }

    public double getExercisePercentage() {
        return exercisePercentage;
    }

    public double getProjectPercentage() {
        return projectPercentage;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public int getBatch() {
        return batch;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public Course getCourse() {
        return course;
    }

    public SME getSme() {
        return sme;
    }

    public Set<Student> getStudents() {
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Classs classs)) return false;
        return classId == classs.classId && batch == classs.batch && course.equals(classs.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classId, batch, course);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Classs{" +
                "classId=" + classId +
                ", quizPercentage=" + quizPercentage +
                ", exercisePercentage=" + exercisePercentage +
                ", projectPercentage=" + projectPercentage +
                ", attendancePercentage=" + attendancePercentage +
                ", batch=" + batch +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", course=" + course +
                ", sme=" + sme +
                ", students=" + students +
                '}';
    }
}
