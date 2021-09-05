package com.example.springbootstudy.domain;

//@Value
public record Student2( // record, 불변객체, 외부 의존성 없이 깔끔하다, getter 없이 필드명으로 접근
                        String name,
                        Integer age,
                        Grade grade
){

    // static factory method
    public static Student2 of(String name, Integer age, Grade grade) {
        return new Student2(name, age, grade);
    }

    public enum Grade {
        A, B, C, D, F
    }
}
// # lombok
// - @Value
// : 불변 객체를 만들때 쓴다.
// : staticConstructor 속성 존재 (staticConstructor = "of" 일 경우 클래스명.of 로 객체 생성 가능)
// : @FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)

// 롬복의 단점
// 1. 사용자 의도와 컴파일러의 눈을 피해간다.
//  final 필드로 했는데 빌더 패턴 시 null 값으로 생성 가능
// 2. 필드 순서가 바뀔 경우 생성자에 매개변수 순서도 같이 바껴서 문제가 생길 수 있음
// 3. toString 순환 참조 문제 (exclude 로 해결 가능)