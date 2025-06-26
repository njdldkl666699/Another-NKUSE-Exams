package IO;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class GZIPTest {
    public static void main(String [] arg) throws IOException {
        OutputStream tar=System.out;
        tar=new FileOutputStream("d://text.txt");
        PrintWriter printWriter=new PrintWriter(tar);
       for(int i=0;i<1000;i++)
        printWriter.write("一段文字测试");
        printWriter.flush();
        System.out.println();
        tar=new FileOutputStream("d://text.gzip");
        GZIPOutputStream outputStream=new GZIPOutputStream(tar);
        printWriter=new PrintWriter(outputStream);
        for(int i=0;i<1000;i++)
            printWriter.write("一段文字测试");
        printWriter.flush();
        outputStream.close();

    }
}
