package com.taxi.controllers;

import com.taxi.dto.CarDTO;
import com.taxi.dto.OrderDTO;
import com.taxi.dto.UserDTO;
import com.taxi.services.CarService;
import com.taxi.services.OrderService;
import com.taxi.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class OrdersControllerTest {

    private Model mockModel;
    private OrderService mockOrderService;
    private UserService mockUserService;
    private CarService mockCarService;

    @BeforeEach
    void setUp() {
        mockOrderService = mock(OrderService.class);
        mockUserService = mock(UserService.class);
        mockCarService = mock(CarService.class);
        mockModel = mock(Model.class);
    }

    @Test
    void getOrder() {
        String userName = "user";
        UserDTO user = new UserDTO();
        user.setRoles("ROLE_ADMIN");

        Authentication mockAuth = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuth);
        when(mockAuth.getName()).thenReturn(userName);
        when(mockUserService.findUserByUserName(userName)).thenReturn(user);

        try (MockedStatic<SecurityContextHolder> mockContext = Mockito.mockStatic(SecurityContextHolder.class)) {
            mockContext.when(SecurityContextHolder::getContext)
                    .thenReturn(mockSecurityContext);
            OrdersController cut = new OrdersController(mockOrderService, mockUserService, mockCarService);
            String view = cut.getOrder(mockModel, null);
            Assertions.assertEquals("redirect:/admin/orders", view);
        }
    }

    @Test
    void getOrderUser() {
        String userName = "user";
        UserDTO user = new UserDTO();
        user.setRoles("ROLE_USER");

        Authentication mockAuth = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuth);
        when(mockAuth.getName()).thenReturn(userName);
        when(mockUserService.findUserByUserName(userName)).thenReturn(user);

        try (MockedStatic<SecurityContextHolder> mockContext = Mockito.mockStatic(SecurityContextHolder.class)) {
            mockContext.when(SecurityContextHolder::getContext)
                    .thenReturn(mockSecurityContext);

            try (MockedStatic<RequestContextUtils> mockFlashMap = Mockito.mockStatic(RequestContextUtils.class)) {
                mockFlashMap.when(() -> RequestContextUtils.getInputFlashMap(mockRequest))
                        .thenReturn(null);

                OrdersController cut = new OrdersController(mockOrderService, mockUserService, mockCarService);
                String view = cut.getOrder(mockModel, mockRequest);
                Assertions.assertEquals("order", view);
            }
        }
    }

    @Test
    void checkoutOrder() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        try (MockedStatic<RequestContextUtils> mockFlashMap = Mockito.mockStatic(RequestContextUtils.class)) {
            mockFlashMap.when(() -> RequestContextUtils.getInputFlashMap(mockRequest))
                    .thenReturn(null);

            OrdersController cut = new OrdersController(mockOrderService, mockUserService, mockCarService);
            String view = cut.checkoutOrder(mockModel, mockRequest);
            Assertions.assertEquals("checkout", view);
        }
    }

    @Test
    void checkoutOrderSave() {
        OrderDTO order = new OrderDTO();

        RedirectAttributes mockRa = mock(RedirectAttributes.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("action")).thenReturn("change");

        OrdersController cut = new OrdersController(mockOrderService, mockUserService, mockCarService);
        String view = cut.checkoutOrderSave(order, mockRa, mockRequest);

        verify(mockRa).addFlashAttribute("order", order);

        Assertions.assertEquals("redirect:/order", view);
    }

    @Test
    void checkoutOrderSaveSubmit() {
        OrderDTO mockOrder = mock(OrderDTO.class);

        RedirectAttributes mockRa = mock(RedirectAttributes.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("action")).thenReturn("submit");

        String userName = "user";
        UserDTO user = new UserDTO();
        user.setRoles("ROLE_USER");

        Authentication mockAuth = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuth);
        when(mockAuth.getName()).thenReturn(userName);
        when(mockUserService.findUserByUserName(userName)).thenReturn(user);

        try (MockedStatic<SecurityContextHolder> mockContext = Mockito.mockStatic(SecurityContextHolder.class)) {
            mockContext.when(SecurityContextHolder::getContext)
                    .thenReturn(mockSecurityContext);

            OrdersController cut = new OrdersController(mockOrderService, mockUserService, mockCarService);
            String view = cut.checkoutOrderSave(mockOrder, mockRa, mockRequest);

            verify(mockOrder).setUser(user);
            verify(mockOrderService).save(mockOrder);
            verify(mockRa).addFlashAttribute("message", "");

            Assertions.assertEquals("redirect:/order", view);
        }
    }

    @Test
    void saveOrderNoCar() {
        RedirectAttributes mockRa = mock(RedirectAttributes.class);

        OrderDTO order = mock(OrderDTO.class);

        List<CarDTO> cars = new ArrayList<>();
        when(mockCarService.findByCategoryAndStatusAndPassengers(order.getCategory(), 1, order.getPassengers())).thenReturn(cars);

        OrdersController cut = new OrdersController(mockOrderService, mockUserService, mockCarService);
        String view = cut.saveOrder(order, mockRa);

        verify(mockRa).addFlashAttribute("error", "");

        Assertions.assertEquals("redirect:/order", view);
    }

    @Test
    void saveOrder() {
        RedirectAttributes mockRa = mock(RedirectAttributes.class);
        OrderDTO order = mock(OrderDTO.class);

        List<CarDTO> cars = new ArrayList<>();
        cars.add(new CarDTO());

        when(mockCarService.findByCategoryAndStatusAndPassengers(order.getCategory(), 1, order.getPassengers())).thenReturn(cars);

        OrdersController cut = new OrdersController(mockOrderService, mockUserService, mockCarService);
        String view = cut.saveOrder(order, mockRa);

        Assertions.assertEquals("redirect:/order/checkout/", view);
    }

    @Test
    void getOrders() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        try (MockedStatic<RequestContextUtils> mockFlashMap = Mockito.mockStatic(RequestContextUtils.class)) {
            mockFlashMap.when(() -> RequestContextUtils.getInputFlashMap(mockRequest))
                    .thenReturn(null);

            when(mockUserService.findUserById(0)).thenReturn(null);
            when(mockOrderService.findByOrderDateBetween(any(), any(), any())).thenReturn(null);

            OrdersController cut = new OrdersController(mockOrderService, mockUserService, mockCarService);
            String view = cut.getOrders(mockModel, mockRequest);

            verify(mockModel).addAttribute("orders", null);

            Assertions.assertEquals("orders", view);
        }
    }

    @Test
    void searchOrders() {
        Report report = new Report();
        RedirectAttributes mockRa = mock(RedirectAttributes.class);

        OrdersController cut = new OrdersController(mockOrderService, mockUserService, mockCarService);
        String view = cut.searchOrders(report, mockRa);

        verify(mockRa).addFlashAttribute("report", report);

        Assertions.assertEquals("redirect:/admin/orders", view);
    }
}