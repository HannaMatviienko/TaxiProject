package com.example.taxi.model.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDAO {
    public String getNow()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
