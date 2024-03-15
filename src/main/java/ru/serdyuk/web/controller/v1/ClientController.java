package ru.serdyuk.web.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.serdyuk.mapper.v1.ClientMapper;
import ru.serdyuk.model.Client;
import ru.serdyuk.service.ClientService;
import ru.serdyuk.web.model.ClientListResponse;
import ru.serdyuk.web.model.ClientResponse;
import ru.serdyuk.web.model.ErrorResponse;
import ru.serdyuk.web.model.UpsetClientRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/client")
@RequiredArgsConstructor
@Tag(name = "Client V1", description = "Client API: version V1")
public class ClientController {

    public final ClientService clientServiceImpl;

    public final ClientMapper clientMapper;

    @Operation(
            summary = "Get all clients",
            description = "Get all clients, and return list -> id, name and list of orders",
            tags = {"client"}
    )
    @GetMapping
    public ResponseEntity<ClientListResponse> findAll() {
        return ResponseEntity.ok(
                clientMapper.clientListToClientResponse(clientServiceImpl.findAll())
        );
    }

    @Operation(
            summary = "Get client by ID",
            description = "Get client by ID, and return id, name and list of orders",
            tags = {"client", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ClientResponse.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
            )}
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                clientMapper.clientToResponse(clientServiceImpl.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid UpsetClientRequest request) {
        Client newClientDb = clientServiceImpl.save(clientMapper.requestToClient(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.clientToResponse(newClientDb));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId,
                                                 @RequestBody UpsetClientRequest request) {
        Client updateClientDb = clientServiceImpl.update(clientMapper.requestToClient(clientId, request));
        return ResponseEntity.ok(clientMapper.clientToResponse(updateClientDb));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientServiceImpl.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> notFoundHandler(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }*/

}
