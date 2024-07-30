package com.ohrim.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyDto {

    private Integer id;
    private String name;
    private String code;
    private String sign;


}
