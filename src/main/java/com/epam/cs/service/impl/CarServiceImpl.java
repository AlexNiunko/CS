package com.epam.cs.service.impl;

import com.epam.cs.dao.impl.CarDaoImpl;
import com.epam.cs.dao.impl.OrderDaoImpl;
import com.epam.cs.entity.Car;
import com.epam.cs.entity.Order;
import com.epam.cs.exception.DaoException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    private static CarServiceImpl instance = new CarServiceImpl();

    private CarServiceImpl() {

    }

    public static CarServiceImpl getInstance() {
        return instance;
    }

    private static final Logger logger = LogManager.getLogger();
    private static final BigDecimal MAX_PRICE_ECONOMY=new BigDecimal("1.3");
    private static final BigDecimal MIN_PRICE_PREMIUM=new BigDecimal("3.1");
    private static final String BODY_TYPE_STATION_WAGON="station wagon";

    @Override
    public List<Car> showAvailableCar() throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        List<Car> availableCar;
        try {
            List<Car> allCars = carDao.findAll();
            availableCar = allCars.stream()
                    .filter(car -> car.getCondition().equals(Boolean.TRUE))
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            logger.error("CarService error while get AllCars {} ", e.getMessage());
            throw new ServiceException();
        }
        return availableCar;
    }
    @Override
    public List<Car> showBusinessCar() throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        List<Car> availableBuisnessCar;
        try{
            List<Car>allCars=carDao.findAll();
            availableBuisnessCar=allCars.stream()
                    .filter(car -> car.getCondition().equals(Boolean.TRUE))
                    .filter(car ->car.getCostPerMinute().equals(car.getCostPerMinute().max(MAX_PRICE_ECONOMY)))
                    .filter(car -> car.getCostPerMinute().equals(car.getCostPerMinute().min(MIN_PRICE_PREMIUM)))
                    .filter(car -> !(car.getBodyType().equals(BODY_TYPE_STATION_WAGON)))
                    .collect(Collectors.toList());
        }catch (DaoException e) {
            logger.error("CarService error while get BusinessCars {} ", e.getMessage());
            throw new ServiceException();
        }
        return availableBuisnessCar;
    }

    @Override
    public List<Car> showEconomyCar() throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        List<Car> availableEconomyCar;
        try{
            List<Car>allCars=carDao.findAll();
            availableEconomyCar=allCars.stream()
                    .filter(car -> car.getCondition().equals(Boolean.TRUE))
                    .filter(car ->car.getCostPerMinute().equals(car.getCostPerMinute().min(MAX_PRICE_ECONOMY)))
                    .collect(Collectors.toList());
        }catch (DaoException e) {
            logger.error("CarService error while get Economy Cars {} ", e.getMessage());
            throw new ServiceException();
        }
        return availableEconomyCar;
    }

    @Override
    public List<Car> showPremiumCar() throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        List<Car> availableEconomyCar;
        try{
            List<Car>allCars=carDao.findAll();
            availableEconomyCar=allCars.stream()
                    .filter(car -> car.getCondition().equals(Boolean.TRUE))
                    .filter(car ->car.getCostPerMinute().equals(car.getCostPerMinute().max(MIN_PRICE_PREMIUM)))
                    .filter(car -> !(car.getBodyType().equals(BODY_TYPE_STATION_WAGON)))
                    .collect(Collectors.toList());
        }catch (DaoException e) {
            logger.error("CarService error while get Premium Cars {} ", e.getMessage());
            throw new ServiceException();
        }
        return availableEconomyCar;
    }
    @Override
    public List<Car> showStationWagonCar() throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        List<Car> availableStationWagonCar;
        try{
            List<Car>allCars=carDao.findAll();
            availableStationWagonCar=allCars.stream()
                    .filter(car -> car.getCondition().equals(Boolean.TRUE))
                    .filter(car -> car.getBodyType().equals(BODY_TYPE_STATION_WAGON))
                    .collect(Collectors.toList());
        }catch (DaoException e) {
            logger.error("CarService error while get Station Wagon Cars {} ", e.getMessage());
            throw new ServiceException();
        }
        return availableStationWagonCar;
    }

    @Override
    public List<Car> showFreeCars() throws ServiceException {
        CarDaoImpl carDao=CarDaoImpl.getInstance();
        List <Car>freeCars;
        try{
            List<Car>allCars=carDao.findAll();
            freeCars=allCars.stream()
                    .filter(car -> car.getCondition().equals(Boolean.TRUE))
                    .filter(car -> car.getCurrentStatus().equals(Boolean.TRUE))
                    .collect(Collectors.toList());
        }catch (DaoException e){
            logger.error("CarService error while get free cars {} ", e.getMessage());
            throw new ServiceException();
        }
        return freeCars;
    }

    @Override
    public List<Car> showFreeEconomyCars() throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        List<Car> availableFreeEconomyCar;
        try{
            List<Car>allCars=carDao.findAll();
            availableFreeEconomyCar=allCars.stream()
                    .filter(car -> car.getCondition().equals(Boolean.TRUE))
                    .filter(car -> car.getCurrentStatus().equals(Boolean.TRUE))
                    .filter(car ->car.getCostPerMinute().equals(car.getCostPerMinute().min(MAX_PRICE_ECONOMY)))
                    .collect(Collectors.toList());
        }catch (DaoException e) {
            logger.error("CarService error while get Free Economy Cars {} ", e.getMessage());
            throw new ServiceException();
        }
        return availableFreeEconomyCar;
    }

    @Override
    public List<Car> showFreePremiumCars() throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        List<Car> availableFreeEconomyCar;
        try{
            List<Car>allCars=carDao.findAll();
            availableFreeEconomyCar=allCars.stream()
                    .filter(car -> car.getCondition().equals(Boolean.TRUE))
                    .filter(car -> car.getCurrentStatus().equals(Boolean.TRUE))
                    .filter(car ->car.getCostPerMinute().equals(car.getCostPerMinute().max(MIN_PRICE_PREMIUM)))
                    .filter(car -> !(car.getBodyType().equals(BODY_TYPE_STATION_WAGON)))
                    .collect(Collectors.toList());
        }catch (DaoException e) {
            logger.error("CarService error while get Free Premium Cars {} ", e.getMessage());
            throw new ServiceException();
        }
        return availableFreeEconomyCar;
    }

    @Override
    public List<Car> showFreeBusinessCar() throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        List<Car> availableFreeBuisnessCar;
        try{
            List<Car>allCars=carDao.findAll();
            availableFreeBuisnessCar=allCars.stream()
                    .filter(car -> car.getCondition().equals(Boolean.TRUE))
                    .filter(car -> car.getCurrentStatus().equals(Boolean.TRUE))
                    .filter(car ->car.getCostPerMinute().equals(car.getCostPerMinute().max(MAX_PRICE_ECONOMY)))
                    .filter(car -> car.getCostPerMinute().equals(car.getCostPerMinute().min(MIN_PRICE_PREMIUM)))
                    .filter(car -> !(car.getBodyType().equals(BODY_TYPE_STATION_WAGON)))
                    .collect(Collectors.toList());
        }catch (DaoException e) {
            logger.error("CarService error while get Free BusinessCars {} ", e.getMessage());
            throw new ServiceException();
        }
        return availableFreeBuisnessCar;
    }

    @Override
    public List<Car> showFreeStationWagonCars() throws ServiceException {
        CarDaoImpl carDao = CarDaoImpl.getInstance();
        List<Car> availableFreeStationWagonCar;
        try{
            List<Car>allCars=carDao.findAll();
            availableFreeStationWagonCar=allCars.stream()
                    .filter(car -> car.getCondition().equals(Boolean.TRUE))
                    .filter(car ->car.getCurrentStatus().equals(Boolean.TRUE))
                    .filter(car -> car.getBodyType().equals(BODY_TYPE_STATION_WAGON))
                    .collect(Collectors.toList());
        }catch (DaoException e) {
            logger.error("CarService error while get Free Station Wagon Cars {} ", e.getMessage());
            throw new ServiceException();
        }
        return availableFreeStationWagonCar;
    }

    @Override
    public boolean getCarById(String id, Car car) throws ServiceException {
        Optional<Car>optionalCar;
        CarDaoImpl carDao=CarDaoImpl.getInstance();
        try {
            optionalCar=carDao.findCarByID(id);
            logger.warn("tram pam {}",optionalCar.get());
            if (optionalCar.isPresent()){
                car.setColor(optionalCar.get().getColor());
                car.setModel(optionalCar.get().getModel());
                car.setPhoto(optionalCar.get().getPhoto());
                car.setRegistrationNumber(optionalCar.get().getRegistrationNumber());
                car.setParkingPlace(optionalCar.get().getParkingPlace().toString());
                car.setCostPerMinute(optionalCar.get().getCostPerMinute());
                logger.warn("our car is {}",car);
                return true;
            }else {
                logger.error("Car with current ID isn't exist");
                return false;
            }
        } catch (DaoException e) {
            logger.error("Failed to select car from DB");
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateCarCurrentStatus(Car car) throws ServiceException {
        boolean result;
        CarDaoImpl carDao=CarDaoImpl.getInstance();
        logger.warn("Update method only car");
        try{
            result=(carDao.updateCarCurrentStatus(car) );
            logger.warn("{}",result);
        } catch (DaoException e){
            logger.error("Error while update car current status");
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updateCarCurrentStatus(Car car, Order order) throws ServiceException {
        boolean result;
        CarDaoImpl carDao=CarDaoImpl.getInstance();
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        logger.warn("Update method car and order");
        try{
            result=(carDao.updateCarCurrentStatus(car) && orderDao.deleteIdCarIntoOrder(order));
            logger.warn("Result update car and current order is {}",result);

        } catch (DaoException e){
            logger.error("Error while update car and order current status");
            throw new ServiceException(e);
        }
        return result;
    }
}
