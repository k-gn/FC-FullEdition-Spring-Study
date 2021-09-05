package com.example.springbootstudy.repository;


import com.example.springbootstudy.domain.Student;
import com.example.springbootstudy.domain.Student2;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Map;

// Spring cache abstraction
// - 애플리케이션에 "투명하게" 캐시를 넣어주는 기능
//      : 데이터를 통신하는 시스템 쌍방이 캐시의 존재를 모른다는 의미
//      : 캐시가 있건 없건, 시스템의 기대 동작은 동일, 캐시의 목표는 오로지 "성능"
// - 메소드, 클래스에 적용 가능
// - 캐시 인프라는 스프링 부트 자동설정으로 세팅되고, 프로퍼티로 관리 가능
// - 반복 작업이라면 고려해보자! (얼마나 자주 바뀌는지 분석 -> 주기와 전략 구성)
//      : 잘 바뀌지 않는 정보를 외부 저장소에서 반복적으로 읽어 온다면 캐싱해서 성능 향샹, I/O 감소
// - 무엇을 얼마나 오랫동안 언제 갱신할지 생각해야 한다.
// @EnableCaching : 캐시 활성화, @Cacheable : 캐시 등록, @CacheEvict : 삭제, @CachePut : 갱신

// # Redis
// - 유명한 캐시 서버 중 하나
// - redis-server.exe , redis-cli.exe
// - 명령어는 redis.io 에서 찾아 사용하면 된다.

@Repository
@RequiredArgsConstructor
public class StudentRepository {

    private final Map<String, Student> storage;

    @Cacheable("student") // 해당 키 그룹의 캐시 등록
    public Student getStudent(String name) {
        System.out.println("[repo] 나의 통행료는 무척 비싸다!");
        return storage.get(name);
    }

    public void enroll(String name, int age, Student.Grade grade) {
        storage.put(name, Student.of(name, age, grade));
    }
}

