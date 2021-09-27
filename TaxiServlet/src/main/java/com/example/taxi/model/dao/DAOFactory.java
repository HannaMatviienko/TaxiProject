package com.example.taxi.model.dao;


public class DAOFactory {

    private DAOFactory() {
    }

    private static CarDAO carDAO = null;

    public static CarDAO getCar() {
        if (carDAO == null)
            carDAO = new CarDAO();
        return carDAO;
    }

    public static void setCar(CarDAO carDAO) {
        DAOFactory.carDAO = carDAO;
    }

    private static UserDAO userDAO = null;

    public static UserDAO getUser() {
        if (userDAO == null)
            userDAO = new UserDAO();
        return userDAO;
    }

    public static void setUser(UserDAO userDAO) {
        DAOFactory.userDAO = userDAO;
    }

    private static OrderDAO orderDAO = null;

    public static OrderDAO getOrder() {
        if (orderDAO == null)
            orderDAO = new OrderDAO();
        return orderDAO;
    }

    public static void setOrder(OrderDAO orderDAO) {
        DAOFactory.orderDAO = orderDAO;
    }

    private static DateDAO dateDAO = null;

    public static DateDAO getDate() {
        if (dateDAO == null)
            dateDAO = new DateDAO();
        return dateDAO;
    }

    public static void setDate(DateDAO dateDAO) {
        DAOFactory.dateDAO = dateDAO;
    }

}
