package ru.serdyuk.mapper.v2;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.serdyuk.model.Client;
import ru.serdyuk.web.model.ClientListResponse;
import ru.serdyuk.web.model.ClientResponse;
import ru.serdyuk.web.model.UpsetClientRequest;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy =
ReportingPolicy.IGNORE, uses = {OrderMapperV2.class})
public interface ClientMapperV2 {

    Client requestToClient(UpsetClientRequest request);

    @Mapping(source = "clientId", target = "id")
    Client requestToClient(Long clientId, UpsetClientRequest request);

    ClientResponse clientToResponse(Client client);

    default ClientListResponse clientListToClientResponseList(
            List<Client> clients) {
        ClientListResponse response = new ClientListResponse();
        response.setClients(clients.stream()
                .map(this::clientToResponse)
                .collect(Collectors.toList()));
        return response;
    }

}
