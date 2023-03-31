import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

public class CSVWriter {

    public static void createCSV(int year, ArrayList<LocalDate> paymentDates, ArrayList<LocalDate> notificationDates) throws FileNotFoundException {
            PrintWriter pw = new PrintWriter(new File(year + ".csv"));
            StringBuilder sb = new StringBuilder();

            sb.append("Payment dates,Notification dates\r\n");
            for (int i = 0; i < 11; i++) {
                sb.append(paymentDates.get(i)).append(",").append(notificationDates.get(i)).append("\r\n");
            }

            pw.write(sb.toString());
            pw.close();
            System.out.println("CSV file created successfully!");
    }
}
