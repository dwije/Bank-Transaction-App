package acctmanager;

/**
Describe the person holding an account
Includes first last name and date of birth
@author Dharma Wijesinghe, Min Sun You
*/
public class Profile {
    private String fname;
    private String lname;
    private Date dob;
    
    /**
     * Constructor for Profile class.
     * Creates a Profile object with the specified first name, last name, and date of birth.
     * @param fname
     * @param lname
     * @param dob
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }
    
    /**
     * Determines if the calling object is equal to the parameter object.
     * @param obj
     * @return true of the two objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Profile) {
            Profile profile = (Profile) obj;
            return (fname.equals(profile.fname) && lname.equals(profile.lname)) && (dob.compareTo(profile.dob)) == 0;
        }
        return false;
    }
    
    /**
     * Converts the calling Profile object to a string.
     * @return a string representation of the Profile object.
     */
    @Override
    public String toString() {    
        return this.fname + " " + this.lname + " " + dob.toString();

    }
}