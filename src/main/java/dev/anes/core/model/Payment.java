package dev.anes.core.model;

import java.time.LocalDate;

import dev.anes.server.PaymentDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Payment {
    private IntegerProperty payment;
    private ObjectProperty<Debtor> debtor;
    private ObjectProperty<LocalDate>loandate;
    private LongProperty pay_amount;
    private ObjectProperty<LocalDate> payment_date;
    private IntegerProperty timely;
    public Payment(Debtor debtor,
            LocalDate loandate,
            long pay_amount,
            LocalDate payment_date,
            int timely
    ) {
        this(PaymentDAO.getLastID()+1,debtor,loandate,pay_amount,payment_date,timely);
    }

    public Payment(int payment,
            Debtor debtor,
            LocalDate loandate,
            long pay_amount,
            LocalDate payment_date,
            int timely
    ) {
        this.payment = new SimpleIntegerProperty(payment);
        this.debtor = new SimpleObjectProperty<>(debtor);
        this.loandate = new SimpleObjectProperty<>(loandate);
        this.pay_amount = new SimpleLongProperty(pay_amount);
        this.payment_date = new SimpleObjectProperty<>(payment_date);
        this.timely = new SimpleIntegerProperty(timely);
    }

    // getters
    public int getPayment() {
        return payment.get();
    }

    public Debtor getDebtor() {
        return debtor.get();
    }
    public LocalDate getLoanDate(){
        return loandate.get();
    }
    public long getPay_amount() {
        return pay_amount.get();
    }

    public LocalDate getPaymnet_date() {
        return payment_date.get();
    }
    public int getTimely(){
        return timely.get();
    }
    // setters
    public void setPayment(int payment) {
        this.payment.set(payment);
    }

    public void setDebtor(Debtor debtor) {
        this.debtor.set(debtor);
    }
    public void setLoanDate(LocalDate loanDate){
        this.loandate.set(loanDate);
    }

    public void setPay_amount(long pay_amount) {
        this.pay_amount.set(pay_amount);
    }

    public void setPayment_date(LocalDate payment_date) {
        this.payment_date.set(payment_date);
    }
    public void setTimely(int timely){
        this.timely.set(timely);
    }
    // properties
    public IntegerProperty paymentProperty() {
        return payment;
    }

    public ObjectProperty<Debtor> debtorProperty() {
        return debtor;
    }
    public ObjectProperty<LocalDate> loanDateProperty(){
        return loandate;
    }
    public LongProperty pay_amountProperty() {
        return pay_amount;
    }

    public ObjectProperty<LocalDate> payment_dateProperty() {
        return payment_date;
    }
    public IntegerProperty timelyProperty(){
        return timely;
    }

}
