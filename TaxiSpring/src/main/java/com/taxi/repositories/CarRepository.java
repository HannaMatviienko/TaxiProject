package com.taxi.repositories;

import com.taxi.dto.CarDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository  extends CrudRepository<CarDTO, Integer> {
    List<CarDTO> findByCategoryAndStatus(Integer category, Integer status);
    Long countById(Integer id);
}
