package dev.anes.core.model;

import java.time.LocalDate;

import dev.anes.server.Loan_InfoDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Loan_Info {
    private IntegerProperty infoID;
    private ObjectProperty<Debtor> debtorID;
    private ObjectProperty<LocalDate> loanDate;
    private LongProperty loanAmount;
    private IntegerProperty months_to_pay;
    private LongProperty interest;
    private LongProperty penalty;
    private IntegerProperty count; 

      public Loan_Info(Debtor debtorID, LocalDate loanDate, long loanAmount, int months_to_pay,
      long interest, long penalty,int count) {
        this(Loan_InfoDAO.getLastID() + 1, debtorID, loanDate, loanAmount, months_to_pay, interest, penalty,count);
    }
    public Loan_Info(int infoID,
            Debtor debtorID,
            LocalDate loanDate,
            long loanAmount,
            int months_to_pay,
            long interest,
            long penalty,
            int count) {
        this.infoID = new SimpleIntegerProperty(infoID);
        this.debtorID = new SimpleObjectProperty<>(debtorID);
        this.loanDate = new SimpleObjectProperty<>(loanDate);
        this.loanAmount = new SimpleLongProperty(loanAmount);
        this.months_to_pay = new SimpleIntegerProperty(months_to_pay);
        this.interest = new SimpleLongProperty(interest);
        this.penalty = new SimpleLongProperty(penalty);
        this.count=new SimpleIntegerProperty(count);

    }

    // getters
    public int getInfoID() {
        return infoID.get();
    }

    public Debtor getDebtorID() {
        return debtorID.get();
    }

    public LocalDate getLoadDate() {
        return loanDate.get();
    }


    public long getLoanAmount() {
        return loanAmount.get();
    }

    public int getMonths_to_pay() {
        return months_to_pay.get();
    }

    public long getInterest() {
        return interest.get();
    }

    public long getPenalty() {
        return penalty.get();
    }
    public int getCount(){
        return count.get();
    }
    // setters
    public void setInfoID(int infoID) {
        this.infoID.set(infoID);
    }

    public void setDebtorID(Debtor debtorID) {
        this.debtorID.set(debtorID);
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate.set(loanDate);
    }

    public void setLoanAmount(long loanAmount) {
        this.loanAmount.set(loanAmount);
    }

    public void setMonths_to_pay(int months_to_pay) {
        this.months_to_pay.set(months_to_pay);
    }

    public void setInterest(long interest) {
        this.interest.set(interest);
    }

    public void setPenalty(long penalty) {
        this.penalty.set(penalty);
    }
    public void setCount(int count){
        this.count.set(count);
    }

    // properties

    public IntegerProperty infoIDProperty() {
        return infoID;
    }

    public ObjectProperty<Debtor> debtorIDProperty() {
        return debtorID;
    }

    public ObjectProperty<LocalDate> loanDateProperty() {
        return loanDate;
    }

    public LongProperty loanAmountProperty() {
        return loanAmount;
    }

    public IntegerProperty months_to_payProperty() {
        return months_to_pay;
    }

    public LongProperty interestProperty() {
        return interest;
    }

    public LongProperty penaltyProperty() {
        return penalty;
    }
    public IntegerProperty countProperty(){
        return count;
    } 

}
