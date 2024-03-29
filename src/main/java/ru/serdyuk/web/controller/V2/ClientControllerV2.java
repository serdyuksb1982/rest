package ru.serdyuk.web.controller.V2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.serdyuk.mapper.v2.ClientMapperV2;
import ru.serdyuk.model.Clients;
import ru.serdyuk.model.Orders;
import ru.serdyuk.service.ClientServiceDb;
import ru.serdyuk.web.model.*;

import java.util.List;


@RestController
@RequestMapping("/api/v2/client")
@RequiredArgsConstructor
public class ClientControllerV2 {

    private final ClientServiceDb databaseClientService;

    private final ClientMapperV2 clientMapper;

    @GetMapping
    public ResponseEntity<ClientListResponse> findAll() {
        return ResponseEntity.ok(
                clientMapper.clientListToClientResponseList(
                        databaseClientService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clientMapper.clientToResponse(
                databaseClientService.findById(id)
        ));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody UpsetClientRequest request) {
        Clients newClients = databaseClientService.save(clientMapper.requestToClient(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientMapper.clientToResponse(newClients));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long id, @RequestBody UpsetClientRequest request) {
        Clients updateClients = databaseClientService.update(clientMapper.requestToClient(id, request));
        return ResponseEntity.ok(clientMapper.clientToResponse(updateClients));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long clientId) {
        databaseClientService.deleteById(clientId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/save-with-orders")
    public ResponseEntity<ClientResponse> createWithOrders(@RequestBody CreateClientWithOrderRequest request) {
        Clients client = Clients.builder().name(request.getName()).build();
        List<Orders> orders = request.getOrders().stream()
                .map(orderRequest -> Orders.builder()
                        .product(orderRequest.getProduct())
                        .cost(orderRequest.getCost())
                        .build()).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(
                clientMapper.clientToResponse(databaseClientService.saveWithOrders(client, orders))
        );
    }

}
