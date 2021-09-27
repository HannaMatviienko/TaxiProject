package com.example.taxi.tools;

import javax.servlet.http.HttpServletRequest;

public class Tools {
    public static String getPath(HttpServletRequest request)
    {
        return request.getRequestURI().substring((request.getContextPath() + request.getServletPath()).length());
    }
}
