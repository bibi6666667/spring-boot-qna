package com.codessquad.qna.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String dateTime;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    public Answer() {}

    public Answer(String contents) {
        this.contents = contents;
    }

    public void save(User writer, Question question) {
        this.writer = writer;
        this.question = question;
        this.dateTime = LocalDateTime.now().format(dateTimeFormatter);
    }

    public void update(Answer answer) {
        this.contents = answer.contents;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean matchWriter(User user) {
        return user.matchUserId(writer.getUserId());
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public User getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
