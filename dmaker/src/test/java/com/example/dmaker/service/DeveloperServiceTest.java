package com.example.dmaker.service;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.exception.DMakerErrorCode;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.repository.DeveloperRepository;
import com.example.dmaker.repository.RetiredDeveloperRepository;
import com.example.dmaker.util.DeveloperLevel;
import com.example.dmaker.util.DeveloperSkill;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.example.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.util.DeveloperLevel.JUNIOR;
import static com.example.dmaker.util.DeveloperLevel.SENIOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// junit : 자바 진영의 단위 테스트 프레임워크
// context = 문맥, 어플리케이션이 돌아가는 설정값과 빈을 담는 곳, 동작하기 위한 전략을 세우는 부분
//@SpringBootTest // 진짜 실행과 동일한 환경, 테스트 시 테스트 컨텍스트에 모든 빈을 로딩한 환경 (통합 테스트)
@ExtendWith(MockitoExtension.class) // mockito 기능을 활용하기 위한 확장
class DeveloperServiceTest {

    // 해당 테스트 내 가짜 객체 등록
    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

//    @Autowired
    @InjectMocks // 생성 시 위에 가짜 객체를 자동으로 주입
    private DeveloperService developerService;

    private final Developer developer = Developer.builder()
            .developerLevel(SENIOR)
            .developerSkill(DeveloperSkill.BACK_END)
            .experienceYears(12)
            .statusCode(StatusCode.EMPLOYED)
            .memberId("memberId")
            .name("name")
            .age(32)
            .build();

    private CreateDeveloper.Request getCreateRequest(
            DeveloperLevel developerLevel,
            DeveloperSkill developerSkill,
            Integer experienceYears
    ) {

        return CreateDeveloper.Request.builder()
                .developerLevel(developerLevel)
                .developerSkill(developerSkill)
                .experienceYears(experienceYears)
                .memberId("memberId")
                .name("name")
                .age(32)
                .build();
    }

    @Test
    public void testSomething() {

//        String result = "hello";
//        assertEquals("hello", result);

        // 격리성 문제
        // db 에 데이터가 있어야만 테스트를 할 수 있다.
        // Mocking 으로 해결 가능
        // 단위 테스트를 작성할 때 외부에 의존하는 부분을 임의의 가짜로 대체하는 기법이 자주 사용되는데 이를 모킹(mocking)이라고 한다.
        // 다시 말해, 모킹(mocking)은 외부 서비스에 의존하지 않고 독립적으로 실행이 가능한 단위 테스트를 작성하기 위해서 사용되는 테스팅 기법
        developerService.createDeveloper(getCreateRequest(SENIOR, DeveloperSkill.BACK_END, MIN_SENIOR_EXPERIENCE_YEARS + 1));
        List<DeveloperDto> allEmployedDevelopers = developerService.getAllEmployedDevelopers();
        System.out.println(allEmployedDevelopers);

    }

    @Test
    void test() {

        // given (가짜 객체의 동작 정의 = Mocking)
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.ofNullable(developer));

        // when
        DeveloperDetailDto detail = developerService.getDeveloperDetail("memberId");

        // then
        assertEquals(SENIOR, detail.getDeveloperLevel());
    }

    @Test
    void createDeveloperTest_success() {
        // given (mocking), 가짜 객체를 사용할 때 반드시 모킹해줘야 한다.
        // 만약 선언했는데 쓰이지 않는 mocking 일 경우 에러가 발생
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        given(developerRepository.save(any()))
                .willReturn(developer);

        // Mock 객체가 어떤 동작을 할 때 파라미터로 받은 값을 캡쳐 (검증에 활용 가능)
        // 실제로 사용되는 파라미터 값을 확인할 수 있고, 외부 API 호출 시 날라가는 데이터를 확인할 수 있다.
        // ArgumentCaptor 생성
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        // when (동작과 결과 받기)
        developerService.createDeveloper(getCreateRequest(SENIOR, DeveloperSkill.BACK_END, MIN_SENIOR_EXPERIENCE_YEARS + 1));

        // then (검증)
        verify(developerRepository, times(1)) // 특정 Mock 의 호출 횟수 검증
            .save(captor.capture()); // save 할 때 넣은 파라미터 캡쳐

        Developer savedDeveloper = captor.getValue();
        assertEquals(SENIOR, savedDeveloper.getDeveloperLevel());
    }

    @Test
    void createDeveloperTest_failed_with_duplicated() {
        // given (mocking)
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(developer));

        // 예외 테스트 같은 경우 예외 발생 시 verify 같은 검증까지 로직이 안오고 응답 또한 못받는다.
        // 따라서 발생하는 예외 자체를 비교하면 된다.
        // when (동작과 결과 받기)
        // then (검증)
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> developerService.createDeveloper(getCreateRequest(SENIOR, DeveloperSkill.BACK_END, 12)));
        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());
    }
}