package com.ohrim.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Currency {


    private int id;
    private String code;
    private String fullName;
    private String sign;




}
