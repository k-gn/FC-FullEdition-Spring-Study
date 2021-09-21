package com.example.getinline.dto;

import com.example.getinline.constant.PlaceType;

// Place dto
// # Record
// 자동으로 생성자와 멤버를 가지는 클래스를 만들어준다.
// record 클래스는 final 클래스이라 상속할 수 없다.
// 각 필드는 private final 필드로 정의된다.
// 모든 필드를 초기화하는 RequiredAllArgument 생성자가 생성된다.
// 각 필드의 getter는 getXXX()가 아닌, 필드명을 딴 getter가 생성된다.(name(), age(), address())
public record PlaceResponse(
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {

    public static PlaceResponse of(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceResponse(placeType, placeName, address, phoneNumber, capacity, memo);
    }
}
