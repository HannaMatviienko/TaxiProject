package com.taxi.controllers;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Report {
    private Integer userId;
    private String dateFrom;
    private String dateTo;
    private int pageCurrent;
    private int pageCount;
    private int pageSize;
    private String sort;
    private String sortDir;

    public Report()
    {
        userId = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String now = formatter.format(date);
        dateFrom = now;
        dateTo = now;
        pageCurrent = 1;
        pageSize = 10;
        pageCount = 0;
        sort = "orderDate";
        sortDir = "DESC";
    }
}
