package ru.serdyuk.web.controller;

import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ru.serdyuk.AbstractTestController;
import ru.serdyuk.StringTestUtils;
import ru.serdyuk.exception.EntityNotFoundException;
import ru.serdyuk.mapper.v1.ClientMapper;
import ru.serdyuk.model.Client;
import ru.serdyuk.model.Order;
import ru.serdyuk.service.ClientService;
import ru.serdyuk.web.model.ClientListResponse;
import ru.serdyuk.web.model.ClientResponse;
import ru.serdyuk.web.model.OrderResponse;
import ru.serdyuk.web.model.UpsetClientRequest;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

public class ClientControllerTest extends AbstractTestController {

    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientMapper clientMapper;

    @Test
    public void whenFindAll_thenReturnAllClients() throws Exception{
        //создание тестовых данных
        List<Client> clients = new ArrayList<>();
        clients.add(createClient(1L, null));
        Order order = createOrder(1L, 100L, null);
        clients.add(createClient(2L, order));

        List<ClientResponse> clientResponses = new ArrayList<>();
        clientResponses.add(createClientResponse(1L, null));
        OrderResponse orderResponse = createOrderResponse(1L, 100L);
        clientResponses.add(createClientResponse(2L, orderResponse));
        //вызов контоллера
        ClientListResponse clientListResponse = new ClientListResponse(clientResponses );
        Mockito.when(clientService.findAll()).thenReturn(clients);
        Mockito.when(clientMapper.clientListToClientResponse(clients)).thenReturn(clientListResponse);
        String actualResponse = mockMvc.perform(get("/api/v1/client")).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("/response/find_al_client_responce.json");
        //выполнение проверок
        Mockito.verify(clientService, Mockito.times(1) ).findAll();
        Mockito.verify(clientMapper, Mockito.times(1)).clientListToClientResponse(clients);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenGetClientById_thenReturnClientById() throws Exception {
        Client client = createClient(1L, null);
        ClientResponse clientListResponse = createClientResponse(1L, null);
        Mockito.when(clientService.findById(1L)).thenReturn(client);
        Mockito.when(clientMapper.clientToResponse(client)).thenReturn(clientListResponse);

        String actualResponse = mockMvc.perform(get("/api/v1/client/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/find_client_by_id_response.json");

        Mockito.verify(clientService, Mockito.times(1)).findById(1L);
        Mockito.verify(clientMapper, Mockito.times(1)).clientToResponse(client);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateClient_thenReturnNewClient() throws Exception{
        Client client = new Client();
        client.setName("Client 1");
        Client createdClient = createClient(1L, null);
        ClientResponse clientResponse = createClientResponse(1L, null);
        UpsetClientRequest request = new UpsetClientRequest("Client 1");

        Mockito.when(clientService.save(client)).thenReturn(createdClient);
        Mockito.when(clientMapper.requestToClient(request)).thenReturn(client);
        Mockito.when(clientMapper.clientToResponse(createdClient)).thenReturn(clientResponse);

        String actualResponse = mockMvc.perform(post("/api/v1/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/create_client_response.json");
        Mockito.verify(clientService, Mockito.times(1)).save(client);
        Mockito.verify(clientMapper, Mockito.times(1)).requestToClient(request);
        Mockito.verify(clientMapper, Mockito.times(1)).clientToResponse(createdClient);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenUpdateClient_thenReturnUpdatedClient() throws Exception {
        UpsetClientRequest request =new UpsetClientRequest("New Client 1");
        Client updatedClient = new Client(1L, "New Client 1", new ArrayList<>());
        ClientResponse clientResponse = new ClientResponse(1L, "New Client 1", new ArrayList<>());

        Mockito.when(clientService.update(updatedClient)).thenReturn(updatedClient);
        Mockito.when(clientMapper.requestToClient(1L, request)).thenReturn(updatedClient);
        Mockito.when(clientMapper.clientToResponse(updatedClient)).thenReturn(clientResponse);

        String actualResponse = mockMvc.perform(put("/api/v1/client/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/update_client_response.json");

        Mockito.verify(clientService, Mockito.times(1)).update(updatedClient);
        Mockito.verify(clientMapper, Mockito.times(1)).requestToClient(1L, request);
        Mockito.verify(clientMapper, Mockito.times(1)).clientToResponse(updatedClient);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenDeleteClientById_thenReturnStatusNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/client/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(clientService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenFindByIdNotExistedClient_thenReturnError() throws Exception {
        Mockito.when(clientService.findById(50L)).thenThrow(new EntityNotFoundException("Клиент с ID 50 не найден!"));
        mockMvc.perform(get("/api/v1/client/50"))
                .andExpect(status().isNotFound());
    }

}
