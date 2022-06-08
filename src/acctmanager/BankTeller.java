package acctmanager;

import java.util.Scanner;
/**
This is the user interface class
Reads commands through console
Handles all exception and invalid data
@author Dharma Wijesinghe, Min Sun You
*/
public class BankTeller {
    private String[] userInputs;
    private boolean tellerIsRunning;
    private AccountDatabase database;
    private static final int ACCOUNT_NOT_FOUND = -1;
    private static final int PLACE_HOLDER_VALUE = 0;

    /**
    Instantiates a bank teller object
    */
    public BankTeller() {
        database = new AccountDatabase();
    }
    
    /**
    Capitalizes first and last name
    */
    private void formatNames() {
        userInputs[2] = userInputs[2].substring(0,1).toUpperCase() + userInputs[2].substring(1).toLowerCase();
        userInputs[3] = userInputs[3].substring(0,1).toUpperCase() + userInputs[3].substring(1).toLowerCase();
    }
    
    /**
    Checks the command user inputs
    Depending on the command, calls the corresponding function.
    @param userInputLine the string user enters.
    */
    private void checkUserInput(String userInputLine) {
        if(userInputLine.equals("")) {
            return;
        }
        userInputs = userInputLine.split("\\s+");
        if(userInputs[0].equals("O")) {openAccount(userInputs);}
        else if(userInputs[0].equals("C")) {closeAccount(userInputs);}
        else if(userInputs[0].equals("D")) {deposit(userInputs);}
        else if(userInputs[0].equals("W")) {withdraw(userInputs);}
        else if(userInputs[0].equals("P")) {printAccounts();}
        else if(userInputs[0].equals("PT")) {printAccountsByType();}
        else if(userInputs[0].equals("PI")) {printAccountsWithDetails();}
        else if(userInputs[0].equals("UB")) {updateBalance();}
        else if(userInputs[0].equals("Q")) {quit();}
        else {
            System.out.println("Invalid command!");
        }
    }
    
    /**
    Calls which account to open depending on command
    Handles exception where there is missing data
    @param userInputs the array holding user inputs
    */
    private void openAccount(String[] userInputs) {
        try {
            formatNames();
            if(userInputs[1].equals("C")) { //Opening checking account
                openCheckingAccount(userInputs);
            } else if(userInputs[1].equals("CC")) {
                openCollegeCheckingAccount(userInputs);
            } else if(userInputs[1].equals("S")) {
                openSavingsAccount(userInputs);
            } else if(userInputs[1].equals("MM")) {
                openMoneyMarketAccount(userInputs);
            } else if(userInputs[1] == null) {
                throw new Exception("Missing data for opening an account.");
            }
            else {
                throw new Exception("Invalid account type!");
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data for opening an account.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Opens a checking account.
    Decides whether if requirement is met to open.
    Handles all exceptions
    @param userInputs the array holding user inputs
    */
    private void openCheckingAccount(String[] userInputs) {
        try {
            Date customerDob = new Date(userInputs[4]);
            if(!customerDob.isValid() || customerDob.compareTo(new Date()) > 0) {
                throw new Exception("Date of birth invalid.");
            }
            double inputBalance = Double.parseDouble(userInputs[5]);
            if(inputBalance <= 0) {
                throw new Exception("Initial deposit cannot be 0 or negative.");
            }
            Profile customer = new Profile(userInputs[2], userInputs[3], customerDob);
            Account newCheckingAccount = new Checking(customer, false, inputBalance);
            Account testCollegeCheckingAccount = new CollegeChecking(customer, PLACE_HOLDER_VALUE);
            if(database.findAcc(testCollegeCheckingAccount) != ACCOUNT_NOT_FOUND) {
                //Customer already has a college checking account, so a checking account cannot be added.
                throw new Exception(customer.toString() + " same account(type) is in the database.");
            } else if(database.findAcc(newCheckingAccount) == ACCOUNT_NOT_FOUND) {
                database.open(newCheckingAccount);
                System.out.println("Account opened.");
            } else { //Checking account is already in the system, may be open or closed.
                if(database.open(newCheckingAccount)) {
                    System.out.println("Account reopened.");
                } else { //Checking account is already open.
                    throw new Exception(customer.toString() + " same account(type) is in the database.");
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data for opening an account.");
        }
        catch(NumberFormatException e) {
            System.out.println("Not a valid amount.");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Opens a college checking account.
    Decides whether it requirement is met to open
    Handles all exceptions
    @param userInputs the array holding user inputs
    */
    private void openCollegeCheckingAccount(String[] userInputs) {
        try {
            Date customerDob = new Date(userInputs[4]);
            if(!customerDob.isValid() || customerDob.compareTo(new Date()) > 0) {
                throw new Exception("Date of birth invalid.");
            }
            double inputBalance = Double.parseDouble(userInputs[5]);
            if(inputBalance <= 0) {
                throw new Exception("Initial deposit cannot be 0 or negative.");
            }
            int campusCode = Integer.parseInt(userInputs[6]);
            if(!(campusCode >= 0 && campusCode <= 2)) {
                throw new Exception("Invalid campus code.");
            }
            Profile customer = new Profile(userInputs[2], userInputs[3], customerDob);
            Account testCheckingAccount = new Checking(customer, PLACE_HOLDER_VALUE);
            Account newCollegeCheckingAccount = new CollegeChecking(customer, false, inputBalance, campusCode);
            if(database.findAcc(testCheckingAccount) != ACCOUNT_NOT_FOUND) {
                //Customer already has a checking account, so another one cannot be added.
                throw new Exception(customer.toString() + " same account(type) is in the database.");
            } else if(database.findAcc(newCollegeCheckingAccount) == ACCOUNT_NOT_FOUND) {
                database.open(newCollegeCheckingAccount);
                System.out.println("Account opened.");
            } else { //College checking account is already in the system, may be open or closed.
                if(database.open(newCollegeCheckingAccount)) {
                    System.out.println("Account reopened.");
                } else { //College Checking account is already open.
                    throw new Exception(customer.toString() + " same account(type) is in the database.");
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data for opening an account.");
        }
        catch(NumberFormatException e) {
            System.out.println("Not a valid amount.");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Opens a savings account.
    Decides whether if requirement is met to open.
    Handles all exceptions
    @param userInputs the array holding user inputs
    */
    private void openSavingsAccount(String[] userInputs) {
        try {
            Date dob = new Date(userInputs[4]);
            if(!dob.isValid() || dob.compareTo(new Date()) > 0) {
                throw new Exception("Date of birth invalid.");
            }
            if(Double.parseDouble(userInputs[5]) <= 0) {
                throw new Exception("Initial deposit cannot be 0 or negative.");
            }
            //Finding the account
            Profile holder = new Profile(userInputs[2], userInputs[3], dob);
            Account acc = new Savings(holder, false, Double.parseDouble(userInputs[5]), Integer.parseInt(userInputs[6]));
            if(database.findAcc(acc) != ACCOUNT_NOT_FOUND) {// acc exists
                if(database.isClosed(acc)) { //is it closed?
                    database.open(acc); //reopen
                }
                else { //it's already open
                    throw new Exception(acc.holder.toString() + " same account(type) is in the database.");    
                }
            }
            else {//account not found
                database.open(acc);
                System.out.println("Account opened.");
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data for opening an account.");
        }
        catch(NumberFormatException e) {
            System.out.println("Not a valid amount.");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Opens a money market account.
    Decides whether if requirement is met to open.
    Handles all exceptions
    @param userInputs the array holding user inputs
    */
    private void openMoneyMarketAccount(String[] userInputs) {
        try {
            Date dob = new Date(userInputs[4]);
            if(!dob.isValid() || dob.compareTo(new Date()) > 0) {
                throw new Exception("Date of birth invalid.");
            }
            if(Double.parseDouble(userInputs[5]) <= 0) {
                throw new Exception("Initial deposit cannot be 0 or negative.");
            }
            if(Double.parseDouble(userInputs[5]) < 2500) {
                throw new Exception("Minimum of $2500 to open a MoneyMarket account.");
            }
            //Finding the account
            Profile holder = new Profile(userInputs[2], userInputs[3], dob);
            Account acc = new MoneyMarket(holder, false, Double.parseDouble(userInputs[5]));
            if(database.findAcc(acc) != ACCOUNT_NOT_FOUND) {// acc exists
                if(database.isClosed(acc)) { //is it closed?
                    database.open(acc); //reopen
                    System.out.println("Account reopened.");
                }
                else { //it's already open
                    throw new Exception(acc.holder.toString() + " same account(type) is in the database.");    
                }
            }
            else {//account not found
                database.open(acc);
                System.out.println("Account opened.");
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data for opening an account.");
        }
        catch(NumberFormatException e) {
            System.out.println("Not a valid amount.");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Returns the type of account.
    @return String the type of account.
    */
    private String typeOfAccount() {
        if(userInputs[1].equals("C")) { 
            return "Checking";
        } else if(userInputs[1].equals("CC")) {
            return "CollegeChecking";
        } else if(userInputs[1].equals("S")) {
            return "Savings";
        } else if(userInputs[1].equals("MM")) {
            return "MoneyMarket";
        }
        return null;
    }
    
    /**
    Returns the type of account used for printing.
    @return String the type of account
    */
    private String typeOfAccountWithSpace() {
        if(userInputs[1].equals("C")) { 
            return "Checking";
        } else if(userInputs[1].equals("CC")) {
            return "College Checking";
        } else if(userInputs[1].equals("S")) {
            return "Savings";
        } else if(userInputs[1].equals("MM")) {
            return "Money Market";
        }
        return null;
    }
 
    /**
    Creates a temporary account
    @param holder the Profile of account
    @param amount the balance
    @return Account the temporary account
    */
    private Account createTempAcc(Profile holder, double amount) {
        Account tempAcc = null;
        String type = typeOfAccount();
        if(type.equals("Checking")) {
            tempAcc = new Checking(holder, amount); //amount set to 0 since its not useful here
        }
        else if(type.equals("CollegeChecking")) {
            tempAcc = new CollegeChecking(holder, amount);
        }
        else if(type.equals("Savings")){
            tempAcc = new Savings(holder, amount);
        }
        else if(type.equals("MoneyMarket")) {
            tempAcc = new MoneyMarket(holder, amount);
        }
        return tempAcc;
    }
    
    /**
    Closes an account
    Does nothing if account is already closed.
    Handles exceptions.
    @param userInputs the array holding user inputs.
    */
    private void closeAccount(String[] userInputs) {
        try {
            Date dob = new Date(userInputs[4]);
            if(!dob.isValid()) {
                throw new Exception("Date of birth invalid.");
            }
            Profile profile = new Profile(userInputs[2], userInputs[3], dob);
            Account matchingAcc = createTempAcc(profile, PLACE_HOLDER_VALUE);
            if(database.close(matchingAcc)) {
                System.out.println("Account closed.");
            }
            else {
                throw new Exception("Account is closed already.");
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data for closing an account.");
        }    
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Deposits into an account.
    Does nothing if account not in database.
    Handles all exceptions.
    @param userInputs the array holding user inputs
    */
    private void deposit(String[] userInputs) {
        try {
            Date dob = new Date(userInputs[4]);
            if(!dob.isValid()) {
                throw new Exception("Date of birth invalid.");
            }
            double deposit = Double.parseDouble(userInputs[5]);
            if(deposit <= 0) {
                throw new Exception("Deposit - amount cannot be 0 or negative.");
            }
            Profile profile = new Profile(userInputs[2], userInputs[3], dob);
            Account tempAcc = createTempAcc(profile, deposit);
            if(database.findAcc(tempAcc) == -1){
                throw new Exception(userInputs[2] + " " + userInputs[3] + " " + dob.toString() + " " + typeOfAccountWithSpace() + " is not in the database.");
            }
            else {
                database.deposit(tempAcc);
                System.out.println("Deposit - balance updated.");
            }
        }
        catch(NumberFormatException e) {
            System.out.println("Not a valid amount.");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Withdraws a given amount from account.
    Does nothing if account does not exist.
    Handles all exceptions
    @param userInputs the array holding user inputs.
    */
    private void withdraw(String[] userInputs) {
        try {
            Date dob = new Date(userInputs[4]);
            if(!dob.isValid()) {
                throw new Exception("Date of birth invalid.");
            }
            double withdraw = Double.parseDouble(userInputs[5]);
            if(withdraw <= 0) {
                throw new Exception("Withdraw - amount cannot be 0 or negative.");
            }
            Profile profile = new Profile(userInputs[2], userInputs[3], dob);
            Account tempAcc = createTempAcc(profile, withdraw);
            if(database.findAcc(tempAcc) == -1){
                throw new Exception(userInputs[2] + " " + userInputs[3] + " " + dob.toString() + " " +  typeOfAccountWithSpace() + " is not in the database.");
            }
            else {
                if(database.withdraw(tempAcc) == false) {
                    System.out.println("Withdraw - insufficient fund.");
                }
                else {
                    System.out.println("Withdraw - balance updated.");
                }
            }
        }
        catch(NumberFormatException e) {
            System.out.println("Not a valid amount.");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Prints all the accounts in the database
    Handles exception where database is empty.
    */
    private void printAccounts() { //P
        try{
            if(database.sizeOfDatabase() == 0) {
                throw new Exception("Account Database is empty!");
            }
            System.out.println("*list of accounts in the database*");
            database.print();
            System.out.println("*end of list*");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Prints all accounts ordered by their type.
    Handles exception where database is empty.
    */
    private void printAccountsByType() { //PT
        try{
            if(database.sizeOfDatabase() == 0) {
                throw new Exception("Account Database is empty!");
            }
            System.out.println("*list of accounts by account type.");
            database.printByAccountType();
            System.out.println("*end of list.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
    Prints all accounts including their fees and monthly interest.
    Handles exception where database is empty.
    */
    private void printAccountsWithDetails() {//PI
        try{
            if(database.sizeOfDatabase() == 0) {
                throw new Exception("Account Database is empty!");
            }
            System.out.println("*list of accounts with fee and monthly interest");
            database.printFeeAndInterest();
            System.out.println("*end of list.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Updates all balances in database.
    Prints all accounts in database.
    Handles exception where database is empty.
    */
    private void updateBalance() { //UB
        try{
            if(database.sizeOfDatabase() == 0) {
                throw new Exception("Account Database is empty!");
            }
            database.updateBalances();
            System.out.println("*list of accounts with updated balance");
            database.print();
            System.out.println("*end of list.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
    Quits the user interface
    */
    private void quit() {
        System.out.println("Bank Teller is terminated.");
        tellerIsRunning = false;
    }
    
    /**
    Continuously reads console for inputs
    */
    public void run() {
        System.out.println("Bank Teller is Running.");
        tellerIsRunning = true;
        Scanner sc = new Scanner(System.in);
        while(tellerIsRunning) {
            String userInputLine = sc.nextLine();
            checkUserInput(userInputLine);
        }
        sc.close();
    }
}