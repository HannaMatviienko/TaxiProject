package com.taxi.dto;

import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class OrderDTO {

    public OrderDTO()
    {
        passengers = 1;
        price = 0.0;
        status = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CarDTO> cars;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private UserDTO user;

    @Column(nullable = false, name = "address_from")
    private String addressFrom;

    @Column(nullable = false, name = "address_to")
    private String addressTo;

    @Column(nullable = false)
    private Integer passengers;

    @Column(nullable = false)
    private Integer category;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private Double price;

    @Column(name = "order_date", columnDefinition = "DATETIME")
    private Date orderDate;

    public String getDisplayDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(orderDate);
    }

    public double getCheckOutPrice()
    {
        if (getCars() == null || getCars().size() == 0)
            setPrice(0.0);
        else {
            double k = 1.0;
            switch (getCategory()) {
                case 1:
                    k = 1.5;
                    break;

                case 2:
                    k = 2.0;
                    break;
            }
            setPrice(getPassengers() * 100.0 * k);
        }
        return price;
    }
}