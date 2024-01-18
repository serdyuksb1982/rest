package ru.serdyuk.web.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientResponse {

    private Long id;

    private String name;

    private List<OrderResponse> orders = new ArrayList<>();

}
