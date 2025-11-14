package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity //DB 속 테이블 생성
public class Article { //테이블 이름
    @Id // 엔티티의 대표값 지정
    @GeneratedValue // 자동생성기능 추가(숫자가 자동으로 매겨짐)
    private Long id;
    @Column // title 필드 선언, DB 테이블의 title 열과 연결
    private String title;
    @Column
    private String content;

    //Article 생성자 추가
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title  = title;
        this.content = content;
    }

    //toSting() 메서드 추가
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
