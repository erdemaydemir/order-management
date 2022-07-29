package com.forguta.ordermanagement.order.entity;

import com.forguta.ordermanagement.common.constant.InventoryStatus;
import com.forguta.ordermanagement.common.constant.OrderStatus;
import com.forguta.ordermanagement.common.constant.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`ORDER`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @Column(name = "STATUS", nullable = false)
    private OrderStatus status;

    @Column(name = "INVENTORY_STATUS")
    private InventoryStatus inventoryStatus;

    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;
}
