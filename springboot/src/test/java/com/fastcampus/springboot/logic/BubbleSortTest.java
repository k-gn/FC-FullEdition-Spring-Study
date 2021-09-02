package com.fastcampus.springboot.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    @DisplayName("버블 솔트 - 리스트를 넣으면 정렬된 결과를 보여준다.")
    @Test
    void test() {

        // given
        BubbleSort<Integer> bubbleSort = new BubbleSort<>();
        // when
        List<Integer> actual = bubbleSort.sort(List.of(3, 2, 4, 5, 1));

        // then
        assertEquals(List.of(1,2,3,4,5), actual);
    }

/*
    Given
    - 테스트에서 구체화하고자 하는 행동을 시작하기 전에 테스트 상태를 설명하는 부분

    When
    - 구체화하고자 하는 그 행동

    Then
    - 어떤 특정한 행동 때문에 발생할거라고 예상되는 변화에 대한 검증
*/
}