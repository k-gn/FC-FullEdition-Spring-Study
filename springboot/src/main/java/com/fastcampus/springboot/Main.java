package com.fastcampus.springboot;

import com.fastcampus.springboot.logic.BubbleSort;
import com.fastcampus.springboot.logic.Sort;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Sort<String> sort = new BubbleSort<>();
        System.out.println("[result] : " + sort.sort(Arrays.asList(args)));
    }
}
