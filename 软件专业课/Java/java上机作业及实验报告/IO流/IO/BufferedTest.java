package IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedTest {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Long starttime=System.currentTimeMillis();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("JVM runtime"+(System.currentTimeMillis()-starttime));
			}
		});
		File f=new File("d:\\hzbxback_test.war");
		File f1=new File("d:\\hzbxback_test_copy.war");
		FileOutputStream fo=new FileOutputStream(f1);
		BufferedOutputStream bo=new BufferedOutputStream(fo,1024*256);
		FileInputStream fi=new FileInputStream(f);
		BufferedInputStream bi=new BufferedInputStream(fi,1024*256);
		int size;
		 byte[] buf = new byte[256];
		 int position=0;

//		while((size = bi.read(buf))!= -1) {  //带有buffer复制
//			position+=size;
//			bo.write(buf, 0, size);
//		}
		int data;
		while((data = bi.read())!=-1) {  //buffered复制
			bo.write(data);
		}
//		int data;
//		while((data = fi.read())!=-1) {  //file复制
//			fo.write(data);
//		}
//		while((size = fi.read(buf))!= -1) {  //无buffer，自设buffer array复制
//			position+=size;
//			fo.write(buf, 0, size);
//		}

		
	}
}
