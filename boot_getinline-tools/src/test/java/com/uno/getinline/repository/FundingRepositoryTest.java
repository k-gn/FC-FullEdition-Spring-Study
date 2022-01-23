package com.uno.getinline.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FundingRepositoryTest {

    @Autowired
    private FundingRepository fundingRepository;

    @Test
    void test() {
        fundingRepository.findByUserIdAndStatus(1L, "COMPLITION");
    }
}