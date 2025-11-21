package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.ToString;

@ToString
public class ArticleForm {
    private final String title; //제목을 받을 필드
    private final String content; //내용을 받을 필드

    // 전송받은 제목과 내용을 필드에 저장하는 생성자 추가
    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 데이터를 잘 받았는지 확인할 toString() 메서드 추가 ->@ToString 어노테이션

    public Article toEntity() {
        return new Article(null, title, content);
    }
}
