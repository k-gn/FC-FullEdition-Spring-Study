package com.example.dmaker.service;

import com.example.dmaker.entity.Developer;
import com.example.dmaker.entity.DeveloperLevel;
import com.example.dmaker.entity.DeveloperSkill;
import com.example.dmaker.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    // ACID
    // Atomic - 원자성 - 모두 동작하거나 하나도 동작하지 않거나
    // Consistency - 일관성 - 정해진 규칙에 따라 끝나는 시점에 일관성이 지켜진 상태여야 한다.
    // Isolation - 독립성 - 트랜잭션을 수행 시 다른 트랜잭션의 연산 작업이 끼어들지 못하도록 보장 (대량 처리에 따른 성능 문제로 적절한 고립성 통제가 필요)
    // Durability - 영속성 - 성공적으로 수행된 트랜잭션(커밋)은 영원히 반영
    @Transactional
    public void createDeveloper() {
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkill(DeveloperSkill.BACK_END)
                .experienceYears(2)
                .name("Olaf")
                .age(5)
                .build();

        developerRepository.save(developer); // 저장, 영속화
    }

}
