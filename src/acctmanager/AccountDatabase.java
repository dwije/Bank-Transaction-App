package acctmanager;

import java.text.DecimalFormat;

/**
Holds all the accounts in an array.
Defines operations for each account.
@author Dharma Wijesinghe, Min Sun You
*/
public class AccountDatabase {
    private Account [] accounts;
    private int numAcct;
    private static final int NOT_FOUND = -1;
    private static final int INITIAL_SIZE = 4;
    private static final int INITIAL_VALUE_NUMBACCT = 0;
    private static final int GROWTH_VALUE = 4;
    private static final int GROUPING_SIZE = 3;

    /**
    Instantiates an AccountDatabase.
    Sets array size to 4
    Sets numAcct to 0.
    */
    public AccountDatabase() {
        this.accounts = new Account[INITIAL_SIZE];
        this.numAcct = INITIAL_VALUE_NUMBACCT;
    }
    
    /**
    Gets the number of accounts in database.
    @return numAcct the number of accounts.
    */
    public int sizeOfDatabase() {
        return this.numAcct;
    }
    
    /**
    Attempts to find the account in parameter.
    @param account the Account to find
    @return NOT_FOUND if not found, the index of account otherwise.
    */
    private int find(Account account) { 
        if(accounts[0] == null) {
            return NOT_FOUND;
        }
        for(int i = 0; i < numAcct; i++) {
            if(accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }
    
    /**
    Tries to find an account
    @param account the Account to find
    @return NOT_FOUND if not found, the index of account otherwise.
    */
    public int findAcc(Account account) {
        return find(account);
    }
    
    /**
    Checks whether an account is closed.
    Does nothing if account not found.
    @param account the account to determine if it's closed.
    @return true if account is closed, false otherwise
    */
    public boolean isClosed(Account account) {
        int foundIndex = find(account);
        if(foundIndex != NOT_FOUND) {
            if(accounts[foundIndex].closed == true) {
                return true;
            }
        }
        return false;
    }
    
    /**
    Grows the account database array by size 4
    */
    private void grow() { 
        Account [] copy = new Account[this.accounts.length + GROWTH_VALUE]; 
        for(int i = 0; i < this.accounts.length; i++) {
            copy[i] = this.accounts[i];
        }
        accounts = copy;
    }
    
    /**
    Opens a given account to the database.
    @param account the Account to open.
    @return true if account is opened, false otherwise.
    */
    public boolean open(Account account) {
        if(accounts[accounts.length - 1] != null) {
            //There is no space left in the appointments array
            this.grow();
        }
        int accountIndex = find(account);
        if(accountIndex == NOT_FOUND) {
            for(int i = 0; i < accounts.length; i++) {
                if(accounts[i] == null) {
                    accounts[i] = account;
                    numAcct++;
                    return true;
                }
            }
        } else {
            if(accounts[accountIndex].closed) {
                accounts[accountIndex].closed = false;
                accounts[accountIndex].balance = account.balance;
                if(accounts[accountIndex].getType().equals("CollegeChecking")) {
                    ((CollegeChecking)accounts[accountIndex]).setCampusCode(((CollegeChecking) account).getCampusCd());
                }
                if(accounts[accountIndex].getType().equals("Savings")) {
                    ((Savings)accounts[accountIndex]).loyal = ((Savings) account).loyal;
                }
                if(accounts[accountIndex].getType().equals("MoneyMarket") && account.balance >= 2500) {
                    ((MoneyMarket)accounts[accountIndex]).loyal = true;
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    
    /**
    Closes the account in the database.
    Does nothing if account is not found.
    @param account the account to close.
    @return true if account is closed, false otherwise.
    */
    public boolean close(Account account) {  
        int indexOfAccount = find(account);
        if(indexOfAccount != NOT_FOUND && !accounts[indexOfAccount].closed) {
            accounts[indexOfAccount].closed = true;
            accounts[indexOfAccount].balance = 0;
            if( accounts[indexOfAccount].getType().equals("Savings") ||  accounts[indexOfAccount].getType().equals("MoneyMarket")) {                
                ((Savings) accounts[indexOfAccount]).changeLoyalty(false);
            }
            if( accounts[indexOfAccount].getType().equals("MoneyMarket")) {                
                ((MoneyMarket) accounts[indexOfAccount]).resetNumberOfWithdrawal();
            }
            return true;
        }
        return false;
    }
    
    /**
    Deposits into an account in database.
    Does nothing if account is not found.
    @param account the account to deposit into.
    */
    public void deposit(Account account) { 
        int indexOfAccount = find(account);
        double amount = account.balance;
        if(indexOfAccount != NOT_FOUND) {
            accounts[indexOfAccount].deposit(amount);
        }
    }
    
    /**
    Withdraws from a given account.
    Does nothing if account not found.
    @param account the account to withdraw from.
    @return true if account is withdrawn, false otherwise.
    */
    public boolean withdraw(Account account) { //return false if insufficient fund
        int indexOfAccount = find(account);
        double amount = account.balance;
        if(indexOfAccount != NOT_FOUND) {
            if(accounts[indexOfAccount].balance > amount) {
                accounts[indexOfAccount].withdraw(amount);
                return true;
            }
        }
        return false;
    } 
   
    /**
    Prints all accounts in database.
    */
    public void print() { 
        for(int i = 0; i < accounts.length; i++) {
            if(accounts[i] == null) {
                return;
            }
            System.out.println(accounts[i].toString());
        }
    }
    
    /**
    Prints all accounts by type.
    */
    public void printByAccountType() { 
        //Sorting account types by alphabetical order
        for(int i = 0; i < numAcct - 1; i++) {
            int minIndex = i;
            for(int j = i + 1; j < numAcct; j++) {
                if(accounts[minIndex].getType().compareToIgnoreCase(accounts[j].getType()) > 0) {
                    minIndex = j;
                }
            }
            Account temp = accounts[i];
            accounts[i] = accounts[minIndex];
            accounts[minIndex] = temp;
        }
        print();
    }
    
    /**
    Prints all accounts with their monthly interest and fees
    */
    public void printFeeAndInterest() {
        DecimalFormat formatObj = new DecimalFormat("###,##0.00");
        formatObj.setGroupingUsed(true);
        formatObj.setGroupingSize(GROUPING_SIZE);
        for(int i = 0; i < accounts.length; i++) {
            if(accounts[i] == null) {
                return;
            }
            System.out.println(accounts[i].toString() + "::fee $" + formatObj.format(accounts[i].fee()) + "::monthly interest $" + formatObj.format(accounts[i].monthlyInterest()));
        }
    }
    
    /**
    Updates the accounts' balance in database.
    */
    public void updateBalances() {
        for(int i = 0; i < numAcct; i++) {
            accounts[i].updateBalance();
        }
    }
}