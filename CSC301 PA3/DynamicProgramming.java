/*
 * CSC301 Programming Assignment 3
 * November/13/2023
 * Author : Havin Lim
 */

package csc301.f23.pa3;

import java.util.Arrays;
import java.util.HashSet;

public class DynamicProgramming {

    public static int SwordSelection(Integer[] studentPref, Integer[] swordsList) {

        // Initialize variables
        // result will be the returning total difference of the assigned length of the sword from the preferred length of
        // each student
        int result = 0;

        // Sort arrays in increasing order
        Arrays.sort(studentPref);
        Arrays.sort(swordsList);
        
        // For all students in the studentPref array
            for(int i = 0; i < studentPref.length; i++) {

                // For all swords in the swordsList array
                for(int j = 0; j < swordsList.length; j++) {

                    // If the length of the sword in the swordList is greater than or equal to the preferred length of the student
                    if(studentPref[i] <= swordsList[j]) {

                        // Add the difference (length of sword - preferred length) to result
                        result = result + (swordsList[j] - studentPref[i]);
                        // Set the length of the sword to 0 to indicate that it has been assigned to a student
                        swordsList[j] = 0;

                        // Break out of the loop
                        break;
                    }
                }
            }

        // Return result  = the total differnece between length of  assigned sword - preferred length of sword
        return result;
    }

    public static int DynamicSwordSelection(Integer[] studentPref, Integer[] swordsList) {
        // Sort arrays in increasing order
        Arrays.sort(studentPref);
        Arrays.sort(swordsList);

        // Initialize variables
        // n = number of students + 1
        // m = number of swords + 1
        int n = studentPref.length;
        int m = swordsList.length;
    
        // Initialize DP table
        int[][] dp = new int[n+1][m+1];
        // As the base case, first row of the DP table is 0
        // Which means that if there are no students, the total difference is 0.
        for(int i = 0; i <= m; i++) {
            dp[0][i] = 0;
        }
    
        // For each student
        for(int i = 1; i <= n; i++) {
            // For each sword
            for(int j = 1; j <= m; j++) {
                // Calculate the absoute difference between the length of the sword and the preferred length of the student
                // Add the difference to the total difference of the previous student and the previous sword
                dp[i][j] = Math.abs(studentPref[i-1] - swordsList[j-1]) + dp[i-1][j-1];

                // If there are more swords than students
                if(j > i) {
                // Consider the case when a sword is left unassigned
                // Compare the total difference of the current student and the current sword with the total difference of the current student and the previous sword
                    dp[i][j] = Math.min(dp[i][j], dp[i][j-1]);
            }

        }
    }
    // Return minimum total difference
     return dp[n][m];
}




     public static boolean Feasibility(String input, HashSet<String> dictionary) {

        // For the base case, if the input string is empty, return true
        if (input.isEmpty()) {
            return true;
        }

        // Check if any prefix of the input string is a valid word in the dictionary
        for (int i = 1; i <= input.length(); i++) {
            String prefix = input.substring(0, i);

            // If the prefix is a valid word in the dictionary
            if (dictionary.contains(prefix)) {

                // Recursively check the remaining part of the string
                String remaining = input.substring(i);

                // Recurively chekc if the remaining part of the string is feasible, return true if feasible
                if (Feasibility(remaining, dictionary)) {
                    return true;
                }
            }
        }
        // Else, if the input string is not feasible, return false
        return false;
    }


    public static String Recovery(String input, HashSet<String> dictionary) {
        // For the base case, if the input string is empty, return an empty string
        if (input.isEmpty()) {
            return "";
        }

        // Check if any prefix of the input string is a valid word in the dictionary
        for (int i = 1; i <= input.length(); i++) {
            String prefix = input.substring(0, i);

            // If the prefix is a valid word in the dictionary
            if (dictionary.contains(prefix)) {

                // Recursively check the remaining part of the string
                String remaining = input.substring(i);
                String result = Recovery(remaining, dictionary);

                // Insert spaces in between the valid words
                if (result != null) {
                    return prefix + (result.isEmpty() ? "" : " ") + result;
                }
            }
        }

        // Else, if the input string is not able to recover, return null
        return null;
    }


    public static int Possibilities(String input, HashSet<String> dictionary) {

        // Initialize an array to store the number of possibilities for each prefix
        int[] possibilities = new int[input.length() + 1];

        // The number of possibilities for the empty string is 1
        possibilities[0] = 1;
    
        // Check each prefix of the input string
        for (int i = 0; i < input.length(); i++) {

            // If the number of possibilities for the current position is not 0
            if (possibilities[i] != 0) {

                // Check each suffix of the input string
                for (int j = i + 1; j <= input.length(); j++) {

                    String word = input.substring(i, j);

                    // If the suffix is a valid word in the dictionary
                    if (dictionary.contains(word)) {
                        // Add the number of possibilities for the current position to the number of possibilities for the suffix
                        possibilities[j] += possibilities[i];
                    }
                }
            }
        }
    
        // Returns the number of ways to split the entire input string.
        // The result is stored in the last position of the array.
        return possibilities[input.length()];
    }
}