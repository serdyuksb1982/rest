package ru.serdyuk.mapper.v2;

import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.serdyuk.model.Orders;
import ru.serdyuk.service.impl.DatabaseClientService;
import ru.serdyuk.web.model.UpsetOrderRequest;

public abstract class OrderMapperDelegate implements OrderMapperV2{

    @Autowired
    private DatabaseClientService databaseClientService;

    @Override
    public Orders requestToOrder(UpsetOrderRequest request) {
        Orders orders = new Orders();
        orders.setCost(request.getCost());
        orders.setProduct(request.getProduct());
        orders.setClients(databaseClientService.findById(request.getClientId()));
        return orders;
    }

    @Mapping(source = "orderId", target = "id")
    @Override
    public Orders requestToOrder(Long orderId, UpsetOrderRequest request) {
        Orders orders = requestToOrder(request);
        orders.setId(orderId);
        return orders;
    }
}
