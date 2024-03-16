package ru.serdyuk.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private String product;

    private BigDecimal cost;

}
