package acctmanager;

/**
This is the driver class used to run the project.
The main method invokes run()
@author Dharma Wijesinghe, Min Sun You
*/
public class RunProject2 {
    
    /**
    Default unused constructor
    */
    public RunProject2() {
        
    }
    /**
    Runs the project by calling the run() method from the BankTeller class.
    @param args String array to hold any argument from the command line.
    */
    public static void main(String[] args) {
        new BankTeller().run(); 
    }
}