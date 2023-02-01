package com.epam.cs.dao.impl;

import com.epam.cs.command.AttributeName;
import com.epam.cs.dao.BaseDao;
import com.epam.cs.dao.CarDao;
import com.epam.cs.entity.Car;
import com.epam.cs.exception.DaoException;
import com.epam.cs.pool.ConnectionPool;
import com.epam.cs.util.ImageConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class CarDaoImpl extends BaseDao<Car> implements CarDao  {
    private static final Logger logger = LogManager.getLogger();
    private static final String AVAILABLE="available";
    private static final String NOT_AVAILABLE="not available";
    private static final String FREE="free";
    private static final String BUSY="busy";
    public static final String SELECT_ALL_CARS = "SELECT color,model,body_type,current_mileage,engine_volume,registration_number,year_of_issue,cost_per_minute,car_condition,current_status,photo from cars";
    private static CarDaoImpl instance=new CarDaoImpl();
    private CarDaoImpl() {
    }
    public static CarDaoImpl getInstance(){
        return instance;
    }

    @Override
    public boolean insert(Car car) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Car car) throws DaoException {
        return false;
    }

    @Override
    public ArrayList<Car> findAll( ) throws DaoException {

        ArrayList<Car>carList=new ArrayList<>();
        Optional<Car>optionalCar;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CARS)) {
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Car car=new Car();
                    car.setColor(resultSet.getString(AttributeName.CAR_COLOR));
                    car.setModel(resultSet.getString(AttributeName.CAR_MODEL));
                    car.setBodyType(resultSet.getString(AttributeName.CAR_BODY_TYPE));
                    car.setCurrentMileage(resultSet.getInt(AttributeName.CAR_CURRENT_MILEAGE));
                    car.setEngineVolume(resultSet.getDouble(AttributeName.CAR_ENGINE_VOLUME));
                    car.setRegistrationNumber(resultSet.getString(AttributeName.CAR_REGISTRATION_NUMBER));
                    car.setYearOfIssue(resultSet.getString(AttributeName.CAR_YEAR_OF_ISSUE));
                    car.setCostPerMinute(BigDecimal.valueOf(resultSet.getDouble(AttributeName.CAR_COST_PER_MINUTE)));
                    car.setCondition(parseConditionOrStatus(resultSet.getString(AttributeName.CAR_CONDITION)));
                    car.setCurrentStatus(parseConditionOrStatus(resultSet.getString(AttributeName.CAR_CURRENT_STATUS)));
                    Blob blobPhoto= resultSet.getBlob(AttributeName.CAR_PHOTO);
                    if (blobPhoto!=null){
                        byte[]imageContent= blobPhoto.getBytes(1,(int)blobPhoto.length());
                        String encodeBase64= ImageConverter.imageToString(imageContent);
                        car.setPhoto(encodeBase64);
                    }
                    optionalCar=Optional.of(car);
                    if (optionalCar.isPresent()){
                        carList.add(optionalCar.get());
                    } else {
                        logger.warn("Failed to select a car from the DB");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return carList;
    }

    @Override
    public Car update(Car car) throws DaoException {
        return null;
    }
    private Boolean parseConditionOrStatus( String attributeName){
        Boolean conditionOrStatus=null;
        switch (attributeName.toLowerCase()){
            case AVAILABLE ->conditionOrStatus=true;
            case NOT_AVAILABLE ->conditionOrStatus=false;
            case BUSY ->conditionOrStatus=false;
            case FREE ->conditionOrStatus=true;
        }
        return conditionOrStatus;
    }
}
