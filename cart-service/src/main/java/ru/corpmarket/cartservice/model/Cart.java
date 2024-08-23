package ru.corpmarket.cartservice.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "consumer_id")
    private UUID consumerId;

    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "count")
    private Integer count;
}
