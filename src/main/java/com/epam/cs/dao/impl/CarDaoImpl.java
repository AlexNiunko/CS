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

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class CarDaoImpl extends BaseDao<Car> implements CarDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String AVAILABLE = "available";
    private static final String NOT_AVAILABLE = "not available";
    private static final String FREE = "free";
    private static final String BUSY = "busy";
    private static final String UPDATE_STATUS_CAR_BY_ID = "UPDATE cars SET current_status=? WHERE id_cars=?";
    private static final String SELECT_ALL_CARS = "SELECT id_cars,color,model,body_type,current_mileage,engine_volume,registration_number,year_of_issue,cost_per_minute,car_condition,current_status,photo,name_parking_place from cars INNER JOIN parking_places ON parking_places.id_parking_place=id_parking ";
    private static final String FIND_ID_CAR_BY_PARAM = "SELECT id_cars FROM cars WHERE color=?,model=?,registration_number=?,cost_per_minute=?";
    private static final String SELECT_CAR_BY_ID = "SELECT color,model,registration_number,cost_per_minute,photo,name_parking_place FROM cars INNER JOIN parking_places ON parking_places.id_parking_place=id_parking WHERE id_cars=?";
    private static CarDaoImpl instance = new CarDaoImpl();

    private CarDaoImpl() {
    }

    public static CarDaoImpl getInstance() {
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
    public ArrayList<Car> findAll() throws DaoException {
        ArrayList<Car> carList = new ArrayList<>();
        Optional<Car> optionalCar;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CARS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Car car = new Car();
                    car.setColor(resultSet.getString(AttributeName.CAR_COLOR));
                    car.setModel(resultSet.getString(AttributeName.CAR_MODEL));
                    car.setBodyType(resultSet.getString(AttributeName.CAR_BODY_TYPE));
                    car.setCurrentMileage(resultSet.getInt(AttributeName.CAR_CURRENT_MILEAGE));
                    car.setEngineVolume(resultSet.getDouble(AttributeName.CAR_ENGINE_VOLUME));
                    car.setRegistrationNumber(resultSet.getString(AttributeName.CAR_REGISTRATION_NUMBER));
                    car.setYearOfIssue(resultSet.getString(AttributeName.CAR_YEAR_OF_ISSUE));
                    car.setCostPerMinute(BigDecimal.valueOf(resultSet.getDouble(AttributeName.CAR_COST_PER_MINUTE)));
                    car.setCondition(parseConditionOrStatusFromDB(resultSet.getString(AttributeName.CAR_CONDITION)));
                    car.setCurrentStatus(parseConditionOrStatusFromDB(resultSet.getString(AttributeName.CAR_CURRENT_STATUS)));
                    car.setParkingPlace(resultSet.getString(AttributeName.CAR_PARKING_PLACE));
                    car.setId(Integer.parseInt(resultSet.getString(AttributeName.ID_CAR)));
                    Blob blobPhoto = resultSet.getBlob(AttributeName.CAR_PHOTO);
                    if (blobPhoto != null) {
                        byte[] imageContent = blobPhoto.getBytes(1, (int) blobPhoto.length());
                        String encodeBase64 = ImageConverter.imageToString(imageContent);
                        car.setPhoto(encodeBase64);
                    }
                    optionalCar = Optional.of(car);
                    if (optionalCar.isPresent()) {
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
    public boolean findIdCarByParam(String id, Car car) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ID_CAR_BY_PARAM)) {
            statement.setString(1, car.getColor());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getRegistrationNumber());
            statement.setString(4, car.getCostPerMinute().toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getString(1);
                    result = true;
                } else {
                    logger.error("Current car is not exist");
                    result = false;
                }
            }

        } catch (SQLException e) {
            logger.error("Can't find ID car ", e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<Car> findCarByID(String idCar) throws DaoException {
        Optional<Car> optionalCar;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CAR_BY_ID)) {
            statement.setString(1, idCar);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Car car = new Car();
                    car.setModel(resultSet.getString(AttributeName.CAR_MODEL));
                    car.setColor(resultSet.getString(AttributeName.CAR_COLOR));
                    car.setCostPerMinute(BigDecimal.valueOf(resultSet.getDouble(AttributeName.CAR_COST_PER_MINUTE)));
                    car.setRegistrationNumber(resultSet.getString(AttributeName.CAR_REGISTRATION_NUMBER));
                    car.setParkingPlace(resultSet.getString(AttributeName.CAR_PARKING_PLACE));
                    Blob blobPhoto = resultSet.getBlob(AttributeName.CAR_PHOTO);
                    if (blobPhoto != null) {
                        byte[] imageContent = blobPhoto.getBytes(1, (int) blobPhoto.length());
                        String encodeBase64 = ImageConverter.imageToString(imageContent);
                        car.setPhoto(encodeBase64);
                    }
                    optionalCar = Optional.of(car);
                    logger.warn("Car by id {} is {}",idCar,car);
                } else {
                    optionalCar = Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.error("Can't find ID car ", e);
            throw new DaoException(e);
        }
        return optionalCar;
    }

    @Override
    public boolean update(Car car) throws DaoException {
        return true;
    }

    @Override
    public boolean updateCarCurrentStatus(Car car) throws DaoException {
        int result = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_CAR_BY_ID)) {
            statement.setString(1, parseStatusToDB(car.getCurrentStatus()));
            statement.setString(2,String.valueOf(car.getId()));
            result= statement.executeUpdate();
            logger.warn("result statement is {}",result);

        } catch (SQLException e) {
            logger.error("Error while update car currrent status",e);
            throw new DaoException(e);
        }
        return result==1;
    }

    private Boolean parseConditionOrStatusFromDB(String attributeName) {
        Boolean conditionOrStatus = null;
        switch (attributeName.toLowerCase()) {
            case AVAILABLE -> conditionOrStatus = true;
            case NOT_AVAILABLE -> conditionOrStatus = false;
            case BUSY -> conditionOrStatus = false;
            case FREE -> conditionOrStatus = true;
        }
        return conditionOrStatus;
    }

    private String parseStatusToDB(Boolean status) {
        if (status) {
            return FREE;
        } else
            return BUSY;
    }
}
