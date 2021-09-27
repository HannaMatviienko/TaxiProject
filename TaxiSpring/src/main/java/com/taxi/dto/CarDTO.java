package com.taxi.dto;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CarDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 16)
    private String plate;

    @Column(nullable = false, length = 64)
    private String model;

    @Column(nullable = false)
    private Integer category;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private Integer passengers;

}
