package lab4.tau.carRest.service;

// w oparciu o przyklad J Neumanna, przerobiony przez T Puzniakowskiego

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import lab4.tau.carRest.domain.Car;

public interface CarManager {
	public Connection getConnection();
	public void setConnection(Connection connection) throws SQLException;
	public int addCar(Car car);
	public int deleteCar(Car car);
	public int updateCar(Car car) throws SQLException;
	public Car getCar(long id) throws SQLException;
	public String introduceSelf();
	public int deleteAll();
	public List<Car> getAllCars();
}
