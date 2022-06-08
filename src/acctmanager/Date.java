package acctmanager;

import java.util.Calendar;

/**
This class defines a date object which holds a day, month, and year value.
It contains methods to check if a Date is valid, compare two Date objects, and convert a Date into a string.
@author Dharma Wijesinghe, Min Sun You
*/
public class Date implements Comparable<Date> {
	private int year;
	private int month;
	private int day;
	
	//Constants
	private static final int[] LONG_MONTHS = {1, 3, 5, 7, 8, 10, 12}; //Array of months containing 31 days
	private static final int LONG_MONTH_DAYS = 31;
	private static final int SHORT_MONTH_DAYS = 30;
	private static final int FEBRUARY = 2;
	private static final int FEB_NON_LEAP_YEAR_DAYS = 28;
	private static final int FEB_LEAP_YEAR_DAYS = 29;
	private static final int MAX_NUM_OF_MONTHS = 12;
	private static final int QUADRENNIAL = 4;
	private static final int CENTENNIAL = 100;
	private static final int QUARTER_CENTENNIAL = 400;
	
	/**
	Initializes the Date object with given date formatted string.
	@param date String containing the date to be initialized.
	*/
	public Date (String date) {
	    int slashIndex = date.indexOf("/");
		month = Integer.parseInt(date.substring(0, slashIndex));
		int secondSlashIndex = date.indexOf("/", slashIndex + 1);
		day = Integer.parseInt(date.substring(slashIndex + 1, secondSlashIndex));
		year = Integer.parseInt(date.substring(secondSlashIndex + 1));
	}
	
	/**
	Initializes the Date object with today's date.
	*/
	public Date() {
		Calendar todaysDate = Calendar.getInstance();
		month = todaysDate.get(Calendar.MONTH) + 1;
		day = todaysDate.get(Calendar.DAY_OF_MONTH);
		year = todaysDate.get(Calendar.YEAR);
	}

	/**
    Returns the year value of the calling Date object.
    @return the year of the calling Date object.
    */
    public int year() {
        return year;
    }
	
	/**
	Checks to see if the calling Date object has a long month (a month containing 31 days).
	@return true if it has long month, false otherwise.
	*/
	private boolean hasLongMonth() {
	    for(int i: LONG_MONTHS) {
	        if(i == this.month) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	Checks if the calling Date object is a valid date.
	@return true if the Date object is valid, false otherwise.
	*/
	public boolean isValid() {
	    if(year < 0) {
	        return false;
	    }
		boolean isLeapYear = false;
		if(this.year % QUADRENNIAL == 0 && this.year % CENTENNIAL != 0) {
			isLeapYear = true;
		} else if(this.year % QUADRENNIAL == 0 && this.year % CENTENNIAL == 0 && this.year % QUARTER_CENTENNIAL == 0) {
			isLeapYear = true;
		}
		if(month <= MAX_NUM_OF_MONTHS && month > 0) {
		    if(this.hasLongMonth()) {
		        if(day <= LONG_MONTH_DAYS && day > 0) {
		            return true;
		        }
		    } else {
		        if(this.month != FEBRUARY && this.day <= SHORT_MONTH_DAYS) {
		            return true;
		        } else {
		            if((isLeapYear && this.day <= FEB_LEAP_YEAR_DAYS) || (!isLeapYear && this.day <= FEB_NON_LEAP_YEAR_DAYS)) {
		                return true;
		            }
		        }
		    }
		}
		return false;
	}
	
	/**
	Compares two Date objects.
	@param date the Date being compared to the calling Date object.
	@return 1 if the calling Date object is chronologically later the than parameter date, 0 if they are equal, -1 otherwise.
	*/
	@Override
	public int compareTo(Date date) {
	    if(this.year > date.year) {
	        return 1;
	    } else if(this.year < date.year) {
	        return -1;
	    } else {
	        if(this.month > date.month) {
	            return 1;
	        } else if(this.month < date.month) {
	            return -1;
	        } else {
	            if(this.day > date.day) {
	                return 1;
	            } else if(this.day < date.day) {
	                return -1;
	            } else {
	                return 0;
	            }
	        }
	    }
	}
	
	/**
	Returns String representation of the Date object.
	@return String of the Date object.
	*/
	public String toString() {
	    return month + "/" + day + "/" + year;
	}
}