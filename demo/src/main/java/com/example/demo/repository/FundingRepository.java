package com.example.demo.repository;

import com.example.demo.constant.Status;
import com.example.demo.entity.Funding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FundingRepository
        extends JpaRepository<Funding, Long> {

    @Query(value = "SELECT * FROM funding WHERE user_id = :userId AND status = :status", nativeQuery = true)
    public List<Funding> findByUserIdAndStatus(Long userId, Status status);
}
