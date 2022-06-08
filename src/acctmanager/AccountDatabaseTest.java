/**
 * 
 */
package acctmanager;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Dharma Wijesinghe, Min Sun You
 *
 */
public class AccountDatabaseTest {

    /**
     * Test method for {@link acctmanager.AccountDatabase#open(acctmanager.Account)}.
     */
    @Test
    public void testOpenNewAccount() {
        //Test case 1 - adding a new account to the database
        AccountDatabase database = new AccountDatabase();
        Profile customer = new Profile("John", "Doe", new Date("1/12/1979"));
        Account account = new Checking(customer, false, 1000);
        assertTrue(database.open(account));
    }
    
    /**
     * Test method for {@link acctmanager.AccountDatabase#open(acctmanager.Account)}.
     */
    @Test
    public void testOpeningAClosedAccount() {
        //Test case 2 - opening a closed account in the database
        AccountDatabase database = new AccountDatabase();
        Profile customer = new Profile("Jane", "Doe", new Date("5/23/1998"));
        Account account = new Savings(customer, false, 4000, 1);
        database.close(account);
        account = new Savings(customer, false, 5000, 1);
        assertTrue(database.open(account));
        assertFalse(account.closed);
        assertEquals(5000, account.balance, 0);
    }
    
    /**
     * Test method for {@link acctmanager.AccountDatabase#open(acctmanager.Account)}.
     */
    @Test
    public void testOpeningAnOpenAccount() {
        //Test case 3 - opening an already opened account
        AccountDatabase database = new AccountDatabase();
        Profile customer = new Profile("John", "Doe", new Date("4/5/1985"));
        Account account = new MoneyMarket(customer, false, 3000);
        database.open(account);
        assertFalse(database.open(account));
        assertFalse(account.closed);
    }
    
    /**
     * Test method for {@link acctmanager.AccountDatabase#close(acctmanager.Account)}.
     */
    @Test
    public void testCloseForExistingOpenCheckingAccount() {
        //Test case #1
        AccountDatabase test1Database = new AccountDatabase();
        Account test1 = new Checking(new Profile("John", "Doe", new Date("05/13/2002")), false, 2500);
        test1Database.open(test1);
        boolean ourOutput1 = test1Database.close(test1);
        assertTrue(ourOutput1);
        assertTrue(test1.closed);
        assertEquals(0, test1.balance, 0);
    }
    
    @Test
    public void testCloseForExistingCollegeCheckingAccount() {
        //test case #2
        AccountDatabase test2Database = new AccountDatabase();
        Account test2 = new CollegeChecking(new Profile("John", "Doe", new Date("05/13/2002")), false, 2500, 0);
        test2Database.open(test2);
        boolean ourOutput2 = test2Database.close(test2);
        assertTrue(ourOutput2);
        assertTrue(test2.closed);
        assertEquals(0, test2.balance, 0);
        assertEquals(0, ((CollegeChecking) test2).getCampusCd());
    }
    
    @Test
    public void testCloseForExistingSavingsAccount() {
        //Test case #3
        AccountDatabase test3Database = new AccountDatabase();
        Account test3 = new Savings(new Profile("John", "Doe", new Date("05/13/2002")), false, 2500, 1);
        test3Database.open(test3);
        boolean ourOutput3 = test3Database.close(test3);
        assertTrue(ourOutput3);
        assertTrue(test3.closed);
        assertEquals(0, test3.balance, 0);
        assertFalse(((Savings)test3).loyal);
    }
    
    @Test 
    public void testCloseForExistingMoneyMarketAccount() {
        //Test case #4
        AccountDatabase test4Database = new AccountDatabase();
        Account test4 = new MoneyMarket(new Profile("John", "Doe", new Date("05/13/2002")), false, 2500);
        test4Database.open(test4);
        boolean ourOutput4 = test4Database.close(test4);
        assertTrue(ourOutput4);
        assertTrue(test4.closed);
        assertEquals(0, test4.balance, 0);
        assertFalse(((MoneyMarket)test4).loyal);
    }
    
    @Test
    public void testCloseForNonExistentAccount() {
        //Test case #5
        AccountDatabase test5Database = new AccountDatabase();
        Account test5 = new Checking(new Profile("John", "Doe", new Date("05/13/2002")), false, 2500);
        boolean ourOutput5 = test5Database.close(test5);
        assertFalse(ourOutput5);
    }
    
    @Test
    public void testCloseForExistingAccountAlreadyClosed() {
        //Test case #6
        AccountDatabase test6Database = new AccountDatabase();
        Account test6 = new Checking(new Profile("John", "Doe", new Date("05/13/2002")), false, 2500);
        test6Database.open(test6);
        test6Database.close(test6);
        boolean ourOutput6 = test6Database.close(test6);
        assertFalse(ourOutput6);
    }
}