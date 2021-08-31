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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor
public class DeveloperService {

    @Value("${developer.level.min.senior}")
    private final Integer minSeniorYears;

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
    // 메소드가 실행 되기 전에 트랜잭션을 생성 또는 참여 -> 메소드 호출 -> 정상 실행이면 커밋, 예외 발생 시 정책에 따른 롤백 (AOP Around)
    @Transactional // Aspect
    public CreateDeveloper.Response createDeveloper(@NonNull CreateDeveloper.Request request) {
//        EntityTransaction transaction = em.getTransaction();
//
//        try {
//            transaction.begin();
        
        validateCreateDeveloperRequest(request);

        // business login start
//        Developer developer = Developer.from(request);
//        Developer developer = createDeveloperFromRequest(request);

//        developerRepository.save(createDeveloperFromRequest(request)); // 저장, 영속화

        return CreateDeveloper.Response.fromEntity(developerRepository.save(createDeveloperFromRequest(request)));
        
        // business login end
//            transaction.commit();
//        }catch (Exception e) {
//            transaction.rollback();
//            throw e;
//        }

    }

    @Transactional(readOnly = true) // 트랜잭션을 통해 변경 방지
    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED).stream()
                .map(DeveloperDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DeveloperDetailDto getDeveloperDetail(String memberId) {
//        return developerRepository.findByMemberId(memberId)
//                .map(DeveloperDetailDto::fromEntity)
//                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        return DeveloperDetailDto.fromEntity(getDeveloperByMemberId(memberId));
    }

    // 중복 부분을 메소드 처리
    private Developer getDeveloperByMemberId(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    // public 메소드엔 큼직큼직한 개념들을 담고, 상세적인 부분은 private 메소드로 빼서 처리한 깔끔한 구조 => 응집도를 높일 수 있다.
    // 응집도는 한 모듈 내부의 처리 요소들이 서로 관련되어 있는 정도 (모듈에 포함된 내부 요소들이 하나의 책임/ 목적을 위해 연결되어있는 연관된 정도)
    // 단, private 메소드에 너무나 다양한 기능을 담지말기 (다시 분리)
    @Transactional // dirty checking
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        // 지역변수의 변경으로 문제가 발생할 수 있으니 주의하자
//        Developer developer = getDeveloperByMemberId(memberId);

//        setDeveloperFromRequest(request, getDeveloperByMemberId(memberId));

        return DeveloperDetailDto.fromEntity(
                getUpdatedDeveloperFromRequest(request, getDeveloperByMemberId(memberId)));
    }

    private Developer getUpdatedDeveloperFromRequest(EditDeveloper.Request request, Developer developer) {
        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkill(request.getDeveloperSkill());
        developer.setExperienceYears(request.getExperienceYears());

        return developer;
    }

    @Transactional // 추 후 변화 가능성을 생각하면 되도록 붙여주는게 유리하다.
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        // EMPLOYED -> RETIRED
        Developer developer = getDeveloperByMemberId(memberId);
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
        
        // 과거에는 이런 예외를 던지는 방식이 아닌 boolean 을 리턴 후 메소드에서 따로 에러 응답을 내려주는 형태였다.
        // 즉, 에러 관련 정보를 넘기고 넘기는 구조
        // 이 방식은 복잡하고, 유지보수가 불편하며 재사용성이 떨어져서 좋지 않음

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent(developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                });
    }

    // 코드를 길게 짜면서 예외에 대한 상황을 처리할 고민 없이
    // 예외 발생 시 그냥 던진 후 전역 처리를 통해 좀 더 간결하고 편한 프로그래밍을 할 수 있다.
    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        // 숫자(매직넘버)의 상수 또는 enum 처리 (응집도 UP!, 유지보수 UP!)

        developerLevel.validateExperienceYears(experienceYears);

//        if (experienceYears < developerLevel.getMinExperienceYears() ||
//                experienceYears > developerLevel.getMaxExperienceYears()) {
//            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
//        }

//        if (developerLevel == DeveloperLevel.SENIOR && experienceYears < MIN_SENIOR_EXPERIENCE_YEARS) {
//            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
//        }
//
//        if (developerLevel == DeveloperLevel.JUNGNIOR
//                && (experienceYears < MAX_JUNIOR_EXPERIENCE_YEARS
//                || experienceYears > MIN_SENIOR_EXPERIENCE_YEARS)) {
//            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
//        }
//
//        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > MAX_JUNIOR_EXPERIENCE_YEARS) {
//            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
//        }
    }

    public static Developer createDeveloperFromRequest(CreateDeveloper.Request request) {
        return Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkill(request.getDeveloperSkill())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();
    }
}
