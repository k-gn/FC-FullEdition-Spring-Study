package com.example.springbootstudy.logic;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BubbleSort <T extends Comparable<T>> implements Sort<T> {

    @Override
    public List<T> sort(List<T> list) {

        List<T> output = new ArrayList<>(list);

        for(int i=output.size()-1; i>0; i--) {
            for(int j=0; j<i; j++) {
                if(output.get(j).compareTo(output.get(j+1)) > 0) {
                    T temp = output.get(j);
                    output.set(j, output.get(j + 1));
                    output.set(j + 1, temp);
                }
            }
        }

        return output;
    }
}

/*
 @Component
    - class level
    - 내가 작성한 클래스
    
 @Bean
    - method level
    - 외부 라이브러리 빈 등록.

 @Configuration
    - 각종 빈 또는 인터페이스 설정 등록
 
 스프링 빈은 기본적으로 프록시 빈으로 생성된다.
 */