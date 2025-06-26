package IO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

class FileWatcherThread extends Thread{
	FileWatcherThread()
	{
		this.setDaemon(true);
	}
	@Override
	public void run() {
		try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
			Path dir = Paths.get("D:\\Test");
			WatchKey key = dir.register(watcher,StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE);
			for (;;) {
			key = watcher.take();
			for (WatchEvent<?> event : key.pollEvents()) {
			System.out.println(event.kind().toString()+event.context());
		
			}
			boolean valid = key.reset();
			if (!valid) {break;}
			}
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
public class FileWatcher {

	public static void main(String [] arg) throws InterruptedException, FileNotFoundException {
		FileWatcherThread fw=new FileWatcherThread();
		fw.start();
		Thread.sleep(1000*60);
		Path dir = Paths.get("D:\\Test");
		FileOutputStream fo=new FileOutputStream("D:\\Test");
		
		
	}
}
