package ru.serdyuk.mapper.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.serdyuk.model.Order;
import ru.serdyuk.service.ClientService;
import ru.serdyuk.web.model.OrderListResponse;
import ru.serdyuk.web.model.OrderResponse;
import ru.serdyuk.web.model.UpsetOrderRequest;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ClientService clientService;

    public Order requestToOrder(UpsetOrderRequest request) {
        Order order = new Order();

        order.setCost(request.getCost());
        order.setProduct(request.getProduct());
        order.setClient(clientService.findById(request.getClientId()));
        return order;
    }

    public Order requestToOrder(Long orderId, UpsetOrderRequest request) {
        Order order = requestToOrder(request);
        order.setId(orderId);
        return order;
    }

    public OrderResponse orderToResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setCost(order.getCost());
        orderResponse.setProduct(order.getProduct());
        return orderResponse;
    }

    public List<OrderResponse> orderListResponseList(List<Order> orders) {
        List<OrderResponse> list = new ArrayList<>();
        for (Order order : orders) {
            OrderResponse orderResponse = orderToResponse(order);
            list.add(orderResponse);
        }
        return list;
    }

    public OrderListResponse orderListToOrderListResponse(List<Order> orders) {
        OrderListResponse response = new OrderListResponse();
        response.setOrders(orderListResponseList(orders));
        return response;
    }

}
