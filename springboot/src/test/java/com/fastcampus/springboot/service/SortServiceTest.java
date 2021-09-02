package com.fastcampus.springboot.service;

import com.fastcampus.springboot.logic.JavaSort;
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