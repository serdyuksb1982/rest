package ru.serdyuk.mapper.v2;

import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.serdyuk.model.Order;
import ru.serdyuk.service.ClientService;
import ru.serdyuk.web.model.UpsetOrderRequest;

public abstract class OrderMapperDelegate implements OrderMapperV2{

    @Autowired
    private ClientService databaseClientService;

    @Override
    public Order requestToOrder(UpsetOrderRequest request) {
        Order order = new Order();
        order.setCost(request.getCost());
        order.setProduct(request.getProduct());
        order.setClient(databaseClientService.findById(request.getClientId()));
        return order;
    }

    @Mapping(source = "orderId", target = "id")
    @Override
    public Order requestToOrder(Long orderId, UpsetOrderRequest request) {
        Order order = requestToOrder(request);
        order.setId(orderId);
        return order;
    }
}
