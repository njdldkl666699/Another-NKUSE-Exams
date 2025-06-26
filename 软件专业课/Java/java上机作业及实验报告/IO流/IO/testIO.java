package IO;

import java.io.*;

public class testIO {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileOutputStream fo = new FileOutputStream("D:\\code\\output.txt");
        ObjectOutputStream dop = new ObjectOutputStream(fo);
        ObjectTest ot = new ObjectTest();
        ot.d=  1.0;
        dop.writeObject(ot);
        ot.d = 2.0;
        dop.writeObject(ot);
        fo.flush();
        fo.close();
        ObjectInputStream oi =
                new ObjectInputStream(new FileInputStream("D:\\code\\output.txt"));
        ot = (ObjectTest) oi.readObject();
        System.out.println(ot.d);
        oi.close();
    }
}
