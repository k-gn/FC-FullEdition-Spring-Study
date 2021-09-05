package com.example.springbootstudy.service;

import com.example.springbootstudy.logic.JavaSort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortServiceTest {

    private SortService sortService = new SortService(new JavaSort<String>());

    @Test
    void test() {

        List<String> strings = sortService.doSort(List.of("3", "1", "2"));

        assertEquals(List.of("1", "2", "3"), strings);
    }

}
// 엔터프라이즈 급 서비스를 개발할 때 우리가 보편적으로 생각하는 대부분의 지원을 스프링이 제공해주려고 노력한다.
// 엔터프라이즈 애플리케이션을 만드는데 필요한 거의 모든 요소를 지원해주는 프레임워크