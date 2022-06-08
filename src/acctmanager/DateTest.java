/**
 * 
 */
package acctmanager;

import static org.junit.Assert.*;
import org.junit.Test;
import acctmanager.Date;

/**
 * @author Dharma Wijesinghe, Min Sun You
 *
 */
public class DateTest {

    /**
     * Test method for {@link acctmanager.Date#isValid()}.
     */
    @Test
    public void testIsValid() {
        //Test case 1
        Date testDate1 = new Date("0/12/2022");
        assertFalse(testDate1.isValid());
        
        //Test case 2
        Date testDate2 = new Date("13/12/2022");
        assertFalse(testDate2.isValid());
        
        //Test case 3
        Date testDate3 = new Date("1/32/2022");
        assertFalse(testDate3.isValid());
        
        //Test case 4
        Date testDate4 = new Date("7/31/2022");
        assertTrue(testDate4.isValid());
        
        //Test case 5
        Date testDate5 = new Date("2/29/2022");
        assertFalse(testDate5.isValid());
        
        //Test case 6
        Date testDate6 = new Date("2/28/2022");
        assertTrue(testDate6.isValid());
        
        //Test case 7
        Date testDate7 = new Date("2/29/2020");
        assertTrue(testDate7.isValid());
        
        //Test case 8
        Date testDate8 = new Date("2/29/2100");
        assertFalse(testDate8.isValid());
        
        //Test case 9
        Date testDate9 = new Date("2/29/2000");
        assertTrue(testDate9.isValid());
        
        //Test case 10
        Date testDate10 = new Date("4/31/2022");
        assertFalse(testDate10.isValid());
        
        //Test case 11
        Date testDate11 = new Date("1/12/-2012");
        assertFalse(testDate11.isValid());
    }
}