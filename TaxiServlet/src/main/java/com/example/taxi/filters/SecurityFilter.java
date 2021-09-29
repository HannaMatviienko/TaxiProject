package com.example.taxi.filters;

import com.example.taxi.model.entity.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            user = new User(0, User.ROLE.GUEST);
            session.setAttribute("user", user);
        }

        if (isAccessOpen(user.getRole(), req.getServletPath())) {
            chain.doFilter(request, response);
        } else {
            if (user.getRole() == User.ROLE.GUEST) {
                res.sendRedirect("user/login");
            } else {
                req.getRequestDispatcher("/noAccess.jsp").forward(request, response);
            }
        }
    }

    private boolean isAccessOpen(User.ROLE role, String path) {
        boolean isAccessOpen = path.matches("(/index.jsp)|(/resources.*)");
        if (isAccessOpen) return true;

        if(role == User.ROLE.GUEST || role == User.ROLE.USER || role == User.ROLE.ADMIN)
            isAccessOpen = path.matches("(/user/(signup|login|logout))");
        if (isAccessOpen) return true;

        if (role == User.ROLE.USER || role == User.ROLE.ADMIN)
            isAccessOpen = path.matches("(/order.*)");
        if (isAccessOpen) return true;

        if (role == User.ROLE.ADMIN)
            isAccessOpen = path.matches("(/admin.*)");

        return isAccessOpen;
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) {
    }
}