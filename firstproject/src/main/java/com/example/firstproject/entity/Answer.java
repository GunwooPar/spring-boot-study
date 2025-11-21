package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "Text")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;
    //자식, @joincolumn(name = question_id ) 생략
    //Question 엔티티를 참조하기 위해 question 속성을 추가
    //다대일 관계, Answer 엔티티의 question 속성과 질문 엔티티가 연결(DB에서 외래키)
}