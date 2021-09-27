package com.example.taxi.model.dao;

import com.example.taxi.model.entity.Order;
import com.example.taxi.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {


    public void save(User user, Order order) throws SQLException, ClassNotFoundException {

        Connection connection = ConnectionPool.getConnection();
        String sql = "INSERT INTO orders (user_id, address_from, address_to, category, passengers, price, status ) VALUES (?, ?, ?, ?, ?, ?, 0)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, user.getId());
        statement.setString(2, order.getAddressFrom());
        statement.setString(3, order.getAddressTo());
        statement.setInt(4, order.getCategory());
        statement.setInt(5, order.getPassengers());
        statement.setDouble(6, order.getPrice());

        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Creating order failed, no rows affected.");
        }

        connection.close();
    }

    public List<Order> get(String dateFrom, String dateTo, String userId, int page, int limit, String sort, String sortDir) throws SQLException, ClassNotFoundException {

        Connection connection = ConnectionPool.getConnection();

        int id = 0;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException ignored) {

        }

        String sql;
        if (id  > 0)
            sql = "SELECT * FROM orders WHERE order_date >= ? AND order_date <= ? AND user_id = ?";
        else
            sql = "SELECT * FROM orders WHERE order_date >= ? AND order_date <= ?";
        sql = sql + " ORDER BY " + sort  + " " + sortDir;
        sql = sql + " LIMIT " + (page-1) * limit + "," + limit;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(dateFrom));
        statement.setDate(2, Date.valueOf(dateTo));
        if (id > 0)
            statement.setInt(3, id);

        List<Order> orderList = new ArrayList<>();
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Order order = new Order();
            order.setId(result.getInt("id"));
            //order.setUser(result.getInt("user_id"));
            order.setAddressFrom(result.getString("address_from"));
            order.setAddressTo(result.getString("address_to"));
            order.setCategory(result.getInt("category"));
            order.setPassengers(result.getInt("passengers"));
            order.setPrice(result.getDouble("price"));
            order.setStatus(result.getInt("status"));
            order.setOrderDate(result.getDate("order_date"));
            orderList.add(order);
        }
        connection.close();
        return orderList;
    }

    public int getPageCount(String dateFrom, String dateTo, String userId, int limit) throws SQLException, ClassNotFoundException {

        Connection connection = ConnectionPool.getConnection();

        int id = 0;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException ignored) {

        }

        String sql;
        if (id  > 0)
            sql = "SELECT count(*) FROM orders WHERE order_date >= ? AND order_date <= ? AND user_id = ?";
        else
            sql = "SELECT count(*) FROM orders WHERE order_date >= ? AND order_date <= ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(dateFrom));
        statement.setDate(2, Date.valueOf(dateTo));
        if (id > 0)
            statement.setInt(3, id);

        List<Order> orderList = new ArrayList<>();
        ResultSet result = statement.executeQuery();

        int cn = 0;
        if (result.next()) {
            cn = result.getInt(1);
        }
        connection.close();

        if (cn > 0)
        {
            int remainder = cn % limit;
            cn = cn / limit;
            if (remainder > 0) cn++;
        }

        return cn;
    }
}
