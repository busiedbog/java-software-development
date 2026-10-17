// Exercise 20.11 (Match grouping symbols)
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Stack;

/** 
 *
 * NOTE: Make sure to give a path without quotation marks.
 * 
 * e.g. C:\Users\USER\OneDrive\Documents\GitHub\java-software-development\Exercise_6_9_BROKEN.java
 *
 **/

class Exercise_20_11 {
    void main() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter file path: ");
        String codePath = reader.nextLine(); 
        
        String code = getSourceCode(codePath);
        
        // Comparator for strings
        if (!"".equals(code)) {
            boolean formattedCorrectly = groupingSymbolMatching(code);
            
            String boolTxt;
            if (formattedCorrectly) {
                boolTxt = " is ";
            } else {
                boolTxt = " is NOT ";
            }
            
            System.out.println("The code" + boolTxt + "formatted correctly!");
        }
    }
    
    public String getSourceCode(String path) {
        
        try {
            Path codePath = Path.of(path);
            String code = Files.readString(codePath);
            return code;
        } catch (InvalidPathException e) {
            System.out.println("Invalid path, please try again.");
        } catch (IOException e) {
            System.out.println("IOException thrown");
        }

        return "";
    }   

    public boolean groupingSymbolMatching(String code) {
        // Initialize stack for bracket storage
        Stack<Character> openerStack = new Stack<>();
        
        // Iterate throgh the code
        for (int i = 0; i < code.length(); i++) {
            // Get current character
            Character currentChar = code.charAt(i);
            
            if (currentChar == '(' || currentChar == '{' || currentChar == '[') {
                // Push opener to stack
                openerStack.push(currentChar);
            } 
            else if (currentChar == ')' || currentChar == '}' || currentChar == ']') {
               
                // Check if stack is empty. If so, return false
                if (openerStack.empty()) {
                    return false;
                }
                
                // Get top of stack (most recent opener)
                Character opener = openerStack.pop();
                
                // If the symbols don't match, return false
                if (!((opener == '{' && currentChar == '}') || 
                    (opener == '(' && currentChar == ')') || 
                    (opener == '[' && currentChar == ']') ))
                {
                    return false;
                }
            }
        }
        
        // If it goes through without error, that means grouping is correct
        return openerStack.isEmpty();
    }
}

