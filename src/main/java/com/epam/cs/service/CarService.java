package com.epam.cs.service;

import com.epam.cs.entity.Car;
import com.epam.cs.entity.Order;
import com.epam.cs.exception.ServiceException;

import java.util.List;

public interface CarService {
    List<Car> showAvailableCar() throws ServiceException;
    List<Car> showEconomyCar() throws ServiceException;
    List<Car> showPremiumCar() throws ServiceException;
    List<Car> showBusinessCar() throws ServiceException;
    List<Car> showStationWagonCar() throws ServiceException;
    List<Car> showFreeCars() throws ServiceException;
    List<Car> showFreeEconomyCars() throws ServiceException;
    List<Car> showFreePremiumCars() throws ServiceException;
    List<Car> showFreeBusinessCar() throws ServiceException;
    List<Car> showFreeStationWagonCars() throws ServiceException;
    boolean getCarById(String id,Car car) throws ServiceException;
    boolean updateCarCurrentStatus(Car car) throws ServiceException;


    boolean updateCarCurrentStatus(Car car, Order order) throws ServiceException;
}
