import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.io.BufferedReader;

/**
 * Class to execute dictionary attack. 
 */
public class DictionaryAttack 
{
    public void execute(String user) 
    {
        Instant start = Instant.now();
        String passwordHashtext = "";
        try 
        {
			BufferedReader reader = new BufferedReader(new FileReader("credentials.txt")); 
			String line = reader.readLine();
            while (line != null) //Find user and password to be cracked
            {
                int i = line.indexOf(user);
                System.out.println(i);
                if (line.indexOf(user) == 0)
                {
                    passwordHashtext = line.split(",")[1];
                    System.out.println(passwordHashtext);
                    try 
                    {
                        String dictHashtext = "";
                        BufferedReader reader2 = new BufferedReader(new FileReader("passwords.txt"));   //Look through dictionary
                        String line2 = reader2.readLine();
                        while (line2 != null) 
                        {
                            try
                            {
                                MessageDigest md2 = MessageDigest.getInstance("MD5");   //Create md5 hashes
                                md2.update(line2.getBytes());
                                byte[] digest2 = md2.digest(line2.getBytes(StandardCharsets.UTF_8));
                                StringBuilder sb2 = new StringBuilder();
                                for (byte b2 : digest2) 
                                {
                                    sb2.append(String.format("%02x", b2));
                                }
                                dictHashtext = sb2.toString();
                                System.out.println(dictHashtext);
                            }
                            catch (NoSuchAlgorithmException e) 
                            { 
                                throw new RuntimeException(e); 
                            } 
                                    
                            if (dictHashtext.equals(passwordHashtext))  //Check if match
                            {
                                System.out.println("\"" + line2 + "\"'s hashtext matches! That's the password!");
                                break;
                            }

                            line2 = reader2.readLine();
                        }
                        reader2.close();
                    } 
                    catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
                }
                line = reader.readLine();
			}
			reader.close();
        } 
        catch (IOException e) 
        {
			e.printStackTrace();
        }
        
        Instant finish = Instant.now(); //Print time taken
 
        long timeElapsed = Duration.between(start, finish).toMillis(); 
        System.out.println("The amount of time taken in seconds was: " + (timeElapsed/1000.0));
    }
}