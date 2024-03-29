package com.epam.cs.entity;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

public class Car extends AbstractEntity{
    private String color;
    private String model;
    private String bodyType;
    private int currentMileage;
    private double engineVolume;
    private String registrationNumber;
    private String yearOfIssue;
    private BigDecimal costPerMinute;
    private Boolean currentStatus;
    private Boolean condition;
    private  String photo;
    private ParkingPlace parkingPlace;


    public Car(){
        this.condition=true;
    }

    public Car(String color, String model, String bodyType, int currentMileage,
               String registrationNumber, String yearOfIssue, BigDecimal costPerMinute,
               Boolean currentStatus, double engineVolume,String photo,String  parkingPlace) {
        this.color = color;
        this.model = model;
        this.bodyType = bodyType;
        this.currentMileage = currentMileage;
        this.engineVolume=engineVolume;
        this.registrationNumber = registrationNumber;
        this.yearOfIssue = yearOfIssue;
        this.costPerMinute = costPerMinute;
        this.currentStatus = currentStatus;
        this.condition = true;
        this.photo=photo;
        this.parkingPlace=ParkingPlace.valueOf(parkingPlace);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setColor(java.lang.String color) {
        this.color = color;
    }

    public void setModel(java.lang.String model) {
        this.model = model;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public void setCurrentMileage(int currentMileage) {
        this.currentMileage = currentMileage;
    }

    public void setRegistrationNumber(java.lang.String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setYearOfIssue(java.lang.String yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public void setCostPerMinute(BigDecimal costPerMinute) {
        this.costPerMinute = costPerMinute;
    }

    public void setCurrentStatus(Boolean currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setCondition(Boolean condition) {
        this.condition = condition;
    }

    public java.lang.String getColor() {
        return color;
    }

    public java.lang.String getModel() {
        return model;
    }

    public String getBodyType() {
        return bodyType;
    }

    public int getCurrentMileage() {
        return currentMileage;
    }

    public java.lang.String getRegistrationNumber() {
        return registrationNumber;
    }

    public java.lang.String getYearOfIssue() {
        return yearOfIssue;
    }

    public BigDecimal getCostPerMinute() {
        return costPerMinute;
    }

    public Boolean getCurrentStatus() {
        return currentStatus;
    }

    public Boolean getCondition() {
        return condition;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public ParkingPlace getParkingPlace() {
        return parkingPlace;
    }

    public void setParkingPlace(String parkingPlace) {
        this.parkingPlace = ParkingPlace.valueOf(parkingPlace);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return currentMileage == car.currentMileage && Double.compare(car.engineVolume, engineVolume) == 0 && Objects.equals(color, car.color) && Objects.equals(model, car.model) && Objects.equals(bodyType, car.bodyType) && Objects.equals(registrationNumber, car.registrationNumber) && Objects.equals(yearOfIssue, car.yearOfIssue) && Objects.equals(costPerMinute, car.costPerMinute) && Objects.equals(currentStatus, car.currentStatus) && Objects.equals(condition, car.condition) && Objects.equals(photo, car.photo) && parkingPlace == car.parkingPlace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color, model, bodyType, currentMileage, engineVolume, registrationNumber, yearOfIssue, costPerMinute, currentStatus, condition, photo, parkingPlace);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("color='" + color + "'")
                .add("model='" + model + "'")
                .add("bodyType='" + bodyType + "'")
                .add("currentMileage=" + currentMileage)
                .add("engineVolume=" + engineVolume)
                .add("registrationNumber='" + registrationNumber + "'")
                .add("yearOfIssue='" + yearOfIssue + "'")
                .add("costPerMinute=" + costPerMinute)
                .add("currentStatus=" + currentStatus)
                .add("condition=" + condition)
                .add("photo='" + photo + "'")
                .add("parkingPlace=" + parkingPlace)
                .toString();
    }
}
