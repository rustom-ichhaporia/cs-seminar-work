import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Demo class to show off password cracker. 
 * @author Rustom Ichhaporia
 */
public class Demo 
{
    public static void main(String[] args) throws FileNotFoundException
    {
        String option = "";
        Scanner scan = new Scanner(System.in);
        while (!option.equals("exit"))  //Run until user exits
        {
            System.out.println("Would you like to conduct a dictionary attack, brute force attack, or exit? ");
            option = scan.nextLine();
            if (option.equalsIgnoreCase("dictionary attack") || option.equalsIgnoreCase("dictionary"))  //Different input options
            {
                System.out.println("Which name would you like to check? The attack may time out if no results are found. Options are Rustom, Pascal, Alec, Ayan, Ethan: ");
                String user = scan.nextLine();
                DictionaryAttack attack = new DictionaryAttack();
                attack.execute(user);
            }
            else if (option.equalsIgnoreCase("brute force attack") || option.equalsIgnoreCase("brute force") || option.equalsIgnoreCase("brute"))
            {
                System.out.println("Which name would you like to check? The attack may time out if no results are found. Options are Rustom, Pascal, Alec, Ayan, Ethan: ");
                String user = scan.nextLine();
                BruteForceAttack attack = new BruteForceAttack();
                attack.execute(user);
            }
            else if (option.equalsIgnoreCase("exit"))   //Exit program option
            {
                System.out.println("Thank you for using my password cracking program. Press enter to exit.");
                scan.nextLine();
            }
        }
        scan.close();
    }
}