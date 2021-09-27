package com.taxi.services;

import com.taxi.controllers.Report;
import com.taxi.dto.OrderDTO;
import com.taxi.repositories.OrderRepository;
import com.taxi.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository repo;

    @Autowired
    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public void save(OrderDTO user) {
        repo.save(user);
    }

    public List<OrderDTO> findByOrderDateBetween(Date dateFrom, Date dateTo, Report report)
    {
        Page<OrderDTO> page = repo.findByOrderDateBetween(dateFrom, dateTo, getRequest(report));
        report.setPageCount(page.getTotalPages());
        if (report.getPageCurrent() > report.getPageCount())
        {
            report.setPageCurrent(1);
            page = repo.findByOrderDateBetween(dateFrom, dateTo, getRequest(report));
        }
        return page.get().collect(Collectors.toList());
    }

    public List<OrderDTO> findByOrderDateBetweenAndUser(Date dateFrom, Date dateTo, UserDTO user, Report report)
    {
        Page<OrderDTO> page = repo.findByOrderDateBetweenAndUser(dateFrom, dateTo, user, getRequest(report));
        report.setPageCount(page.getTotalPages());
        if (report.getPageCurrent() > report.getPageCount())
        {
            report.setPageCurrent(1);
            page = repo.findByOrderDateBetweenAndUser(dateFrom, dateTo, user, getRequest(report));
        }
        return page.get().collect(Collectors.toList());
    }

    private PageRequest getRequest(Report report)
    {
        Sort sort = Sort.by(report.getSort());
        if (Objects.equals(report.getSortDir(), "DESC"))
            sort = sort.descending();
        else
            sort = sort.ascending();
        return PageRequest.of(report.getPageCurrent()-1, report.getPageSize(), sort);
    }
}



