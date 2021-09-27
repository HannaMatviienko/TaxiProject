package com.example.taxi.controller.commands.user;

import com.example.taxi.controller.commands.Command;
import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class SignUpCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (request.getMethod().equals("GET")) {
            return "/WEB-INF/jsp/signup.jsp";
        }
        else
        {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String userName = request.getParameter("userName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            try {
                User user = DAOFactory.getUser().newUser(firstName, lastName, userName, email, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    request.setAttribute("username", user.getLogin());
                    return "redirect:/order";
                } else {
                    String message = "Invalid email/password";
                    request.setAttribute("message", message);

                    request.setAttribute("firstName", firstName);
                    request.setAttribute("lastName", lastName);
                    request.setAttribute("userName", userName);
                    request.setAttribute("email", email);
                    request.setAttribute("password", password);
                    return "/WEB-INF/jsp/signup.jsp";
                }
            } catch (SQLException | ClassNotFoundException ex) {
                throw new ServletException(ex);
            }
        }
    }
}