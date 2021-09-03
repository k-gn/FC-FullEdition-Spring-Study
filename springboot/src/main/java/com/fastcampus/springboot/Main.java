package com.fastcampus.springboot;

//import com.fastcampus.springboot.config.Config;
import com.fastcampus.springboot.logic.BubbleSort;
import com.fastcampus.springboot.logic.Sort;
import com.fastcampus.springboot.service.SortService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

//public class Main {
//    public static void main(String[] args) {
//
//        // AnnotationConfigApplicationContext - @Configure Annotation을 이용한 Java Code를 설정정보로 사용
//        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
//
//        SortService sortService = context.getBean(SortService.class);
//
////        Sort<String> sort = new BubbleSort<>();
//        System.out.println("[result] : " + sortService.doSort(Arrays.asList("3", "1", "2")));
//    }
//}
