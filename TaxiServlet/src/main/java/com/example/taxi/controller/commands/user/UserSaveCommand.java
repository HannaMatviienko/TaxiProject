package com.example.taxi.controller.commands.user;

import com.example.taxi.controller.commands.Command;
import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.entity.User;
import com.example.taxi.tools.PBKDF2Hasher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class UserSaveCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            User user = new User();
            user.setId(Integer.parseInt(request.getParameter("id")));
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setPassword(new PBKDF2Hasher().hash(request.getParameter("password").toCharArray()));
            user.setRole(User.ROLE.parseRole(request.getParameter("roles")));
            user.setEmail(request.getParameter("email"));
            DAOFactory.getUser().save(user);
            return "redirect:/admin/users";
        } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
            throw new ServletException(ex);
        }
    }
}