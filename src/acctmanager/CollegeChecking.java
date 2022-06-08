package acctmanager;

import java.text.DecimalFormat;

/**
This represents a college checking account.
Requires an input of a college campus code representing the different campuses.
@author Dharma Wijesinghe, Min Sun You
*/
public class CollegeChecking extends Checking {
    
    private static final double MONTHLY_FEE = 0;
    private static final double ANNUAL_INTEREST_RATE_PERCENT = 0.25;
    private static final int TEMPORARY_CAMPUS_CODE = -1;


    private int campusCode;
    
    /**
    Creates a temporary college checking account.
    Used for withdrawing and depositing.
    @param holder the Profile of the account.
    @param amount the amount to deposit or withdraw
    */
    public CollegeChecking(Profile holder, double amount) {//used to create temporary account when withdrawing/depositing
        super(holder, false, amount);
        campusCode = TEMPORARY_CAMPUS_CODE;
    }
    
    /**
    Creates a college checking account to open
    @param holder the profile of the account
    @param closed the boolean value whether it is closed or not
    @param balance the initial balance
    @param campusCode the campus code of this account.
    */
    public CollegeChecking(Profile holder, boolean closed, double balance, int campusCode) {//includes specific data/operations to college checking account
        super(holder, closed, balance);
        this.campusCode = campusCode;
    }
    
    /**
    Returns the campus code of this account.
    @return int the corresponding campus code.
    */
    public int getCampusCd() {
        return campusCode;
    }
    
    /**
    Assigns campus code to the parameter value
    @param code the campus code number to assign to.
    */
    public void setCampusCode(int code) {
        campusCode = code;
    }
    
    /**
    Returns a string representation of college checking accounts.
    @return String the string representation of college checking accounts.
    */
    @Override
    public String toString() {
        DecimalFormat formatObj = new DecimalFormat("###,##0.00");
        formatObj.setGroupingUsed(true);
        formatObj.setGroupingSize(GROUPING_SIZE);
        if(closed) {
            return "College Checking::" + holder.toString() + "::Balance $" + formatObj.format(balance) + "::CLOSED::" + getCampusCode();
        }
        return "College Checking::" + holder.toString() + "::Balance $" + formatObj.format(balance) + "::" + getCampusCode();
    }
    
    /**
    Determines if two accounts are both checking and held by the same person.
    @param obj the object to compare to.
    @return true if accounts are same, false otherwise.
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
    Returns the corresponding string value of campus code.
    @return String the string value of what the campus code is.
    */
    public String getCampusCode() {
        switch (this.campusCode) {
        case 0:
            return "NEW_BRUNSWICK";
        case 1:
            return "NEWARK";
        case 2:
            return "CAMDEN";
        default:
            return null;
        }
    }
    
    /**
    Calculates the monthly interest of college checking.
    @return double the monthly interest of college checking accounts.
    */
    @Override
    public double monthlyInterest() {//return the monthly interest
        double annualInterestRate = ANNUAL_INTEREST_RATE_PERCENT / TOTAL_PERCENTAGE;
        double monthlyInterestRate = annualInterestRate / ANNUAL_DURATION;
        return balance * monthlyInterestRate;
    }
    
    /**
    Returns the fee of all college checking accounts.
    @return double the fee.
    */
    @Override
    public double fee() {//return the monthly fee
        return MONTHLY_FEE;
    }
    
    /**
    Returns the type of college checking accounts.
    @return String the type of account this is.
    */
    @Override
    public String getType() {//return the account type (class name)
        return "CollegeChecking";
    }
}