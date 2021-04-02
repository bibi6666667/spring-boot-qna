package com.codessquad.qna.model.dto;

import com.codessquad.qna.exception.EntityNotCreateException;
import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class QuestionDto {

    private Long id;

    private UserDto writer;

    private String dateTime;

    private String title;

    private String contents;

    @JsonIgnore
    private List<Answer> answers;

    private boolean deleted;

    public QuestionDto() {}

    public QuestionDto(Question question) {
        this.id = question.getId();
        this.writer = new UserDto(question.getWriter());
        this.dateTime = question.getDateTime();
        this.title = question.getTitle();
        this.contents = question.getContents();
        this.answers = question.getAnswers();
        this.deleted = question.isDeleted();
    }

    public Question toEntity() {
        if (title == null || contents == null) {
            throw new EntityNotCreateException();
        }
        return new Question(title, contents);
    }

    public int getAnswersCount() {
        return (int) answers.stream()
                .filter(answer -> !answer.isDeleted())
                .count();
    }

    public Long getId() {
        return id;
    }

    public UserDto getWriter() {
        return writer;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "id=" + id +
                ", writer=" + writer +
                ", dateTime='" + dateTime + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
