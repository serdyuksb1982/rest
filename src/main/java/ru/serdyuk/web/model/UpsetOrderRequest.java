package ru.serdyuk.web.model;

import lombok.*;
import ru.serdyuk.model.Client;

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
