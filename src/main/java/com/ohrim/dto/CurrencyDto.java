package com.ohrim.dto;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CurrencyDto {

    Integer id;
    String name;
    String code;
    String sign;
}
