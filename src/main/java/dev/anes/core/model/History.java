package dev.anes.core.model;

import java.time.LocalDate;

import dev.anes.server.HistoryDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class History {
    private IntegerProperty historyID;
    private ObjectProperty<Debtor> debtorID;
 
    private IntegerProperty countloan;
    private ObjectProperty<LocalDate> loandate;
    private LongProperty loanAmount;
    private IntegerProperty monthtopay;
    private LongProperty penalty;
    private LongProperty interest;
    public History(Debtor debtorID,int countloan,
    LocalDate loandate,long loanAmount,int monthtopay ,long penalty,long interest){
        this(HistoryDAO.getLastID()+1,debtorID,countloan,loandate,loanAmount,monthtopay,penalty,interest);
    }

    public History(int historyID, Debtor debtorID,
            int countloan, LocalDate loandate, long loanAmount, int monthtopay,long penalty,long interest) {
        this.historyID = new SimpleIntegerProperty(historyID);
        this.debtorID = new SimpleObjectProperty<>(debtorID);
        this.countloan = new SimpleIntegerProperty(countloan);
        this.loandate = new SimpleObjectProperty<>(loandate);
        this.loanAmount = new SimpleLongProperty(loanAmount);
        this.monthtopay = new SimpleIntegerProperty(monthtopay);
        this.penalty= new SimpleLongProperty(penalty);
        this.interest=new SimpleLongProperty(interest);

    }
    
    //getters
    public int getHistoryID(){
        return historyID.get();
    }
    public Debtor getDebtorID(){
        return debtorID.get();
    }
   
    public int getCountloan(){
        return countloan.get();
    }
    public LocalDate getLoanDate(){
        return loandate.get();
    }
    public long getLoanAmount(){
        return loanAmount.get();
    }
    public int getMonthtoPay(){
        return monthtopay.get();
    }
    public long getPenalty(){
        return penalty.get();
    }
    public long getInterest(){
        return interest.get();
    }
    //setter
    public void setHistoryID(int historyID){
        this.historyID.set(historyID);
    }
    public void setDebtorID(Debtor debtorID){
        this.debtorID.set(debtorID);
    }
    public void setCountLoan(int countloan){
        this.countloan.set(countloan);
    }
    public void setLoanDate(LocalDate loanDate){
        this.loandate.set(loanDate);
    }
    public void setLoanAmount(long loanamount){
        this.loanAmount.set(loanamount);
    }
    public void setMonthtoPay(int monthtopay){
        this.monthtopay.set(monthtopay);
    }
    public void setPenalty(long penalty){
        this.penalty.set(penalty);
    }
    public void setInterest(long interest){
        this.interest.set(interest);
    }
    //property
    public IntegerProperty historyIDProperty(){
        return historyID;
    }
    public ObjectProperty<Debtor> debtorIDProperty(){
        return debtorID;
    }
  
    public IntegerProperty countloanProperty(){
        return countloan;
    }
    public ObjectProperty<LocalDate>laondateProperty(){
        return loandate;
    }
    public LongProperty loanamountProperty(){
        return loanAmount;
    }
    public IntegerProperty monthtopayProperty(){
        return monthtopay;
    }
    public LongProperty interestProperty(){
        return interest;
    }
    public LongProperty penaltyProperty(){
        return penalty;
    }

}
