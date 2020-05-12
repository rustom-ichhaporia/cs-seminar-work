/**
 * Driver class and method to run an encryption and decryption program for a modified Playfair's cipher. 
 * @author Rustom Ichhaporia, Alec Chen
 * @since 04/28/2020
 */

import java.util.Scanner;

public class EncrypytionDriver 
{
    public static void main(String[] args)
    {
        String option = ""; //User prompted action
        Scanner scan = new Scanner(System.in);
        QProject proj = new QProject(); //Initializes QProject object to run encryption and decryption methods
        while (!option.equals("exit"))  //Wait until the user chooses to exit
        {
            System.out.println("Would you like to encode, decode, or exit? ");  
            option = scan.nextLine();
            if (option.equals("encode"))    //Encoding option
            {
                proj.encrypt();                
            }
            else if (option.equals("decode"))   //Decoding option
            {
                proj.decrypt();
            }
            else if (option.equals("exit"))     //Closes program
            {
                System.out.println("Thank you for using my encryption algorithm. Press enter to close the program. Goodbye!");
                scan.nextLine();
            } 
            else    //Safety in case the user does not enter a valid option
            {
                System.out.println("This is not a valid option. Please try again.");
            }
        }
        scan.close();
    }
}