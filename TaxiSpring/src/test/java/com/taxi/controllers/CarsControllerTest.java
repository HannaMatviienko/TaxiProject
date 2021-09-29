package com.taxi.controllers;

import com.taxi.dto.CarDTO;
import com.taxi.services.CarService;
import com.taxi.tools.CarNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.*;

class CarsControllerTest {

    private Model mockModel;
    private CarService mockService;

    @BeforeEach
    void setUp() {
        mockModel = mock(Model.class);
        mockService = mock(CarService.class);
    }

    @Test
    void getCars() {
        when(mockService.listAll()).thenReturn(null);

        CarsController controller = new CarsController(mockService);
        String view = controller.getCars(mockModel);

        verify(mockModel).addAttribute("cars", null);

        Assertions.assertEquals("cars", view);
    }

    @Test
    void newCar() {
        AtomicReference<CarDTO> mockCar = new AtomicReference<>();
        try (MockedConstruction<CarDTO> ignored = Mockito.mockConstruction(CarDTO.class,
                (mock, context) -> mockCar.set(mock))) {

            CarsController controller = new CarsController(mockService);
            String view = controller.newCar(mockModel);

            verify(mockCar.get()).setStatus(1);
            verify(mockCar.get()).setCategory(0);
            verify(mockModel).addAttribute("car", mockCar.get());
            verify(mockModel).addAttribute("mode", 0);

            Assertions.assertEquals("car", view);
        }
    }

    @Test
    void saveCar() {
        CarDTO car = new CarDTO();

        CarsController controller = new CarsController(mockService);
        String view = controller.saveCar(car);

        verify(mockService).save(car);

        Assertions.assertEquals("redirect:/admin/cars", view);
    }

    @Test
    void editCar() throws CarNotFoundException {
        int id = 1;
        CarDTO car = new CarDTO();

        when(mockService.get(id)).thenReturn(car);

        CarsController controller = new CarsController(mockService);
        String view = controller.editCar(id, mockModel);

        verify(mockModel).addAttribute("car", car);
        verify(mockModel).addAttribute("mode", 1);

        Assertions.assertEquals("car", view);
    }

    @Test
    void deleteCar() throws CarNotFoundException {
        int id = 1;

        CarsController controller = new CarsController(mockService);
        String view = controller.deleteCar(id);

        verify(mockService).delete(id);

        Assertions.assertEquals("redirect:/admin/cars", view);
    }
}