package com.example.taxi.controller.commands.user;

import com.example.taxi.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

class LogOutCommandTest {

    @Test
    void execute() throws ServletException {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(mockRequest.getSession()).thenReturn(mockSession);

        LogOutCommand command = new LogOutCommand();
        String page = command.execute(mockRequest, null);

        verify(mockSession).removeAttribute("user");
        verify(mockSession).setAttribute("user", new User(0, User.ROLE.GUEST));

        Assertions.assertEquals("/WEB-INF/jsp/login.jsp", page);
    }
}