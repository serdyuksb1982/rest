package ru.serdyuk.web.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientListResponse {

    private List<ClientResponse> clients = new ArrayList<>();

}
