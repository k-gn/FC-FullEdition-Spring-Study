package com.uno.getinline.repository;

import com.uno.getinline.domain.Funding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, Long> {

    @Query(value = "SELECT * FROM funding WHERE user_id = :userId AND status = :status", nativeQuery = true)
    public List<Funding> findByUserIdAndStatus(Long userId, String status);
}
