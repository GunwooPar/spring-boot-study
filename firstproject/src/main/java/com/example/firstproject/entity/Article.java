package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.ToString;


import java.time.LocalDateTime;

@ToString
@Entity //DB 속 테이블 생성
public class Article { //테이블 이름
    @Id // 엔티티의 대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id; //final 초기값=최종적인 값

    @Column // title 필드 선언, DB 테이블의 title 열과 연결
    private final String title;

    @Column
    private final String content;

    //Article 생성자 추가
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title  = title;
        this.content = content;
    }

}