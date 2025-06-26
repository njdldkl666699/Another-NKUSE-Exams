package IO;

import java.io.*;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCopyUtil {
    public File src;
    public File dest;
    public int len=0;
    public int complished;
    public String zipName;
    public Thread thread;
    ZipCopyUtil(String src, String dest,String zipName) {
        this.src = new File(src);
        this.dest = new File(dest);
        this.zipName = zipName;
        findLength(this.src);
        complished = 0;
    }
    void findLength(File src)
    {
        if(src.isDirectory())
        {
            File[] files = src.listFiles();
            for (File file : files) {
                if(file.isDirectory())
                    findLength(file);
                else
                    len++;
            }
        }
    }
    public void startToZip(Thread thread) throws IOException, InterruptedException {
        //ToZip(src,dest,zipName,new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(dest), 4096)));
        this.thread = thread;
        ToZip(src,dest,zipName,new ZipOutputStream(new FileOutputStream(dest)));
    }
    public void ToZip(File src, File dest,String zipName,ZipOutputStream zos) throws IOException, InterruptedException {

        if(src.isDirectory()) {
            File[] files = src.listFiles();
            for(File file : files) {
                ZipEntry ze = new ZipEntry(zipName+File.separatorChar+file.getName());
                System.out.println(ze);
                zos.putNextEntry(ze);
                if(file.isDirectory()) {
                    ToZip(new File(src.getAbsolutePath()+File.separatorChar+file.getName()), dest,zipName+File.separatorChar+file.getName(),zos);
                }
                else {
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src.getAbsolutePath()+File.separatorChar+file.getName()));
                    //byte[] buffer = new byte[1024];
                    int length;
                    /*while ((length = bis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }*/
                    while ((length = bis.read()) != -1) {
                        zos.write(length);
                    }
                    zos.flush();
                    bis.close();
                    //thread.sleep(500);
                    complished++;
                    System.out.println("文件拷贝进度："+complished+"/"+len);
                }
            }
        }
        else {
            ZipEntry ze = new ZipEntry(zipName+File.separatorChar+src.getName());
            zos.putNextEntry(ze);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src.getAbsolutePath()));
            //byte[] buffer = new byte[1024];
            int length;
            /*while ((length = bis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }*/
            while ((length = bis.read()) != -1) {
                zos.write(length);
            }
            zos.flush();
            bis.close();
            //thread.sleep(500);
            complished++;
            System.out.println("文件拷贝进度："+complished+"/"+len);
        }
    }
}
