package com.epam.cs.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

public class Order extends AbstractEntity{
    private int idOrder;
    private int idCar;
    private int idUser;
    private java.sql.Date applicationTime;
    private java.sql.Date rentalStartTime;
    private java.sql.Date rentalEndTime;
    private ConditionCarAfterReturn conditionCarAfterReturn;
    private long orderMileage;
    private OrderStatus orderStatus;
    private PaymentMethod method;
    private CreditCard card;

    public Order() {
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public java.sql.Date getApplicationTime() {
        return applicationTime;
    }

    public void  setApplicationTime(Date date) {
        this.applicationTime =java.sql.Date.valueOf(String.valueOf(date));
    }

    public java.sql.Date getRentalStartTime() {
        return rentalStartTime;
    }

    public void setRentalStartTime(Date date) {

        this.rentalStartTime = java.sql.Date.valueOf(String.valueOf(date));
    }

    public java.sql.Date getRentalEndTime() {
        return rentalEndTime;
    }

    public void setRentalEndTime(Date date) {

        this.rentalEndTime = java.sql.Date.valueOf(String.valueOf(date));
    }

    public ConditionCarAfterReturn getConditionCarAfterReturn() {
        return conditionCarAfterReturn;
    }

    public void setConditionCarAfterReturn(ConditionCarAfterReturn conditionCarAfterReturn) {
        this.conditionCarAfterReturn = conditionCarAfterReturn;
    }

    public long getOrderMileage() {
        return orderMileage;
    }

    public void setOrderMileage(long orderMileage) {
        this.orderMileage = orderMileage;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus){
        this.orderStatus=OrderStatus.valueOf(orderStatus.toUpperCase());
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = PaymentMethod.valueOf(method.toUpperCase());
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return idOrder == order.idOrder && idCar == order.idCar && idUser == order.idUser && orderMileage == order.orderMileage && Objects.equals(applicationTime, order.applicationTime) && Objects.equals(rentalStartTime, order.rentalStartTime) && Objects.equals(rentalEndTime, order.rentalEndTime) && conditionCarAfterReturn == order.conditionCarAfterReturn && orderStatus == order.orderStatus && method == order.method && Objects.equals(card, order.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOrder, idCar, idUser, applicationTime, rentalStartTime, rentalEndTime, conditionCarAfterReturn, orderMileage, orderStatus, method, card);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("idOrder=" + idOrder)
                .add("idCar=" + idCar)
                .add("idUser=" + idUser)
                .add("applicationTime=" + applicationTime)
                .add("rentalStartTime=" + rentalStartTime)
                .add("rentalEndTime=" + rentalEndTime)
                .add("conditionCarAfterReturn=" + conditionCarAfterReturn)
                .add("orderMileage=" + orderMileage)
                .add("orderStatus=" + orderStatus)
                .add("method=" + method)
                .add("card=" + card)
                .toString();
    }
}
