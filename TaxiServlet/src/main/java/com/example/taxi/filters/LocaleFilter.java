package com.example.taxi.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "LocaleFilter", urlPatterns = {"/*"})
public class LocaleFilter implements Filter {

    private static final String DEFAULT_LOCALE = "ua";
    private static final String PARAM_NAME = "lang";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getParameter(PARAM_NAME) != null) {
            req.getSession().setAttribute(PARAM_NAME, req.getParameter(PARAM_NAME));
            Cookie cookie = new Cookie(PARAM_NAME, req.getParameter(PARAM_NAME));
            cookie.setMaxAge(60*60*24);
            res.addCookie(cookie);
        } else {
            Object attr = req.getSession().getAttribute(PARAM_NAME);
            if (attr == null) {
                boolean isLang = false;
                Cookie[] cookies = req.getCookies();
                if (cookies != null) {
                    for (Cookie ck : cookies) {
                        if (PARAM_NAME.equals(ck.getName())) {
                            req.getSession().setAttribute(PARAM_NAME, ck.getValue());
                            isLang = true;
                        }
                    }
                }
                if (!isLang) {
                    req.getSession().setAttribute(PARAM_NAME, DEFAULT_LOCALE);
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) {
    }
}