package com.codessquad.qna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    private String dateTime;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @OrderBy("id DESC")
    @JsonIgnore
    private List<Answer> answers;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    public Question() {}

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void save(User user) {
        this.writer = user;
        this.dateTime = LocalDateTime.now().format(dateTimeFormatter);
    }

    public void update(Question quesion) {
        this.title = quesion.getTitle();
        this.contents = quesion.getContents();
    }

    public boolean delete() {
        if (verifyAnswers() != 0) {
            return false;
        }
        this.deleted = true;
        answers.forEach(Answer::delete);
        return true;
    }

    public int verifyAnswers() {
        return (int) answers.stream()
                .filter(answer -> !answer.isDeleted() && !answer.matchWriter(writer))
                .count();
    }

    public boolean matchWriter(User user) {
        return user.matchUserId(writer.getUserId());
    }

    public int getAnswersCount() {
        return (int) answers.stream()
                .filter(answer -> !answer.isDeleted())
                .count();
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer=" + writer +
                ", dateTime='" + dateTime + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
