package com.fastcampus.springboot.service;

import com.fastcampus.springboot.logic.JavaSort;
import com.fastcampus.springboot.logic.Sort;

import java.util.List;

public class SortService {

    private Sort<String> sort;

    public SortService(Sort<String> sort) {
        System.out.println(sort.getClass().getName());
        this.sort = sort;
    }

    public List<String> doSort(List<String> list) {

        return sort.sort(list);
    }
}
