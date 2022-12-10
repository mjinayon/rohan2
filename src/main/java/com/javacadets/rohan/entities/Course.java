package com.javacadets.rohan.entities;

import com.javacadets.rohan.constants.RohanStatus;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courseId;
    @Column(unique = true, nullable = false)
    private String code;
    private String title;
    private String description;
    private String status;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Classs> classes;

    public Course() {}

    public Course(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.status = RohanStatus.ACTIVATED;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Classs> getClasses() {
        return classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return courseId == course.courseId && code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, code);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
