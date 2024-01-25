package ru.serdyuk.web.controller;

import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.serdyuk.AbstractTestController;
import ru.serdyuk.StringTestUtils;
import ru.serdyuk.mapper.v1.ClientMapper;
import ru.serdyuk.model.Client;
import ru.serdyuk.model.Order;
import ru.serdyuk.service.ClientService;
import ru.serdyuk.web.model.ClientListResponse;
import ru.serdyuk.web.model.ClientResponse;
import ru.serdyuk.web.model.OrderResponse;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        List<Client> clients = new ArrayList<>();
        clients.add(createClient(1L, null));
        Order order = createOrder(1L, 100L, null);
        clients.add(createClient(2L, order));

        List<ClientResponse> clientResponses = new ArrayList<>();
        clientResponses.add(createClientResponse(1L, null));
        OrderResponse orderResponse = createOrderResponse(1L, 100L);
        clientResponses.add(createClientResponse(2L, orderResponse));

        ClientListResponse clientListResponse = new ClientListResponse(clientResponses );
        Mockito.when(clientService.findAll()).thenReturn(clients);
        Mockito.when(clientMapper.clientListToClientResponse(clients)).thenReturn(clientListResponse);
        String actualResponse = mockMvc.perform(get("/api/v1/client")).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("/response/find_al_client_responce.json");

        Mockito.verify(clientService, Mockito.times(1) ).findAll();
        Mockito.verify(clientMapper, Mockito.times(1)).clientListToClientResponse(clients);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

}
