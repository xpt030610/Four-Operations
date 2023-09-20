import java.io.*;


public class FileIO {

    /**
     * @param FileName 读取文件的文件名
     * @return 文件字符串内容
     * @throws IOException 异常
     */
    public static String readFile(String FileName) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(FileName));//输入流
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }

        reader.close();//关闭输入流
        return builder.toString();
    }

    public static void writeFile(String outFileName, String outContent) throws IOException {

        BufferedWriter writer=new BufferedWriter(new FileWriter(outFileName));
        writer.write(outContent);
        writer.flush();
        writer.close();
    }
}
