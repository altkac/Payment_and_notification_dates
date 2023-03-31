import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.DayOfWeek;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // Dates that are not working days
        ArrayList<String> unwantedDates = new ArrayList<>();
        // Dates when payments should be done
        ArrayList<LocalDate> paymentDates = new ArrayList<>();
        // Dates for sending notifications
        ArrayList<LocalDate> notificationDates = new ArrayList<>();

        JSONReader.createDatesArrayList(unwantedDates);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter year between 2023 and 2027: ");
        int year = sc.nextInt();
        while(year < 2023 || year > 2027){
            System.out.println("Invalid year. Try again: ");
            year = sc.nextInt();
        }

        paymentAndNotificationDates(year, unwantedDates, paymentDates, notificationDates);
        CSVWriter.createCSV(year, paymentDates, notificationDates);

        System.out.println("+---------------+--------------------+");
        System.out.println("| Payment dates:| Notification dates:|");
        System.out.println("|---------------+--------------------|");
        for (int i = 0; i < 12; i++) {
           System.out.println("|  " + paymentDates.get(i) + "\t|\t  " + notificationDates.get(i) + "\t |");
        }
        System.out.println("+---------------+--------------------+");
    }

    public static void paymentAndNotificationDates(int year, ArrayList<String> unwantedDates,
                                                   ArrayList<LocalDate> paymentDates,
                                                   ArrayList<LocalDate> notificationDates) {
        // Day of the month to make payment
        int dayOfMonth = 10;

        for (int month = 1; month <= 12; month++) {
            LocalDate date = LocalDate.of(year, month, dayOfMonth);
            // While the date is a weekend or a public holiday
            while (isWeekend(date) || containsDate(unwantedDates, date)){
                dayOfMonth -= 1;
                date = date.withDayOfMonth(dayOfMonth);
            }
            dayOfMonth = 10;
            paymentDates.add(date);
        }

        for (int month = 1; month <= 12; month++) {
            // The number of days that should be deducted from the date of notification
            int minusDays = 3;
            // dayOfMonth is now represents day of payment
            dayOfMonth = paymentDates.get(month-1).getDayOfMonth();
            LocalDate date = LocalDate.of(year, month, dayOfMonth);

            // While not all the days were not deducted from a date or while date is a weekend/public holiday
            while(minusDays > 0 || (isWeekend(date) || containsDate(unwantedDates, date))){
                // If the date is a working day
                if(!(isWeekend(date) || containsDate(unwantedDates, date))) {
                    dayOfMonth--;
                    minusDays--;
                    date = date.withDayOfMonth(dayOfMonth);
               }else{
                   dayOfMonth--;
                   date = date.withDayOfMonth(dayOfMonth);
               }
            }
            notificationDates.add(date);
        }
    }

    // Checks if the given date is a public holiday (unwantedDates array list contains this date)
    public static boolean containsDate(ArrayList<String> unwantedDates, LocalDate date){
        for (String dateString : unwantedDates) {
            // Parse the String object to a LocalDate object
            LocalDate parsedDate = LocalDate.parse(dateString);
            if (parsedDate.equals(date)) {
                return true;
            }
        }
        return false;
    }

    // Checks if the given date is a weekend or not
    public static boolean isWeekend(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY){
            return true;
        }
        return false;
    }
}