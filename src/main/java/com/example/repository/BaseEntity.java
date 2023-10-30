package com.example.repository;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//공통 매핑 정보를 가진 엔티티
@MappedSuperclass //이 엔티티를 상속받은 다른 엔티티에서 아래 필드들을 컬럼으로 사용할 수 있다.
@EntityListeners(AuditingEntityListener.class) //Auditing 정보 리스너
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false) //변경 X, null X
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
