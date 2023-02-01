package com.epam.cs.entity;

import com.epam.cs.dao.impl.CarDaoImpl;
import com.epam.cs.exception.DaoException;

import java.util.ArrayList;
import java.util.StringJoiner;

public class CarList {
    CarDaoImpl carDao=CarDaoImpl.getInstance();
    private ArrayList<Car> list=carDao.findAll();

    public CarList() throws DaoException {

    }


    public ArrayList<Car> getList() {
        return list;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CarList.class.getSimpleName() + "[", "]")
                .add("list=" + list)
                .toString();
    }
}
