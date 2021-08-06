package com.hang.book.springboot.domain.posts;

import com.hang.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // Lombok 어노테이션 (코드 단순화 -> 필수 X)
@NoArgsConstructor // Lombok 어노테이션 (코드 단순화 -> 필수 X) 기본 생성자 자동 추가(이해 XXX)
@Entity // JPA 어노테이션 (필수)
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙 <-를 추가해야 auto increment 지원
    private Long id;

    // 테이블의 컬럼을 나타냄(필수 아님) 기본값 이외에 추가로 변경이 필요한 옵션이 있을때 사용
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스 생성(아직 이해 XXX)
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }

}
