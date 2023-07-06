package com.epam.cs.dao;

import com.epam.cs.entity.Car;
import com.epam.cs.exception.DaoException;

import java.util.Optional;

public interface CarDao {
    boolean findIdCarByParam(String id,Car car) throws DaoException;
    Optional<Car>findCarByID(String idCar) throws DaoException;
    boolean updateCarCurrentStatus(Car car) throws DaoException;

}
