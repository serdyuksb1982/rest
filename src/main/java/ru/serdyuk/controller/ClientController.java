package ru.serdyuk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.serdyuk.mapper.v1.ClientMapper;
import ru.serdyuk.model.Client;
import ru.serdyuk.service.ClientService;
import ru.serdyuk.web.model.ClientListResponse;
import ru.serdyuk.web.model.ClientResponse;
import ru.serdyuk.web.model.UpsetClientRequest;

@RestController
@RequestMapping("api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    public final ClientService clientService;

    public final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<ClientListResponse> findAll() {
        return ResponseEntity.ok(
                clientMapper.clientListToClientResponse(clientService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                clientMapper.clientToResponse(clientService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody UpsetClientRequest request) {
        Client newClient = clientService.save(clientMapper.requestToClient(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.clientToResponse(newClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId,
                                                 @RequestBody UpsetClientRequest request) {
        Client updateClient = clientService.update(clientMapper.requestToClient(clientId, request));
        return ResponseEntity.ok(clientMapper.clientToResponse(updateClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
