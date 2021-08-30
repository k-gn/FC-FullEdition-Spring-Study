package com.example.dmaker.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

// 영속성 도메인 오브젝트 (영속성이란 메모리영역이 아닌 물리 데이터 저장공간(database)에 저장되어 애플리케이션이 종료되더라도 데이터가 사라지지 않고 남아있음을 의미)
// 엔티티 매니저는 엔티티 객체들을 영속 컨텍스트(Persistence Context)에 넣어두고 관리
@Entity // 테이블에 대응하는 하나의 클래스 (데이터의 집합, 저장되고, 관리되어야하는 데이터)
// 이 아래 롬복들은 거의 기본적으로 붙는다.
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RetiredDeveloper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String memberId;
    private String name;

    // auditing 설정
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
