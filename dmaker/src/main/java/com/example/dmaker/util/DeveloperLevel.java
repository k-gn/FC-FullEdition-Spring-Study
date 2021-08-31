package com.example.dmaker.util;

import com.example.dmaker.exception.DMakerException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

import static com.example.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;

@AllArgsConstructor
@Getter
public enum DeveloperLevel {

//    NEW("신입 개발자", 0, 0),
//    JUNIOR("주니어 개발자", 1, MAX_JUNIOR_EXPERIENCE_YEARS),
//    JUNGNIOR("중니어 개발자", MAX_JUNIOR_EXPERIENCE_YEARS + 1, MIN_SENIOR_EXPERIENCE_YEARS - 1),
//    SENIOR("시니어 개발자", MIN_SENIOR_EXPERIENCE_YEARS, 70);

    NEW("신입 개발자", years -> years == 0),
    JUNIOR("주니어 개발자", years -> years <= MAX_JUNIOR_EXPERIENCE_YEARS),
    JUNGNIOR("중니어 개발자", years -> years > MAX_JUNIOR_EXPERIENCE_YEARS && years < MIN_SENIOR_EXPERIENCE_YEARS),
    SENIOR("시니어 개발자", years -> years >= MIN_SENIOR_EXPERIENCE_YEARS);

    private final String description;
//    private final Integer minExperienceYears;
//    private final Integer maxExperienceYears;

    // 함수를 사용한 validation
    private final Function<Integer, Boolean> validateFunction;

    public void validateExperienceYears(Integer years) {
        if(!validateFunction.apply(years)) // apply -> 정의된 함수로 들어온 객체 T를 객체 R로 매핑 (Function<T, R>)
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
    }
}
