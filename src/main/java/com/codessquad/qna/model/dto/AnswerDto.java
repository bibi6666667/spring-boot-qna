package com.codessquad.qna.model.dto;

import com.codessquad.qna.exception.EntityNotCreateException;
import com.codessquad.qna.model.Answer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnswerDto {

    private Long id;

    private QuestionDto question;

    private UserDto writer;

    private String contents;

    private String dateTime;

    private boolean deleted;

    public AnswerDto() {}

    public AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.question = new QuestionDto(answer.getQuestion());
        this.writer = new UserDto(answer.getWriter());
        this.contents = answer.getContents();
        this.dateTime = answer.getDateTime();
        this.deleted = answer.isDeleted();
    }

    public Answer toEntity() {
        if (contents == null) {
            throw new EntityNotCreateException();
        }
        return new Answer(contents);
    }

    public Long getId() {
        return id;
    }

    public QuestionDto getQuestion() {
        return question;
    }

    public UserDto getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "id=" + id +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
