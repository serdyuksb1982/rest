package ru.serdyuk.web.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderResponse {

    private Long id;

    private String product;

    private BigDecimal cost;

}
