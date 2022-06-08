package acctmanager;

import java.text.DecimalFormat;

/**
This class represents common data and methods of all Accounts.
Every other type of accounts share the data fields and methods in this class.
@author Dharma Wijesinghe, Min Sun You
*/
public abstract class Account { //same person can hold different types of accounts

    protected Profile holder;
    protected boolean closed;
    protected double balance;
    public static final int GROUPING_SIZE = 3;

    /**
    Helps initialize the data fields for subclasses.
    @param holder the Profile of the account.
    @param closed the boolean value representing if its closed or open
    @param balance the initial balance
    */
    public Account(Profile holder, boolean closed, double balance) {
        this.holder = holder;
        this.closed = closed;
        this.balance = balance;
    }
    
    /**
    Determines whether 2 accounts have the same profile.
    @param obj the object to compare with.
    @return true if accounts are equal, false otherwise.
    */
    @Override
    public boolean equals(Object obj) { //What is purpose of this if we're going to override it in subclass?
        if(obj instanceof Account) {
            Account account = (Account) obj;
            return this.holder.equals(account.holder);
        }
        return false;
    }
    
    /**
    Returns string representation of an account.
    @return String the string representation of this account.
    */
    @Override
    public String toString() {
        DecimalFormat formatObj = new DecimalFormat("###,##0.00");
        formatObj.setGroupingUsed(true);
        formatObj.setGroupingSize(GROUPING_SIZE);
        return this.holder.toString() + "::Balance $" + formatObj.format(balance);
    }    
    
    /**
    Subtracts the balance with given amount.
    @param amount the amount to subtract
    */
    public void withdraw(double amount) {
        if((balance - amount) > 0) {
            balance -= amount;
        }
    }
    
    /**
    Adds amount to balance.
    @param amount the amount to add.
    */
    public void deposit(double amount) {
        this.balance += amount;       
    }
    
    /**
    Denotes that this method must be implemented in subclasses.
    Calculates monthly interest from annual interest rate.
    @return double the monthly interest.
    */
    public abstract double monthlyInterest(); //return the monthly interest
    
    /**
    Denotes this method must return the fee for an account and implemented.
    Returns the fee of a particular account.
    @return double the monthly fee.
    */
    public abstract double fee(); //return the monthly fee
    
    /**
    Denotes that this method must return the type of an account and implemented.
    @return String the type of account.
    */
    public abstract String getType(); //return the account type (class name)
    
    /**
    Denotes that this method must be implemented in subclasses
    Updates the balance with fees and monthly interest.
    */
    public abstract void updateBalance();
}