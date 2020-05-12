import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.io.BufferedReader;
import java.util.Scanner;
/**
 * Class to execue brute force password attack. 
 */
public class BruteForceAttack
{
    public void execute(String user)
    {
        Instant start = Instant.now();  //Used to time the process
        String passwordHashtext = "";
        try 
        {
			BufferedReader reader = new BufferedReader(new FileReader("credentials.txt"));  //Look through credentials file for user
			String line = reader.readLine();
            while (line != null) 
            {
                int ln = line.indexOf(user);
                System.out.println(ln);
                if (line.indexOf(user) == 0)
                {
                    passwordHashtext = line.split(",")[1];
                    System.out.println("Password hashtext: " + passwordHashtext);   //Find password to be cracked
                    String alphabet = "abcdefghijklmnopqrstuvwxyz";
                    int maxLen = 9;
                    for (int i = 1; i < maxLen; i++)
                    { 
                        StringBuilder combination = new StringBuilder(i);
                        for (int j = 0; j < i; j++)
                        {
                            for (int k = 0; k < 26; k++)
                            {
                                combination.insert(j, alphabet.charAt(k));
                                
                                String combinationHashtext = "";    //Brute force password string
                                try
                                {
                                    MessageDigest md = MessageDigest.getInstance("MD5");    //Convert to MD5
                                    md.update(combination.toString().getBytes());
                                    byte[] digest = md.digest(line.getBytes(StandardCharsets.UTF_8));
                                    StringBuilder sb = new StringBuilder();
                                    for (byte b : digest) 
                                    {
                                        sb.append(String.format("%02x", b));
                                    }
                                    combinationHashtext = sb.toString();
                                    System.out.println(combinationHashtext);
                                }
                                catch (NoSuchAlgorithmException e) 
                                { 
                                    throw new RuntimeException(e); 
                                } 
                                if (combinationHashtext.equals(passwordHashtext))   //Check if hashes match
                                {
                                    System.out.println("\"" + combination.toString() + "\"'s hashtext matches! That's the password!");
                                    System.in.read();
                                    break;
                                }
                            
                            }
                        }
                    }
                }
                line = reader.readLine();   //Check next line
			}
			reader.close();
        } 
        catch (IOException e) 
        {
			e.printStackTrace();
        }
        
        Instant finish = Instant.now(); //End process timer
 
        long timeElapsed = Duration.between(start, finish).toMillis(); 
        System.out.println("The amount of time taken in seconds was: " + (timeElapsed/1000.0));
    }
}