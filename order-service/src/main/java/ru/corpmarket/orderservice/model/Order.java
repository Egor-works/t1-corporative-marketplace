package ru.corpmarket.orderservice.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "count")
    private Integer count;

    @Column(name = "consumer_id")
    private UUID consumerId;

    @Column(name = "status")
    private Status status;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "price")
    private Integer price;

    @Column(name = "title")
    private String title;


}
