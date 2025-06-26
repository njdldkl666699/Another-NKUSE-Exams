package IO;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ZIpTest {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String src = sc.nextLine();
        String[] arr = src.split("\\\\");
        String dest = "D:\\test"+ File.separatorChar+ arr[arr.length-1]+".zip";
        System.out.println(dest);
        ZipCopyUtil zipCopyUtil =new ZipCopyUtil(src,dest,arr[arr.length-1]);
        ZIpThread zt = new ZIpThread(zipCopyUtil);
        ScannerThread scannerThread=new ScannerThread();
        scannerThread.start();
        zt.start();
        while(zt.isAlive()&&scannerThread.isAlive()){}
    }
}
