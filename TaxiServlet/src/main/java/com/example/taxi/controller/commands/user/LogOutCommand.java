package com.example.taxi.controller.commands.user;

import com.example.taxi.controller.commands.Command;
import com.example.taxi.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("user", new User(0, User.ROLE.GUEST));
        return "/WEB-INF/jsp/login.jsp";
    }
}