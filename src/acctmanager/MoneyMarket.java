package acctmanager;

import java.text.DecimalFormat;

/**
This class represents Money Market class which is a type of Savings.
Every Money Market account keeps track of number of withdrawals
@author Dharma Wijesinghe, Min Sun You
*/
public class MoneyMarket extends Savings { //includes specific data/operations to a money market account

    private static final double MONTHLY_FEE = 10.0;
    private static final double WAIVED_THRESHOLD = 2500.0;
    private static final double LOYAL_THRESHOLD = 2500.0;
    private static final double ANNUAL_INTEREST_RATE_NON_LOYAL = 0.008;
    private static final double ANNUAL_INTEREST_RATE_LOYAL = 0.0095;
    private static final int PLACE_HOLDER_VALUE = 0;
    private static final int INITIAL_WITHDRAW_VALUE = 0;
    private static final int WITHDRAW_THRESHOLD = 3;
    private static final int INCREMENT = 1;

    private int numberOfWithdrawal;
    
    /**
    Creates a temporary money market account.
    Used for withdrawing and depositing.
    @param holder the Profile of account.
    @param amount the amount to withdraw or deposit
    */
    public MoneyMarket(Profile holder, double amount) { //used to create temporary account when withdrawing/depositing
        super(holder, false, amount, PLACE_HOLDER_VALUE);
        this.numberOfWithdrawal = PLACE_HOLDER_VALUE;
    }
    
    /**
    Creates a money market account to open.
    @param holder the Profile of account.
    @param closed boolean indicating whether it is closed or not.
    @param balance the initial balance.
    */
    public MoneyMarket(Profile holder, boolean closed, double balance) {
        super(holder, closed, balance, LOYAL_TRUE); //By default it is loyal
        numberOfWithdrawal = INITIAL_WITHDRAW_VALUE;
    }
    
    /**
    Withdraws an amount from balance. 
    Does nothing if amount is greater than balance
    @param amount the amount to withdraw
    */
    @Override
    public void withdraw(double amount) {
        if((this.balance - amount) > 0) {
            this.balance = this.balance - amount;
            numberOfWithdrawal = numberOfWithdrawal + INCREMENT;
        }
        if(balance < LOYAL_THRESHOLD) {
            this.loyal = false;
        }
    }
    
    /**
    Returns how many times an account withdrew.
    @return double the number of withdraws.
    */
    public double getNumberOfWithdrawal() {
        return numberOfWithdrawal;
    }
    
    /**
    Resets the number of withdrawals.
    */
    public void resetNumberOfWithdrawal() {
        this.numberOfWithdrawal = INITIAL_WITHDRAW_VALUE;
    }
    
    /**
    Returns string representation of money market accounts.
    @return String the string value of this account.
    */
    @Override 
    public String toString() {
        DecimalFormat formatObj = new DecimalFormat("0.00");
        formatObj.setGroupingUsed(true);
        formatObj.setGroupingSize(GROUPING_SIZE);
        if(closed) {
            return "Money Market Savings::" + holder.toString() + "::Balance $" + formatObj.format(balance) + "::CLOSED::withdrawl: " + this.numberOfWithdrawal;            
        }
        else {
            if(loyal) {
                return "Money Market Savings::" + holder.toString() + "::Balance $" + formatObj.format(balance) + "::Loyal::withdrawl: " + this.numberOfWithdrawal;
            }
            else {
                return "Money Market Savings::" + holder.toString() + "::Balance $" + formatObj.format(balance) + "::withdrawl: " + this.numberOfWithdrawal;
            }
        }
    }
    
    /**
    Determines whether two money market accounts are equal.
    @param obj the object to compare with.
    @return true calling object and param object are equal, false otherwise.
    */
    @Override
    public boolean equals(Object obj) { //What is purpose of this if we're going to override it in subclass?
        if(obj instanceof Account) {
            Account account = (Account) obj;
            return this.holder.equals(account.holder) && this.getType().equals(account.getType());
        }
        return false;
    }
    
    /**
    Returns monthly interest for this account.
    @return double the monthly interest.
    */
    @Override
    public double monthlyInterest() { //return the monthly interest   POSSIBLY REDUNDANT
        double annualInterestRate;
        double monthlyInterestRate;
        if(balance < LOYAL_THRESHOLD) { //no longer loyal            
            annualInterestRate = ANNUAL_INTEREST_RATE_NON_LOYAL;
            monthlyInterestRate = annualInterestRate / ANNUAL_DURATION;           
        }
        else {
            annualInterestRate = ANNUAL_INTEREST_RATE_LOYAL;
            monthlyInterestRate = annualInterestRate / ANNUAL_DURATION;            
        }
        return this.balance * monthlyInterestRate;
    }
    
    /**
    Returns the fee for this account.
    @return double the monthly fee.
    */
    @Override
    public double fee() {//return the monthly fee
        if(this.balance >= WAIVED_THRESHOLD) {
            if(this.numberOfWithdrawal <= WITHDRAW_THRESHOLD) {
                return WAIVED_FEE;
            }
        }
        return MONTHLY_FEE;
    }
    
    /**
    Returns the type of account.
    @return String the type of account.
    */
    @Override
    public String getType() {//return the account type (class name)
        return "MoneyMarket";
    }
    
    /**
    Updates the balance with subtracted fees and added interest.
    Checks whether balance falls below the loyal threshold.
    */
    @Override 
    public void updateBalance() {
        super.updateBalance();
        if(this.balance < LOYAL_THRESHOLD) {
            this.loyal = false;
        }
    }
}