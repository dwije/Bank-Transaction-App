package acctmanager;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Dharma Wijesinghe, Min Sun You
 *
 */
public class MoneyMarketTest { //MoneyMarket class, test monthlyInterest() [5 points]
    
    //If you write all test cases in single test method, you have to comment what you are testing
    //But it's better to separate test cases by having multiple test methods.
    //You dont need any comments if the method name itself is self explaining
    /**
     * Test method for {@link acctmanager.MoneyMarket#monthlyInterest()}.
     */
    @Test
    public void testMonthlyInterestForLoyalMoneyMarketAccounts() {
        //Test case #1
        MoneyMarket test1 = new MoneyMarket(new Profile("John", "Doe", new Date("05/13/2002")), false, 2500);
        double expectedOutput = 2500 * (((0.95) / 100.0) / 12.0);
        double monthlyInterestTest1 = test1.monthlyInterest();
        assertEquals(expectedOutput, monthlyInterestTest1, 0.0);
    }
    
    @Test
    public void testMonthlyInterestForNonLoyalMoneyMarketAccounts() {
        //test case #2
        MoneyMarket test2 = new MoneyMarket(new Profile("John", "Doe", new Date("05/13/2002")), false, 2500);
        test2.withdraw(1000.0);
        double monthlyInterestTest2 = test2.monthlyInterest();
        assertEquals(1, monthlyInterestTest2, 0);
    }
    
    @Test
    public void testMonthlyInterestForNonLoyalToLoyalAccounts() {
        //test case #3
        MoneyMarket test3 = new MoneyMarket(new Profile("John", "Doe", new Date("05/13/2002")), false, 2500);
        test3.withdraw(1000);
        double monthlyInterestNonLoyal = test3.monthlyInterest();
        assertEquals(monthlyInterestNonLoyal, 1, 0);
        
        test3.deposit(1000);
        double monthlyInterestLoyal = test3.monthlyInterest();
        double expectedValue = 2500 * ((0.95 / 100) / 12);
        assertEquals(monthlyInterestLoyal, expectedValue, 0);
    }
    
    @Test
    public void testMonthlyInterestForLoyalToNonLoyalAccounts() {
        //test case #4
        MoneyMarket test4 = new MoneyMarket(new Profile("John", "Doe", new Date("05/13/2002")), false, 2500);
        double monthlyInterestLoyal = test4.monthlyInterest();
        double expectedValue = 2500 * ((0.95 / 100) / 12);
        assertEquals(expectedValue, monthlyInterestLoyal, 0);
        
        test4.withdraw(2000);
        double monthlyInterestNonLoyal = test4.monthlyInterest();
        double secondExpectedValue = 500 * ((0.8 / 100) / 12);
        assertEquals(secondExpectedValue, monthlyInterestNonLoyal, 0);
    }
}
