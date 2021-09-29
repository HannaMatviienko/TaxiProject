package com.taxi.controllers;

import com.taxi.dto.CarDTO;
import com.taxi.dto.OrderDTO;
import com.taxi.services.OrderService;
import com.taxi.services.CarService;
import com.taxi.dto.UserDTO;
import com.taxi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class OrdersController {

    private final OrderService orderService;
    private final UserService userService;
    private final CarService carService;

    @Autowired
    public OrdersController(OrderService orderService, UserService userService, CarService carService) {
        this.orderService = orderService;
        this.userService = userService;
        this.carService = carService;
    }

    @GetMapping("/order")
    public String getOrder(Model model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.findUserByEmail(auth.getName());
        if (Objects.equals(user.getRoles(), "ROLE_ADMIN")) {
            return "redirect:/admin/orders";
        } else {
            Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
            if (inputFlashMap == null || !inputFlashMap.containsKey("order"))
                model.addAttribute("order", new OrderDTO());
            return "order";
        }
    }

    @GetMapping("/order/checkout")
    public String checkoutOrder(Model model, HttpServletRequest request) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap == null || !inputFlashMap.containsKey("order"))
            model.addAttribute("order", new OrderDTO());
        return "checkout";
    }

    @PostMapping("/order/checkout/save")
    public String checkoutOrderSave(OrderDTO order, RedirectAttributes ra, HttpServletRequest request) {
        String action = request.getParameter("action");
        if (Objects.equals(action, "submit")) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = userService.findUserByEmail(auth.getName());
            order.setUser(user);
            order.setOrderDate(new Date());
            orderService.save(order);
            ra.addFlashAttribute("message", "");
        } else {
            ra.addFlashAttribute("order", order);
        }
        return "redirect:/order";
    }

    @PostMapping("/order/save")
    public String saveOrder(OrderDTO order, RedirectAttributes ra) {
        List<CarDTO> cars = carService.findByCategoryAndStatusAndPassengers(order.getCategory(), 1, order.getPassengers());
        order.setCars(cars);

        ra.addFlashAttribute("order", order);
        if (cars.isEmpty()) {
            ra.addFlashAttribute("error", "");
            return "redirect:/order";
        } else {
            return "redirect:/order/checkout/";
        }
    }

    @GetMapping("/admin/orders")
    public String getOrders(Model model, HttpServletRequest request) {
        Report report;
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap == null || !inputFlashMap.containsKey("report"))
            report = new Report();
        else
            report = (Report) inputFlashMap.get("report");

        UserDTO user = userService.findUserById(report.getUserId());
        List<OrderDTO> orders;

        if (user == null)
            orders = orderService.findByOrderDateBetween(java.sql.Date.valueOf(report.getDateFrom()), java.sql.Date.valueOf(report.getDateTo()), report);
        else
            orders = orderService.findByOrderDateBetweenAndUser(java.sql.Date.valueOf(report.getDateFrom()), java.sql.Date.valueOf(report.getDateTo()), user, report);
        model.addAttribute("orders", orders);
        model.addAttribute("report", report);
        model.addAttribute("users", userService.listUsers());
        return "orders";
    }

    @PostMapping("/admin/orders/search")
    public String searchOrders(Report report, RedirectAttributes ra) {
        ra.addFlashAttribute("report", report);
        return "redirect:/admin/orders";
    }
}