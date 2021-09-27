package com.example.taxi.controller.commands.user;

import com.example.taxi.controller.commands.Command;
import com.example.taxi.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ServletContext context = request.getServletContext();
        context.removeAttribute("user");
        context.setAttribute("user", new User(0, User.ROLE.GUEST));
        return "/WEB-INF/jsp/login.jsp";
    }
}

