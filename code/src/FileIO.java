import java.io.*;


public class FileIO {
    public static String readFile(String FileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FileName));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        reader.close();
        return builder.toString();
    }

    public static void writeFile(String outFileName, String outContent) throws IOException {
        BufferedWriter writer=new BufferedWriter(new FileWriter(outFileName));
        writer.write(outContent);
        writer.flush();
        writer.close();
    }
}
