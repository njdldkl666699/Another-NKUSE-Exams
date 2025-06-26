package IO;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class ByteToChar {
    public static void main(String [] arg) throws IOException {

                try {
                    // 使用UTF-8编码创建FileWriter
                    OutputStreamWriter writer =  new java.io.OutputStreamWriter(new java.io.FileOutputStream("D://example.txt"), "UTF-8");
                    writer.write("这是一些文本ABC。");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


