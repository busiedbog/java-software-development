// Exercise 6.31
// (Financial: credit card number validation)

import java.util.*;

public static void main(String[] args) {
    Scanner scannerObject = new Scanner(System.in);
    System.out.print("Enter card number: ");
 
    long cardNumber = scannerObject.nextLong();
    
    String validString;
    
    if (isValid(cardNumber)) {
        validString = "is valid.";
    } else {
        validString = "is NOT valid.";
    }
    
    System.out.println("This card " + validString);
}

// check if card number is valid
public static boolean isValid(long number) { 
    
    // if size is not between 13 and 16 digits, then false
    int numberSize = getSize(number);
    if (!(13 <= numberSize && numberSize <= 16)) {
        System.out.println("Number is outside of digit range: " + numberSize);
        return false;
    }
    
    // check if prefix is valid
    if (!prefixMatched(number)){
        System.out.println("Invalid Prefix");
        return false;
    }
    
    int evenSum = sumOfDoubleEvenPlace(number);
    int oddSum = sumOfOddPlace(number);
    
    int totalSum = evenSum + oddSum;
    
    // check if sum mod 10 equal to 0
    boolean valid = (totalSum % 10 == 0);
    
    if (!valid) {
        System.out.println("Even Sum: " + evenSum);
        System.out.println("Odd Sum: " + oddSum);
        System.out.println("Sum: " + totalSum);
    }   
    
    return valid;
}

// add together all the numbers in even places
public static int sumOfDoubleEvenPlace(long number) {
    
    // create int for return val
    int sum = 0;
    
    // convert long to string for easier digit finding
    String numString = String.valueOf(number);
    
    // get every even digit 
    for (int i = numString.length() - 2; i >= 0; i -= 2 ) {
        int digit = numString.charAt(i) - '0';
        sum += getDigit(digit * 2);
    }
    
    return sum;
}

// return number if it is single digit, otherwise return sum of two digits
public static int getDigit(int number) {
    
    String numberString = String.valueOf(number);
    int numberLength = numberString.length();
    
    switch(numberLength) {
        case 1 -> {
            return number;
        }
        case 2 -> {
            
            int finalNumber = 0;
            for (int i = 0; i < 2; i++) {
                finalNumber += numberString.charAt(i) - '0';
            }
            return finalNumber;
        }
        default -> {
            return -1;
        }
    }
}

// sum of odd digits
public static int sumOfOddPlace(long number) {
    // create int for return val
    int sum = 0;
    
    // convert long to string for easier digit finding
    String numString = String.valueOf(number);
    
    // get every odd digit 
    for (int i = numString.length() - 1; i >= 0; i -=2 ) {
        sum += numString.charAt(i) - '0';
    }
    
    return sum;
}

// return true if prefix is valid
public static boolean prefixMatched(long number) {
    // get first two digits of number
    long prefixNum = getPrefix(number, 2);
    String prefixString = String.valueOf(prefixNum);
    
    switch (prefixString.charAt(0)) {
        case '4' -> {
            // Visa prefix
            return true;
        }
        case '5' -> {
            // Master prefix
            return true;
        }
        case '6' -> {
            // Discover prefix
            return true;
        }
        case '3' -> {
            // 37 is American Express prefix
            return (prefixString.charAt(1) == '7');
        }
        default -> {
            return false;
        }
    }
    
}

// return the number of digits in d
public static int getSize(long d) {
    
    // convert long to string for length method
    String dString = String.valueOf(d);
    
    // return string length
    return dString.length();
    
}

public static long getPrefix(long number, int k) {
    // convert long to string
    String numString = String.valueOf(number);
    
    // intitialize final number
    String finalNum = "";
    
    // if number of digits in long is less than k, return number
    if (numString.length() < k) {
        return number;
    } else {
        // get the prefix and return
        for (int i = 0; i < k; i++) {
            finalNum += numString.charAt(i);
        }
        return Long.parseLong(finalNum);
    }
    
}