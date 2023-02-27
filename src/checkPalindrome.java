import java.util.Scanner;

/**
 * Check Palindrome - a recursive function tha determines if a string is a palindrome.
 *
 * Program assumes to ignore spaces, program can also check for phrases
 *
 * @author Jaavin
 * @date 2022/01/06
 */

public class checkPalindrome {

    /**
     * isPalindrome - recursively check if str is palindrome
     * @param str string to check if palindrome
     * @return if str is a palindrome
     */
    private static boolean isPalindrome(String str) {
        boolean thisPalindrome = false;

        // if the string is 0/1 char long it is palindrome
        if(str.length() == 0 || str.length() == 1){
            thisPalindrome = true;
        }
        // check if the first and last char are the same
        else if(str.charAt(0) == str.charAt(str.length()-1)) {
            // recursively call, and remove the outer/first and last char
            thisPalindrome = isPalindrome(str.substring(1, str.length()-1));
        }

        return thisPalindrome;
    }

    /**
     * Main Program
     * @param args cmd line args
     */
    public static void main(String[] args) {
        // Ask user for string
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the String :");
        String str = sc.nextLine();

        // remove all the spaces in the string
        String strCheck = str.replaceAll("\\s","");

        //Check if string is palindrome
        if (isPalindrome(strCheck.toUpperCase())) {
            System.out.println(str + " is a palindrome");
        } else {
            System.out.println(str + " is NOT a palindrome");
        }

        sc.close();
    }
}