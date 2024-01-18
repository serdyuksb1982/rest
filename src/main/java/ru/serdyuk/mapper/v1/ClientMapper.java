package ru.serdyuk.mapper.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.serdyuk.model.Client;
import ru.serdyuk.web.model.ClientListResponse;
import ru.serdyuk.web.model.ClientResponse;
import ru.serdyuk.web.model.UpsetClientRequest;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final OrderMapper orderMapper;

    public Client requestToClient(UpsetClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        return client;
    }

    public Client requestToClient(Long clientId, UpsetClientRequest request) {
        Client client = requestToClient(request);
        client.setId(clientId);
        return client;
    }

    public ClientResponse clientToResponse(Client client) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(client.getId());
        clientResponse.setName(client.getName());
        clientResponse.setOrders(orderMapper.orderListResponseList(client.getOrders()));

        return clientResponse;
    }

    public ClientListResponse clientListToClientResponse(List<Client> clients) {
        ClientListResponse response = new ClientListResponse();
        response.setClients(clients.stream().map(this::clientToResponse).collect(Collectors.toList()));
        return response;
    }
}
