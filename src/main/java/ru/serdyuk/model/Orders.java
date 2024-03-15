package ru.serdyuk.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;


@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(name = "orders")
public class Orders {

    public Orders() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ToString.Exclude
    private Clients clients;

    @CreationTimestamp
    private Instant createAt;

    @UpdateTimestamp
    private Instant updateAt;

}
