package com.fastcampus.springboot;

import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void test() {

        String[] args = {"3", "1", "2"};
        try {
            Main.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}