package com.example.dmaker.service;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.dto.EditDeveloper;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.entity.RetiredDeveloper;
import com.example.dmaker.repository.RetiredDeveloperRepository;
import com.example.dmaker.util.DeveloperLevel;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.dmaker.exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;
    private final EntityManager em;

    // ACID
    // Atomic - 원자성 - 모두 동작하거나 하나도 동작하지 않거나
    // Consistency - 일관성 - 정해진 규칙에 따라 끝나는 시점에 일관성이 지켜진 상태여야 한다.
    // Isolation - 독립성 - 트랜잭션을 수행 시 다른 트랜잭션의 연산 작업이 끼어들지 못하도록 보장 (대량 처리에 따른 성능 문제로 적절한 고립성 통제가 필요)
    // Durability - 영속성 - 성공적으로 수행된 트랜잭션(커밋)은 영원히 반영
    // auto commit 되거나 직접 한번에 commit 할 수 있다.
    // 스프링에서 트랜잭션은 어노테이션으로 쓸 때 AOP 기반으로 동작함 (TransactionInterceptor - invoke)
    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
//        EntityTransaction transaction = em.getTransaction();
//
//        try {
//            transaction.begin();
        
        validateCreateDeveloperRequest(request);
        
        
        // business login start
        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkill(request.getDeveloperSkill())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();
        developerRepository.save(developer); // 저장, 영속화
        
        return CreateDeveloper.Response.fromEntity(developer);
        
        // business login end
//            transaction.commit();
//        }catch (Exception e) {
//            transaction.rollback();
//            throw e;
//        }

    }

    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED).stream()
                .map(DeveloperDto::fromEntity).collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    @Transactional // dirty checking
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateEditDeveloperRequest(request, memberId);

        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(() -> new DMakerException(NO_DEVELOPER));

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkill(request.getDeveloperSkill());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);
    }

    @Transactional // 추 후 변화 가능성을 생각하면 되도록 붙여주는게 유리하다.
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        // EMPLOYED -> RETIRED
        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);

        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();

        retiredDeveloperRepository.save(retiredDeveloper);

        return DeveloperDetailDto.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // business validation
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();

        validateDeveloperLevel(developerLevel, experienceYears);

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent(developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                });
    }

    private void validateEditDeveloperRequest(EditDeveloper.Request request, String memberId) {
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();

        validateDeveloperLevel(developerLevel, experienceYears);

        developerRepository.findByMemberId(memberId).orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if (developerLevel == DeveloperLevel.SENIOR && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }


}
