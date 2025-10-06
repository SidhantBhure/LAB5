import java.io.*;

public class Assignment4 {
    public static void main(String[] args) {
        String inputFile = "app.log";
        String outputFile = "log_summary.txt";

        int infoCount = 0;
        int warnCount = 0;
        int errorCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            
            while ((line = br.readLine()) != null) {
                if (line.startsWith("INFO")) {
                    infoCount++;
                } else if (line.startsWith("WARN")) {
                    warnCount++;
                } else if (line.startsWith("ERROR")) {
                    errorCount++;
                }
            }

            bw.write("Log File Summary Report\n");
            bw.write("-----------------------\n");
            bw.write("INFO messages: " + infoCount + "\n");
            bw.write("WARN messages: " + warnCount + "\n");
            bw.write("ERROR messages: " + errorCount + "\n");

            System.out.println(" Log summary generated successfully in " + outputFile);

        } catch (IOException e) {
            System.out.println(" Error processing the log file: " + e.getMessage());
        }
    }
}
