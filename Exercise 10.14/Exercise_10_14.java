// Exercise 10.14
// (The myDate class)

// Import Date, LocalDate, ZoneId
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

// Main loop
void main(String[] args) {
    
    MyDate date1 = new MyDate();
    MyDate date2 = new MyDate(34355555133101L);
    
    printDateInfo(date1);
    printDateInfo(date2);
    
}

void printDateInfo(MyDate date) {
    
    String formatString = "Date - Day: %d, Month: %d, Year: %d\n";
    
    System.out.printf(formatString, date.getDay(), date.getMonth(), date.getYear());
    
}

class MyDate {
    
    // Data fields
    private int year;
    private int month;
    private int day;
    
    // No-arg Constructor
    MyDate() {
        
        // Create date object with elapsed milliseconds
        Date currentDate = new Date();
        
        // Convert date to LocalDate
        LocalDate currentLocalDate = currentDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        
        // Convert LocalDate into year, month, day values
        year = currentLocalDate.getYear();
        month = currentLocalDate.getMonthValue() - 1;
        day = currentLocalDate.getDayOfMonth();
    }
    
    // Construct MyDate object with specified elapsed time since midnight, 
    // January 1, 1970, in milliseconds
    MyDate(long elapsedMs) {
        
        // Create date object with elapsed milliseconds
        Date currentDate = new Date();
        currentDate.setTime(elapsedMs);
        
        // Convert date to LocalDate
        LocalDate currentLocalDate = currentDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        
        // Convert LocalDate into year, month, day values
        year = currentLocalDate.getYear();
        month = currentLocalDate.getMonthValue() - 1;
        day = currentLocalDate.getDayOfMonth();
    }
    
    // Construct MyDate object with specified year, month, and day
    MyDate(int newYear, int newMonth, int newDay) {
        year = newYear;
        month = newMonth;
        day = newDay;
    }
    
    int getYear() {
        return year;
    }
    
    int getMonth() {
        return month;
    }
    
    int getDay() {
        return day;
    }
    
    void setDate(long elapsedTime) {
        
        // Create date object with elapsed milliseconds
        Date currentDate = new Date();
        currentDate.setTime(elapsedTime);
        
        // Convert date to LocalDate
        LocalDate currentLocalDate = currentDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        
        // Convert LocalDate into year, month, day values
        year = currentLocalDate.getYear();
        month = currentLocalDate.getMonthValue() - 1;
        day = currentLocalDate.getDayOfMonth();
        
    }
    
}