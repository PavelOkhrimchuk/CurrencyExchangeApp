package com.ohrim.model;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Currency {


    private int id;
    private String code;
    private String fullName;
    private String sign;




}
