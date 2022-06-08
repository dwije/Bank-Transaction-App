package acctmanager;

/**
This represents a savings account
A savings account can be loyal or nonloyal.
@author Dharma Wijesinghe, Min Sun You
*/
public class Savings extends Account { //includes specific data/operations to savings account
    
    private static final double MONTHLY_FEE = 6.0;
    private static final double WAIVED_THRESHOLD = 300.0;
    private static final double ANNUAL_INTEREST_RATE_NON_LOYAL = 0.3;
    private static final double ANNUAL_INTEREST_RATE_LOYAL = 0.45;
    private static final double TOTAL_PERCENTAGE = 100;
    public static final double ANNUAL_DURATION = 12;
    public static final int WAIVED_FEE = 0;
    public static final int LOYAL_TRUE = 1;

    protected boolean loyal;
    
    /**
    Creates a temporary savings account for withdrawing or depositing.
    @param holder the profile of this account.
    @param amount the amount to withdraw or deposit.
    */
    public Savings(Profile holder, double amount) { //used to create temporary account when withdrawing/depositing
        super(holder, false, amount);
        this.loyal = false;
    }
    
    /**
    Creates a savings account to open.
    @param holder the profile of the account.
    @param closed boolean value whether it is closed or not
    @param balance the intial balance
    @param loyalCode value representing if it is loyal or not
    */
    public Savings(Profile holder, boolean closed, double balance, int loyalCode) {
        super(holder, closed, balance);
        if(loyalCode == LOYAL_TRUE) {
            this.loyal = true;
        }
        else {
            this.loyal = false;
        }
    }
    
    /**
    Gives a string representation of this Savings object
    Denotes if it is closed or loyal.
    @return String the string representation.
    */
    @Override
    public String toString() {
        if(closed) {
            if(loyal) {
                return "Savings::" + super.toString() + "::Loyal::CLOSED";
            }
            else {
                return "Savings::" + super.toString() + "::CLOSED";
            }
        }
        else {
            if(loyal) {
                return "Savings::" + super.toString() + "::Loyal";
            }
            else {
                return "Savings::" + super.toString();
            }
        }
    }
    
    /**
    Determines whether two savings accounts are equal.
    @param obj the object to compare to.
    @return true if two accounts are both savings and owned by same person, false otherwise.
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
    Changes the loyal status.
    @param changingValue the loyal status to change to.
    */
    public void changeLoyalty(boolean changingValue) {
        this.loyal = changingValue;
    }
    
    /**
    Calculates the monthly interest of savings accounts.
    @return double the monthly interest.
    */
    public double monthlyInterest() { //return the monthly interest
        double annualInterestRate;
        if(loyal) {
            annualInterestRate = ANNUAL_INTEREST_RATE_LOYAL / TOTAL_PERCENTAGE; 
        }
        else {
            annualInterestRate = ANNUAL_INTEREST_RATE_NON_LOYAL / TOTAL_PERCENTAGE;
        }
        double monthlyInterestRate = annualInterestRate / ANNUAL_DURATION;
        return balance * monthlyInterestRate;
    }
    
    /**   
    Returns the monthly fee of savings accounts.
    @return double the monthly fee.
    */
    public double fee() { //return the monthly fee
        if(balance >= WAIVED_THRESHOLD) {
            return WAIVED_FEE;
        }
        return MONTHLY_FEE;
    }
    
    /**
    Returns the type of this account.
    @return String the account type.
    */
    public String getType() { //return the account type (class name)
        return "Savings";
    }
    
    /**
    Updates the balance.
    Adds monthly interest and subtracts the monthly fee.
    */
    public void updateBalance() {
        this.balance += this.monthlyInterest();
        this.balance -= this.fee();
    }
}