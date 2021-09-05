package com.example.springbootstudy.service;

import com.example.springbootstudy.logic.Sort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

// 다리 역할
@Service
public class SortService {

    private Sort<String> sort;

    public SortService(@Qualifier("bubbleSort") Sort<String> sort) {
        System.out.println(sort.getClass().getName());
        this.sort = sort;
    }

    public List<String> doSort(List<String> list) {

        return sort.sort(list);
    }
}
