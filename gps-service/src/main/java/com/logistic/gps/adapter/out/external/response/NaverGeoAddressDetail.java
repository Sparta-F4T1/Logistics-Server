package com.logistic.gps.adapter.out.external.response;

public record NaverGeoAddressDetail(
    String jibunAddress,
    String roadAddress,
    Double x, //위도
    Double y //경도
) {
}
