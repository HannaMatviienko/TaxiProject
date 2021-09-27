package com.example.taxi.controller.commands.user;

import com.example.taxi.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserNewCommandTest {

    @Test
    void execute() throws ServletException {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        UserNewCommand command = new UserNewCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("mode", 0);
        verify(mockRequest).setAttribute("user", new User());

        Assertions.assertEquals("/WEB-INF/jsp/user.jsp", page);
    }
}