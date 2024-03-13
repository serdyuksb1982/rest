package ru.serdyuk.web.controller.V2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.serdyuk.mapper.v2.OrderMapperV2;
import ru.serdyuk.model.Order;
import ru.serdyuk.service.OrderService;
import ru.serdyuk.web.model.OrderListResponse;
import ru.serdyuk.web.model.OrderResponse;
import ru.serdyuk.web.model.UpsetOrderRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v2/order")
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderService databaseOrderService;

    private final OrderMapperV2 orderMapper;

    @GetMapping
    public ResponseEntity<OrderListResponse> findAll() {
        return ResponseEntity.ok(orderMapper.orderListToOrderListResponse(
                databaseOrderService.findAll()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                orderMapper.orderToResponse(databaseOrderService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid UpsetOrderRequest request) {
        Order newOrder = databaseOrderService.save(orderMapper.requestToOrder(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderMapper.orderToResponse(newOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable("id") Long orderId, @RequestBody @Valid UpsetOrderRequest request) {
        Order updateOrder = databaseOrderService.update(orderMapper.requestToOrder(orderId, request));
        return ResponseEntity.ok(orderMapper.orderToResponse(updateOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        databaseOrderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}