package com.epam.cs.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;
import java.util.StringJoiner;

public class CreditCard extends AbstractEntity {
    private String number;
    private String CardHolderName;
    private String cvc;
    private BigDecimal currentSum;

    private String DateExpiry;

    public String getNumber() {
        return number;
    }

    public CreditCard() {

    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCardHolderName() {
        return CardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        CardHolderName = cardHolderName;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public BigDecimal getCurrentSum() {
        return currentSum;
    }

    public void setCurrentSum() {
        this.currentSum=new BigDecimal(generateSum(100,3000));
    }
    public void setCurrentSum(String sum) {
        this.currentSum=new BigDecimal(sum);
    }

    public String getDateExpiry() {
        return DateExpiry;
    }

    public void setDateExpiry(String dateExpiry) {
        DateExpiry = dateExpiry;
    }


    private int generateSum(int min, int max){
        Random random=new Random();
        return random.nextInt(max-min)+min;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(number, that.number) && Objects.equals(CardHolderName, that.CardHolderName) && Objects.equals(cvc, that.cvc) && Objects.equals(currentSum, that.currentSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, CardHolderName, cvc, currentSum);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreditCard.class.getSimpleName() + "[", "]")
                .add("number='" + number + "'")
                .add("CardHolderName='" + CardHolderName + "'")
                .add("cvc='" + cvc + "'")
                .add("currentSum=" + currentSum)
                .toString();
    }
}
