/**
 * Methods class for the encryption/decryption Playfair cipher program. 
 * @author Rustom Ichhaporia, Alec Chen
 * @since 04/28/2020
 */

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Scanner;

public class QProject
{
    private Scanner scan;   //Used for prompting user
    public QProject()
    {
        scan = new Scanner(System.in);
    }
    
    /** 
     * Prompts the user for their keyword. 
     * @return String
     */
    private String getKey()
    {
        System.out.println("Please enter your keyword: ");  //Prompts user for keyword
        String keyword = formatInput(scan.nextLine(), true);    //Formats the input (see method for more details)
        return keyword;
    }
    
    /** 
     * Generates the two-dimensional array cipher table from the user-inputted key. 
     * @param key
     * @return char[][]
     */
    private char[][] generateTable(String key)
    {
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; //Does not contain J
        char[][] table = new char[5][5];    //Mandatory length of table type
        for (int letter = 0; letter < 25; letter++) //Loop through every element
        {
            if (letter < key.length())
            {
                table[(int) (letter/5)][letter%5] = key.charAt(letter);
                alphabet = alphabet.replaceAll(String.valueOf(key.charAt(letter)), ""); //Ensures no crossover
            }
        }
        for (int col = 0; col < 5; col++)
        {
            for (int row = 0; row < 5; row++)
            {
                if (!Character.isLetter(table[row][col]))   //Fills in the rest of the alphabet not used in the keyword
                {
                    table[row][col] = alphabet.charAt(0);
                    alphabet = alphabet.substring(1);
                }
            }
        }
        return table;
    }
    
    /** 
     * Prints the cipher table to the console. 
     * @param table
     */
    public void printTable(char[][] table)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /** 
     * Adds necessary Xs in the cleartext as required by the cipher algorithm. 
     * @param cleartext
     * @return String
     */
    public String addXs(String cleartext)
    {
        String result = "";
        for (int i = 0; i < cleartext.length(); i +=2)
        {
            if (i == cleartext.length() - 1)    //Case for odd-number length, adds X at end
            {
                result += String.valueOf(cleartext.charAt(i)) + "X";    
            }
            else if (cleartext.charAt(i) == cleartext.charAt(i+1))  //If digraph is the same, place an X in the middle
            {
                result += String.valueOf(cleartext.charAt(i)) + "X" + String.valueOf(cleartext.charAt(i));
            }
            else    //Default option
            {
                result += String.valueOf(cleartext.charAt(i)) + String.valueOf(cleartext.charAt(i+1));
            }
        }
        return result;
    }

    /**
     * Runs the encryption. 
     */
    public void encrypt()
    {
        String key = getKey();  //Get key, generate and print table
        char[][] table = generateTable(key);
        printTable(table);
        
        System.out.println("Please enter your cleartext: ");
        String cleartext = addXs(formatInput(scan.nextLine(), false));  //Format cleartext
        System.out.println("The formatted cleartext is: " + cleartext);

        String encoded = "";    //Result to be output
        //Convert table to string for easier method access
        String tableString = new String(table[0]) + new String(table[1]) + new String(table[2]) + new String(table[3]) + new String(table[4]); 

        //Loop over every digraph
        for (int letter = 0; letter < cleartext.length(); letter += 2)
        {
            int letter1Ind = tableString.indexOf(String.valueOf(cleartext.charAt(letter)));
            int letter2Ind = tableString.indexOf(String.valueOf(cleartext.charAt(letter+1)));
            int letter1Row = (int)(letter1Ind/5), letter1Col = (letter1Ind%5), letter2Row = (int)(letter2Ind/5), letter2Col = (letter2Ind%5);
            if (letter1Row == letter2Row)   //Case for same row
            {
                if (letter1Col == 4)
                    encoded += String.valueOf(table[letter1Row][0]);
                else
                    encoded += String.valueOf(table[letter1Row][letter1Col+1]);
                if (letter2Col == 4)
                    encoded += String.valueOf(table[letter2Row][0]);
                else
                    encoded += String.valueOf(table[letter2Row][letter2Col+1]);
            }
            else if (letter1Col == letter2Col)  //Case for same column
            {
                if (letter1Row == 4)
                    encoded += String.valueOf(table[0][letter1Col]);
                else
                    encoded += String.valueOf(table[letter1Row+1][letter1Col]);
                if (letter2Row == 4)
                    encoded += String.valueOf(table[0][letter2Col]);
                else
                    encoded += String.valueOf(table[letter2Row+1][letter2Col]);
            }
            else    //Case for different column and row
            {
                encoded += String.valueOf(table[letter1Row][letter2Col]);
                encoded += String.valueOf(table[letter2Row][letter1Col]);
            }
        }
        System.out.println("Your encoded message is: " + encoded);  //Output result
    }

    /**
     * Runs the decryption. Mostly the same as the encryption method. 
     */
    public void decrypt()
    {
        String key = getKey();  //Gets key, generates table
        char[][] table = generateTable(key);
        printTable(table);
        
        System.out.println("Please enter your ciphertext: ");   //Prompts user for ciphertext and formats it
        String ciphertext = addXs(formatInput(scan.nextLine(), false));
        System.out.println("The formatted ciphertext is: " + ciphertext);

        String decoded = "";    //String to be outputted
        String tableString = new String(table[0]) + new String(table[1]) + new String(table[2]) + new String(table[3]) + new String(table[4]); 

        //Loop over every digraph
        for (int letter = 0; letter < ciphertext.length(); letter += 2)
        {
            int letter1Ind = tableString.indexOf(String.valueOf(ciphertext.charAt(letter)));
            int letter2Ind = tableString.indexOf(String.valueOf(ciphertext.charAt(letter+1)));
            int letter1Row = (int)(letter1Ind/5), letter1Col = (letter1Ind%5), letter2Row = (int)(letter2Ind/5), letter2Col = (letter2Ind%5);
            if (letter1Row == letter2Row)   //Same row case, switch wraparound to opposite direction
            {
                if (letter1Col == 0)
                    decoded += String.valueOf(table[letter1Row][4]);
                else
                    decoded += String.valueOf(table[letter1Row][letter1Col-1]);
                if (letter2Col == 0)
                    decoded += String.valueOf(table[letter2Row][4]);
                else
                    decoded += String.valueOf(table[letter2Row][letter2Col-1]);
            }
            else if (letter1Col == letter2Col)  //Same column case, switch wraparound to opposite direction
            {
                if (letter1Row == 0)
                    decoded += String.valueOf(table[4][letter1Col]);
                else
                    decoded += String.valueOf(table[letter1Row-1][letter1Col]);
                if (letter2Row == 0)
                    decoded += String.valueOf(table[4][letter2Col]);
                else
                    decoded += String.valueOf(table[letter2Row-1][letter2Col]);
            }
            else    //Different row and column case
            {
                decoded += String.valueOf(table[letter1Row][letter2Col]);
                decoded += String.valueOf(table[letter2Row][letter1Col]);
            }
        }
        System.out.println("Your decoded message is: " + decoded);  //Output result
    }
    
    /** 
     * Formats the input of the user so the program does not run into errors. 
     * @param raw_input
     * @param removeDuplicates
     * @return String
     */
    private String formatInput(String raw_input, boolean removeDuplicates)
    {
        String input = raw_input;
        input = input.replaceAll("[^a-zA-Z]", "");  //Removes nonalphabetic characters
        input = input.toUpperCase();    //Converts to upper case
        input = input.replaceAll("J", "I"); //Replaces Js with Is
        if (removeDuplicates)
        {
            input = Arrays.asList(input.split("")) //Removes duplicate characters
            .stream()
            .distinct()
            .collect(Collectors.joining());
        }
        return input;
    }
}