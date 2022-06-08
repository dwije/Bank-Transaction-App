package acctmanager;

/**
This class represents a type of account used for checking.
It inherits all the data fields and methods from Account superclass
@author Dharma Wijesinghe, Min Sun You
*/
public class Checking extends Account { //includes specific data/operations to regular checking account
    
    private static final double MONTHLY_FEE = 25;
    private static final double WAIVED_THRESHOLD = 1000.0;
    private static final double ANNUAL_INTEREST_RATE_PERCENT = 0.1;
    public static final double TOTAL_PERCENTAGE = 100;
    public static final double ANNUAL_DURATION = 12;
    
    /**
    Creates a temporary Checking account used for withdrawing and depositing.
    @param holder the profile of this temporary account.
    @param amount the amount to deposit or withdraw.
    */
    public Checking(Profile holder, double amount) { //used to create temporary account when withdrawing/depositing
        super(holder, false, amount);
    }
    
    /**
    Creates a checking account that could be open.
    @param holder the profile of this checking account.
    @param closed the boolean value whether it is closed or open.
    @param balance the balance of the account to open.
    */
    public Checking(Profile holder, boolean closed, double balance) { //each account holder can only have one checking account
        super(holder, closed, balance);
    }
    
    /**
    Returns a string representation of this Checking account class.
    @return String the string representation 
    */
    @Override
    public String toString() {
        if(closed) {
            return "Checking::" + super.toString() + "::CLOSED";
        }
        return "Checking::" + super.toString();
    }
    
    /**
    Determines if the calling Checking account is equal to the obj account.
    @param obj the Object to compare with.
    @return true if equal, false otherwise.
    */
    @Override
    public boolean equals(Object obj) { //What is purpose of this if we're going to override it in subclass?
        if(obj instanceof Account) {
            Account account = (Account) obj;
            return super.equals(account) && this.getType().equals(account.getType());
        } 
        return false;
    }
    
    /**
    Calculates the monthly interest for checking accounts.
    @return double the monthly interest
    */
    public double monthlyInterest() {//return the monthly interest
        double annualInterestRate = ANNUAL_INTEREST_RATE_PERCENT / TOTAL_PERCENTAGE;
        double monthlyInterestRate = annualInterestRate / ANNUAL_DURATION;
        return balance * monthlyInterestRate;
    }
    
    /**
    Returns the monthly fee for checking accounts.
    Fees are wavied if balance meets certain threshold.
    @return double the monthly fee calculated.
    */
    public double fee() { //return the monthly fee
        if(balance >= WAIVED_THRESHOLD) {
            return 0;
        }
        return MONTHLY_FEE;
    }
    
    /**
    Returns the type of account for this class.
    @return String the type of this account.
    */
    public String getType() { //return the account type (class name)
        return "Checking";
    }
    
    /**
    Updates the balance of checking account
    Subtracts the fees and adds the monthly interest.
    */
    public void updateBalance() {
        this.balance = this.balance - this.fee();
        this.balance = this.balance + this.monthlyInterest();
    }
}