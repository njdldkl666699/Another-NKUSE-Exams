package IO;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



public class ZipFileTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub

        File f=new File("f:\\filedate.zip");
        FileOutputStream fo=new FileOutputStream(f);
        ZipOutputStream zot=new ZipOutputStream(fo);
        //压缩某一个目录下的所有文件
        File fzip=new File("F:\\picback\\");

        File[] needzip=fzip.listFiles();
        for(File tempf:needzip){
            if(tempf.isFile()){
            BufferedInputStream bufferedInputStream=new
                    BufferedInputStream(new FileInputStream(tempf));
            //加上相对路径，则保存目录父子关系
                ZipEntry ze=new ZipEntry("picback\\"+tempf.getName());
                zot.putNextEntry(ze);

                int data;
                while((data=bufferedInputStream.read())!=-1){
                    zot.write(data);
                }
                zot.flush();
                zot.closeEntry();
                bufferedInputStream.close();
            }

        }


        zot.close();
        fo.close();




    }
}
