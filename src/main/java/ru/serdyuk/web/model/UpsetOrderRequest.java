package ru.serdyuk.web.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpsetOrderRequest {

    private Long clientId;

    private String product;

    private BigDecimal cost;

}
