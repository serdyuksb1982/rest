package ru.serdyuk.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private Long id;

    private String name;


    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        } else orders.add(order);
    }

    /*public void removeOrder(Long orderId) {
        orders = orders.stream().filter(o -> !o.getId().equals(orderId)).collect(Collectors.toList());
    }*/
}
