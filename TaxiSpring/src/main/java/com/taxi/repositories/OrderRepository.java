package com.taxi.repositories;

import com.taxi.dto.OrderDTO;
import com.taxi.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Pageable;
import java.util.Date;

public interface OrderRepository extends CrudRepository<OrderDTO, Integer> {
    Page<OrderDTO> findByOrderDateBetween(Date dateFrom, Date dateTo, Pageable pageable);
    Page<OrderDTO> findByOrderDateBetweenAndUser(Date dateFrom, Date dateTo, UserDTO user, Pageable pageable);
}
