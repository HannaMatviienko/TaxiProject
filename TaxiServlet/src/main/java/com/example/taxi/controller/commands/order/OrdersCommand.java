package com.example.taxi.controller.commands.order;

import com.example.taxi.controller.commands.Command;
import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.dao.DateDAO;
import com.example.taxi.model.dao.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class OrdersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (request.getMethod().equals("GET")) {
            DateDAO date = DAOFactory.getDate();
            request.setAttribute("dateFrom", date.getNow());
            request.setAttribute("dateTo", date.getNow());
            request.setAttribute("userId", 0);
            request.setAttribute("pageCurrent", 1);
            request.setAttribute("pageCount", 0);
            request.setAttribute("sort", "order_date");
            request.setAttribute("sortDir", "DESC");
        } else {
            String dateFrom = request.getParameter("dateFrom");
            String dateTo = request.getParameter("dateTo");
            String userId = request.getParameter("userId");

            int id;
            try {
                id = Integer.parseInt(userId);
            } catch (NumberFormatException ignored) {
                id = 0;
            }
            request.setAttribute("userId", id);

            int pageCurrent;
            try {
                pageCurrent = Integer.parseInt(request.getParameter("pageCurrent"));
            } catch (NumberFormatException ignored) {
                pageCurrent = 1;
            }

            String pageId = request.getParameter("page");
            int page = pageCurrent;
            if (pageId != null && pageId.startsWith("#")) {
                pageId = pageId.substring(1);
                switch (pageId) {
                    case "back":
                        if (page > 1) page--;
                        break;
                    case "next":
                        page++;
                        break;
                    default:
                        page = Integer.parseInt(pageId);
                        break;
                }
            }

            int limit = 10;
            String sort = request.getParameter("sort");
            String sortDir = request.getParameter("sortDir");

            OrderDAO orderDAO = DAOFactory.getOrder();
            try {
                int pageCount = DAOFactory.getOrder().getPageCount(dateFrom, dateTo, userId, limit);
                if (page > pageCount)
                    page = 1;
                request.setAttribute("orders", orderDAO.get(dateFrom, dateTo, userId, page, limit, sort, sortDir));
                request.setAttribute("dateFrom", dateFrom);
                request.setAttribute("dateTo", dateTo);
                request.setAttribute("pageCurrent", page);
                request.setAttribute("pageCount", pageCount);
                request.setAttribute("sort", sort);
                request.setAttribute("sortDir", sortDir);
            } catch (SQLException | ClassNotFoundException ex) {
                throw new ServletException(ex);
            }
        }

        try {
            request.setAttribute("users", DAOFactory.getUser().getUsers());
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }
        return "/WEB-INF/jsp/orders.jsp";
    }
}
