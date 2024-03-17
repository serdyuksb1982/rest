package ru.serdyuk.mapper.v2;

import org.mapstruct.*;
import ru.serdyuk.model.Orders;
import ru.serdyuk.web.model.OrderListResponse;
import ru.serdyuk.web.model.OrderResponse;
import ru.serdyuk.web.model.UpsetOrderRequest;

import java.util.List;

@DecoratedWith(OrderMapperDelegate.class)
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapperV2 {

    Orders requestToOrder(UpsetOrderRequest request);

    @Mapping(source = "orderId", target = "id")
    Orders requestToOrder(Long orderId, UpsetOrderRequest request);

    OrderResponse orderToResponse(Orders orders);

    List<OrderResponse> orderListToResponseList(List<Orders> orders);

    default OrderListResponse orderListToOrderListResponse(List<Orders> orders) {
        OrderListResponse response = new OrderListResponse();
        response.setOrders(orderListToResponseList(orders));
        return response;
    }

}
