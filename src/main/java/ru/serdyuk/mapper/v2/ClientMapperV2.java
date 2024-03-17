package ru.serdyuk.mapper.v2;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.serdyuk.model.Clients;
import ru.serdyuk.web.model.ClientListResponse;
import ru.serdyuk.web.model.ClientResponse;
import ru.serdyuk.web.model.UpsetClientRequest;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy =
ReportingPolicy.IGNORE, uses = {OrderMapperV2.class})
public interface ClientMapperV2 {

    Clients requestToClient(UpsetClientRequest request);

    @Mapping(source = "clientId", target = "id")
    Clients requestToClient(Long clientId, UpsetClientRequest request);

    ClientResponse clientToResponse(Clients clients);

    default ClientListResponse clientListToClientResponseList(
            List<Clients> clients) {
        ClientListResponse response = new ClientListResponse();
        List<ClientResponse> list = new ArrayList<>();
        for (Clients clients1 : clients) {
            ClientResponse clientResponse = clientToResponse(clients1);
            list.add(clientResponse);
        }
        response.setClients(list);
        return response;
    }

}
