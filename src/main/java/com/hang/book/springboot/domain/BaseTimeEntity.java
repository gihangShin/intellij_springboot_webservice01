package com.hang.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // JPA Entity 클래스들이 상속할 경우 플드들도 컬럼으로 인식하도록함.
@EntityListeners(AuditingEntityListener.class)  // 이하 클래스의 Auditing 기능 포함
public abstract class BaseTimeEntity {

    @CreatedDate    // Entity 가 생성되어 저장될 때 시간이 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate   // 조회한 Entity의 값을 변경할 때 시간이 자동 저장됨 (?)
    private LocalDateTime modifiedDate;

}
