package com.forguta.ordermanagement.inventory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`PRODUCT`")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, columnDefinition = "varchar(150)")
    private String name;

    @Column(name = "ALLOCATABLE_QUANTITY", nullable = false)
    private Integer allocatableQuantity;
}
