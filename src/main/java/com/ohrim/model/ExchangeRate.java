package com.ohrim.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ExchangeRate {
    private int id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;


}
