package IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipInputStream;
public class TestMappedByteBuffer {
    private static int length = 0xFFFFF;//512M
    
    private abstract static class Tester {
        private String name;
        public Tester(String name) {
            this.name = name;
        }
        public void runTest() {
            System.out.print(name + ": ");
            long start = System.currentTimeMillis();
            test();
            System.out.println(System.currentTimeMillis()-start+" ms");
        }
        public abstract void test();
    }
    private static Tester[] testers = {
        new Tester("Stream RW") {
            public void test() {
                try (FileInputStream fis = new FileInputStream(
                        "d:\\a.txt");
                        DataInputStream dis = new DataInputStream(new BufferedInputStream(fis));
                        FileOutputStream fos = new FileOutputStream(
                                "d:\\a.txt");
                        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(fos));) {
                    
                    byte b = (byte)('A'+1);
                    for(int i=0;i<length;i++) {
                        dos.writeByte(b);
                        dos.flush();
                    }                   
                    while (dis.read()!= -1) {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },
        new Tester("Mapped RW") {
            public void test() {
                try (FileChannel channel = FileChannel.open(Paths.get("f:\\b.txt"),
                        StandardOpenOption.READ, StandardOpenOption.WRITE);) {
                    MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
                    mapBuffer.clear();
                    for(int i=0;i<length;i++) {
                        mapBuffer.put((byte)('A'+4));
                    }
                    mapBuffer.flip();
                    mapBuffer.force();
                    while(mapBuffer.hasRemaining()) {
                        mapBuffer.get();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },
        new Tester("Mapped PRIVATE") {
            public void test() {
                try (FileChannel channel = FileChannel.open(Paths.get("f:\\c.txt"),
                        StandardOpenOption.DSYNC,StandardOpenOption.READ,StandardOpenOption.WRITE);) {
                    MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
                    System.out.println(mapBuffer.position());
                    mapBuffer.clear();
                    System.out.println(length);
                    
                    System.out.println(mapBuffer.position());
                    for(int i=0;i<length;i++) {
                        mapBuffer.put((byte)('A'+3));
                    }
                    System.out.println(mapBuffer.limit(mapBuffer.position()));
                    mapBuffer.flip();
                    //System.out.println(mapBuffer.limit());
                    //System.out.println(mapBuffer.position());
                    mapBuffer.force();
                    //channel.write(mapBuffer);
                    
//                    while(mapBuffer.hasRemaining()) {
//                        mapBuffer.get();
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    public static void main(String[] args) {
        for(Tester tester:testers) {
            tester.runTest();
        }
    }
}
