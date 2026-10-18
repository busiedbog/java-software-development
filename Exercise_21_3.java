// Exercise 21.3
// (Count the keywords in Java source code)
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;

/** 
 *
 * NOTE: Make sure to give a path without quotation marks.
 * Also, this program is taken from my code in Exercise 20.11 because the intents are similar
 * e.g. C:\Users\USER\OneDrive\Documents\GitHub\java-software-development\Exercise_6_9_BROKEN.java
 *
 **/

class Exercise_21_3 {
    
    // Static and final hash set is constant and doesn't need to change
        private static final Set<String> KEYWORDS = Set.of("abstract", "assert",
                "boolean", "break", "byte", "case", "catch", "char", "class", 
                "const", "continue", "default", "do", "double", "else", "enum", 
                "extends", "false", "final", "finally", "float", "for", "goto", 
                "if", "implements", "import", "instanceof", "int", "interface", 
                "long", "native", "new", "null", "package", "private", 
                "protected", "public", "return", "short", "static", "strictfp",
                "super", "switch", "synchronized", "this", "throw", "throws",
                "transient", "true", "try", "void", "volatile", "while", "_");
    
    void main() {
        // Scanner for file path
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter file path: ");
        String codePath = reader.nextLine(); 
        
        String code = getSourceCode(codePath);
        
        // Comparator for strings
        if (!"".equals(code)) {
            System.out.println("The program is\n" + code);
            
            int numberOfKeywords = countKeywords(code);
            
            System.out.println("The number of keywords in the program is " + numberOfKeywords);
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

    public int countKeywords(String code) {
        
        int numberOfKeywords = 0;
        
        // remove anything inside double quotes
        code = code.replaceAll("\"([^\"\\\\]|\\\\.)*\"", "");
        
        // remove char "literals"
        code = code.replaceAll("'([^'\\\\]|\\\\.)'", "");
        
        // remove multi-line comments
        code = code.replaceAll("/\\*[\\s\\S]*?\\*/", "");
        
        // remove single-line comments
        code = code.replaceAll("//.*", "");
        
        // Split into words and iterate through them
        String[] codeArray = code.split("[ ]");
        
        for (String codeWord : codeArray) {
            if (KEYWORDS.contains(codeWord)) {
                numberOfKeywords++;
            }
        }
            
        return numberOfKeywords;
       }
 
}