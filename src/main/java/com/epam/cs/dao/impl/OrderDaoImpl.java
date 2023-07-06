package com.epam.cs.dao.impl;

import com.epam.cs.command.AttributeName;
import com.epam.cs.dao.BaseDao;
import com.epam.cs.dao.OrderDao;
import com.epam.cs.entity.Car;
import com.epam.cs.entity.CreditCard;
import com.epam.cs.entity.Order;
import com.epam.cs.entity.OrderStatus;
import com.epam.cs.exception.DaoException;
import com.epam.cs.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String DELETE_PRE_ORDER="DELETE FROM orders WHERE id_order=?";
    private static final String DELETE_PAYMENT_METHOD_IN_ORDER="UPDATE orders SET payment_method=null WHERE id_order=?";
    private static final String DELETE_CARD_FROM_ORDER="UPDATE orders SET id_card=null WHERE id_order=?";
    private static final String SELECT_CREDIT_CARD_BY_ID="SELECT id_credit_card,card_number,date_expiry,name_card_holder,current_sum FROM cards WHERE id_credit_card=? ";
    private static final String INSERT_CREDIT_CARD_TO_ORDER="INSERT INTO orders (id_card) VALUES (?) WHERE id_order=? ";
    private static final String SELECT_CREDIT_CARD_BY_NUMBER_AND_NAME="SELECT id_credit_card FROM cards WHERE card_number=? AND name_card_holder=?";
    private static final String INSERT_CREDIT_CARD="INSERT INTO cards (card_number,date_expiry,name_card_holder,cvc,current_sum) values(?,?,?,?,?)";
    private static final String INSERT_PRE_ORDER_WITH_CREDIT_CARD ="INSERT INTO orders  (id_car,id_client,date_of_application,status,payment_method,id_card) values(?,?,?,?,?,?)";
    private static final String INSERT_PRE_ORDER_WITH_CASH="INSERT INTO orders  (id_car,id_client,date_of_application,status,payment_method) values(?,?,?,?,?)";
    private static final String INSERT_PAYMENT_METHOD_INTO_ORDER="INSERT INTO orders (payment_method) values(?) WHERE id_order=?";
    private static final String GET_ID_ORDER_BY_PARAM="SELECT id_order,date_of_application FROM orders  WHERE id_car=? AND id_client=? AND date_of_application=?";
    private static final String UPDATE_ID_CAR_INTO_ORDER="UPDATE orders SET id_car=null WHERE id_order=?";
    private static final String CHECK_ORDER_STATUS_BY_EMAIL="SELECT name_order_status FROM order_status INNER JOIN orders ON orders.status=id_order_status INNER JOIN users ON users.user_id=id_client WHERE e_mail=? ";
    private static final String UPDATE_CAR_INTO_PREORDER ="UPDATE orders SET id_car=? WHERE id_order=?";
    private static final String MAKE_ORDER_FROM_PRE_ORDER="UPDATE orders  SET status=?,car_issue_date=?  WHERE id_order=?";
//    private static final String SET_RENTAL_START_TIME="INSERT INTO orders ()"
    private static OrderDaoImpl instance=new OrderDaoImpl();

    public OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        return instance;
    }


    @Override
    public boolean insert(Order order) throws DaoException {

        return false;
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        return false;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        return true;
    }
    @Override
    public boolean findIdOrderByParam(Order order) throws DaoException{
        int idOrder;
        boolean result;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(GET_ID_ORDER_BY_PARAM)){
            logger.warn(" id order is {}",order.getIdOrder());
            statement.setInt(1,order.getIdCar());
            statement.setInt(2,order.getIdUser());
            statement.setDate(3,order.getApplicationTime());
            logger.warn("Application statement is {}",order.getApplicationTime());
            logger.warn("{}",statement);
            try(ResultSet resultSet= statement.executeQuery()){
                if (resultSet.next()){
                    logger.warn("Get ID with current parameters, ID is {}, date_of_application is {}",resultSet.getInt(AttributeName.ID_ORDER),resultSet.getTimestamp(AttributeName.DATE_OF_APPLICATION));
                    idOrder=resultSet.getInt(AttributeName.ID_ORDER);
                    result=true;
                } else {
                    idOrder=0;
                    logger.warn("Can not find ID current order");
                    result=false;
                }
            }
        } catch (SQLException e){
            logger.error("Failed to get id order by param");
            throw new DaoException(e);
        }
        order.setIdOrder(idOrder);
        logger.warn("Order is {}, result is {}",order,result);
        return result;
    }

    @Override
    public Optional<Order> findOrderByEmail(String email) throws DaoException {
        Optional<Order>optionalOrder;
        try(Connection connection= ConnectionPool.getInstance().getConnection();
            PreparedStatement statement= connection.prepareStatement(CHECK_ORDER_STATUS_BY_EMAIL)){
            statement.setString(1,email);
            try(ResultSet resultSet= statement.executeQuery()) {
                if (resultSet.next()){
                    Order order=new Order();
                    logger.warn("hello {}",resultSet.getString(AttributeName.NAME_ORDER_STATUS));
                    order.setOrderStatus(resultSet.getString(AttributeName.NAME_ORDER_STATUS));
                    logger.warn("bye-bye");
                    optionalOrder=Optional.of(order);
                    logger.warn(" current order {}",order);
                } else {
                    optionalOrder=Optional.empty();
                    logger.warn("Order is not exists in DB");
                }
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
        return optionalOrder;
    }

    @Override
    public boolean addPreOrderWithCreditCard(Order order) throws DaoException {
        Date date =new Date(new java.util.Date().getTime());
        boolean result=false;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
            PreparedStatement statement= connection.prepareStatement(INSERT_PRE_ORDER_WITH_CREDIT_CARD)){
            statement.setString(1,String.valueOf(order.getIdCar()));
            statement.setString(2,String.valueOf(order.getIdUser()));
            order.setApplicationTime(date);
            statement.setString(3,order.getApplicationTime().toString());
            statement.setString(4,String.valueOf(order.getOrderStatus().ordinal())+1);
            statement.setString(5,order.getMethod().toString());
            statement.setString(6,String.valueOf(order.getCard().getId()));
            statement.executeUpdate();
            result=true;
            logger.warn("Add pre_order");
        } catch (SQLException e){
            logger.error("Failed to insert pre-order");
            throw new DaoException(e);
        }
        return result;
    }
    @Override
    public boolean addPreOrderWithCash(Order order) throws DaoException{
        java.sql.Date date =new Date(new java.util.Date().getTime());
        logger.warn("Time added pre-order is {}",date);
        boolean result=false;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
            PreparedStatement statement= connection.prepareStatement(INSERT_PRE_ORDER_WITH_CASH)){
            statement.setInt(1,order.getIdCar());
            statement.setInt(2,order.getIdUser());
            statement.setDate(3,date);
            order.setApplicationTime(date);
            statement.setInt(4,order.getOrderStatus().ordinal()+1);
            statement.setString(5,order.getMethod().toString());
            statement.executeUpdate();
            result=true;
            logger.warn("Add pre_order");
        } catch (SQLException e){
            logger.error("Failed to insert pre-order");
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean addCreditCard(CreditCard card) throws DaoException {
        logger.error("In addCreditCard service method");
        boolean result;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(INSERT_CREDIT_CARD)){
            logger.warn("You are before statment setting paremeters");
            statement.setString(1, card.getNumber());
            statement.setString(2, card.getDateExpiry());
            statement.setString(3, card.getCardHolderName());
            statement.setString(4, card.getCvc());
            statement.setString(5, card.getCurrentSum().toString());
            logger.warn("You are before execute methode {}",card);
            statement.execute();
            logger.warn("Added a credit card");
            result=true;
        }catch (SQLException e){
            logger.error("Failed to add credit card");
            throw new DaoException(e);
        }
        return result;
    }
    @Override
    public Optional<CreditCard> getIdCardByNumberAndName(HashMap<String, String> cardData) throws DaoException{
        Optional<CreditCard> optionalCard;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(SELECT_CREDIT_CARD_BY_NUMBER_AND_NAME)){
            statement.setString(1,cardData.get(AttributeName.CARD_NUMBER));
            statement.setString(2,cardData.get(AttributeName.CARD_OWNER_NAME));
            try(ResultSet resultSet= statement.executeQuery()){
                if (resultSet.next()){
                    CreditCard card=new CreditCard();
                    card.setId(Integer.parseInt(resultSet.getString(AttributeName.ID_CREDIT_CARD)));
                    optionalCard=Optional.of(card);
                    logger.warn("Credit card is exist");
                } else {
                    optionalCard=Optional.empty();
                    logger.warn("Credit card is not exist ");
                }
            }
        }catch (SQLException e){
            logger.error("Failed to select credit card from DB");
            throw new DaoException(e);
        }
        return optionalCard;
    }
    @Override
    public Optional<CreditCard> findCreditCardById(String idCreditCard)throws DaoException{
        Optional<CreditCard>creditCardOptional;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(SELECT_CREDIT_CARD_BY_ID)){
            statement.setString(1,idCreditCard);
            try(ResultSet resultSet= statement.executeQuery()){
                if (resultSet.next()){
                    CreditCard card=new CreditCard();
                    card.setId(resultSet.getInt(AttributeName.ID_CREDIT_CARD));
                    card.setCurrentSum(resultSet.getString(AttributeName.CURRENT_SUM_CREDIT_CARD));
                    card.setCardHolderName(resultSet.getString(AttributeName.CARD_OWNER_NAME));
                    card.setNumber(resultSet.getString(AttributeName.CARD_NUMBER));
                    card.setDateExpiry(resultSet.getString(AttributeName.CARD_EXPIRY_DATE));
                    logger.warn("Credit card with current id is exist");
                    creditCardOptional=Optional.of(card);
                } else {
                    logger.warn("Credit card with current id is not exist");
                    creditCardOptional=Optional.empty();
                }
            }
        }catch (SQLException e){
            logger.error("Failed to find credit card");
            throw new DaoException(e);
        }
        return creditCardOptional;
    }
    @Override
    public boolean addIdCreditCardToOrder(CreditCard card, Order order) throws DaoException{
        boolean result=false;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(INSERT_CREDIT_CARD_TO_ORDER)){
            logger.error("Id card is {} , Id order is {}",card.getId(),order.getId() );
            statement.setInt(1,card.getId());
            statement.setInt(2,order.getId());
            result=statement.execute();
            if (result) {
                logger.warn("Add credit card to order");
            } else {
                logger.warn("Do not add credit card to order");
            }
        } catch (SQLException e){
            logger.error("Failed to add credit card to orders");
        }
        return result;
    }
    @Override
    public boolean deletePaymentMethodFromOrder(Order order) throws DaoException{
        boolean result;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement= connection.prepareStatement(DELETE_PAYMENT_METHOD_IN_ORDER)){
            statement.setInt(1,order.getId());
            logger.error("Into OrderDao, id Order is {} , result execute is {}",order.getId(),statement.executeUpdate());
            result=statement.executeUpdate()==1;
        } catch (SQLException e){
            logger.error("Failed to delete payment method from orders");
            throw new DaoException(e);
        }
        return result;
    }
    @Override
    public boolean deleteCreditCardFromOrder(Order order) throws DaoException{
        boolean result;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement= connection.prepareStatement(DELETE_CARD_FROM_ORDER)){
            statement.setInt(1,order.getId());
            result=statement.executeUpdate()==1;
        } catch (SQLException e){
            logger.error("Failed to delete credit card from orders");
            throw new DaoException(e);
        }
        return result;
    }
    public boolean insertIntoOrderPaymentMethod(Order order) throws DaoException{
        boolean result;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(INSERT_PAYMENT_METHOD_INTO_ORDER)){
            statement.setString(1,order.getMethod().toString());
            statement.setString(2,String.valueOf(order.getId()));
            result= statement.execute();
        }catch (SQLException e ){
            logger.error("Failed to add payment method to order");
            throw new DaoException(e);
        }
        return result;
    }
    public boolean deletePreorder(Order order) throws DaoException{
        boolean result;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(DELETE_PRE_ORDER)){
            logger.warn("id order is {}",order.getIdOrder());
            statement.setInt(1,order.getIdOrder());
            result=statement.executeUpdate()==1;
        } catch (SQLException e){
            logger.warn("Failed to delete pre-order");
            throw new DaoException(e);
        }
        return result;
    }
    public boolean deleteIdCarIntoOrder(Order order) throws DaoException{
        boolean result;
        logger.warn("id order is {}",order.getIdOrder());
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement= connection.prepareStatement(UPDATE_ID_CAR_INTO_ORDER)){
            if (findIdOrderByParam(order)) {
                logger.warn("order is {}",order);
                statement.setInt(1, order.getIdOrder());
                result= statement.executeUpdate()==1;
                logger.warn("Result statment is {}",result);
            } else {
                logger.warn("can not find order");
                result=false;
            }
        }catch (SQLException e){
            logger.error("Failed to update id_car into order");
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean insertCarIntoPreorder(Car car,Order order) throws DaoException {
        boolean result;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement= connection.prepareStatement(UPDATE_CAR_INTO_PREORDER)){
            statement.setInt(1,car.getId());
            statement.setInt(2,order.getIdOrder());
            result= statement.executeUpdate()==1;
            if (result){
                logger.warn("Added car into pre-order");
            } else {
                logger.warn("Didn't add car into pre-order");
            }
        }catch (SQLException e){
            logger.error("Failed to add car to pre-order");
            throw new DaoException(e);
        }
        return result;
    }
    @Override
    public boolean makeOrderFromPreorder(Order order) throws DaoException {
        java.sql.Date date =new Date(new java.util.Date().getTime());
        order.setOrderStatus(OrderStatus.BEING_EXECUTED.toString());
        boolean result;
        try(Connection connection=ConnectionPool.getInstance().getConnection();
        PreparedStatement statement=connection.prepareStatement(MAKE_ORDER_FROM_PRE_ORDER)){
            statement.setInt(1,order.getOrderStatus().ordinal()+1);
            statement.setDate(2,date);
            order.setRentalStartTime(date);
            statement.setInt(3,order.getIdOrder());
            result=statement.executeUpdate()==1;
            if (result){
                logger.warn("Order has made");
            } else {
                logger.warn("Order hasn't maked");
            }
        }catch (SQLException e){
            logger.error("Failed to make order from preorder");
            throw new DaoException(e);
        }
        return result;
    }
}
