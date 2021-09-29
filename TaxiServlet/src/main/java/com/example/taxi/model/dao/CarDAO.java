package com.example.taxi.model.dao;

import com.example.taxi.model.entity.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    public List<Car> findByCategoryAndStatusAndPassengers(Integer category, Integer status, Integer passengers) throws SQLException,
            ClassNotFoundException {
        List<Car> list = new ArrayList<>();

        List<Car> cars = findByCategoryAndStatus(category, status);
        int left = passengers;
        for (Car car : cars) {
            list.add(car);
            left = left - car.getPassengers();
            if (left <= 0) break;
        }

        if (left > 0)
            list.clear();

        return list;
    }

    private List<Car> findByCategoryAndStatus(Integer category, Integer status) throws SQLException {
        List<Car> list = new ArrayList<>();

        Connection connection = ConnectionPool.getConnection();
        String sql = "SELECT * FROM cars WHERE category = ? AND status = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, category);
        statement.setInt(2, status);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Car car = new Car();
            car.setId(result.getInt("id"));
            car.setCategory(result.getInt("category"));
            car.setModel(result.getString("model"));
            car.setPlate(result.getString("plate"));
            car.setPassengers(result.getInt("passengers"));
            list.add(car);
        }

        connection.close();
        return list;
    }

    public List<Car> get() throws SQLException,
            ClassNotFoundException {

        Connection connection = ConnectionPool.getConnection();
        String sql = "SELECT * FROM cars";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet result = statement.executeQuery();
        List<Car> list = new ArrayList<>();
        while (result.next()) {
            list.add(initCar(result, new Car()));
        }
        connection.close();
        return list;
    }

    public Car get(int id) throws SQLException,
            ClassNotFoundException {

        Connection connection = ConnectionPool.getConnection();
        String sql = "SELECT * FROM cars WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();
        Car car = null;
        if (result.next()) {
            car = new Car();
            initCar(result, car);
        }
        connection.close();
        return car;
    }

    private Car initCar(ResultSet result, Car car) throws SQLException {
        car.setId(result.getInt("id"));
        car.setCategory(result.getInt("category"));
        car.setModel(result.getString("model"));
        car.setPlate(result.getString("plate"));
        car.setStatus(result.getInt("status"));
        car.setPassengers(result.getInt("passengers"));
        return car;
    }

    public void save(Car car) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionPool.getConnection();
        String sql;
        if (car.getId() == -1)
            sql = "INSERT INTO cars (category, model, plate, status, passengers) VALUES (?, ?, ?, ?, ?)";
        else
            sql = "UPDATE cars SET category = ?, model = ?, plate = ?, status = ?, passengers = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, car.getCategory());
        statement.setString(2, car.getModel());
        statement.setString(3, car.getPlate());
        statement.setInt(4, car.getStatus());
        statement.setInt(5, car.getPassengers());
        if (car.getId() != -1)
            statement.setInt(6, car.getId());

        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Creating car failed, no rows affected.");
        }
        connection.close();
    }

    public void delete(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionPool.getConnection();
        String sql = "DELETE FROM cars WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Deleting car failed, no rows affected.");
        }
        connection.close();
    }
}