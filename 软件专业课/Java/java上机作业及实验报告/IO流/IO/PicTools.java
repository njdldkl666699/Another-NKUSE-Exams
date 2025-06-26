package IO;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class PicTools {
	private static Logger log = Logger.getLogger("PicTools");
	private static String piclib="F:\\picback\\";
	public static void main(String[] args) throws IOException {
		Path startingDir = Paths.get("F:\\picexample");
		List<Path> result = new LinkedList<Path>();
		Files.walkFileTree(startingDir, new FindJavaVisitor(result));
	}

	private static class FindJavaVisitor extends SimpleFileVisitor<Path>{
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
			String filePath = file.toFile().getAbsolutePath();
			int width = 200;
			String newPath = file.toFile().getName();
			newPath=newPath.replaceAll("\\.", "_1.");
			ImageUtil.zoom( width, file.toFile(), piclib+newPath);
			result.add(file.getFileName());
			System.out.println(file.getFileName());
			return FileVisitResult.CONTINUE;
		}
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
				throws IOException
		{
			System.out.println(dir.getFileName());
			if(dir.toFile().getPath().indexOf("新建")!=-1)
				return FileVisitResult.TERMINATE;
			else
				return FileVisitResult.CONTINUE;
		}
		private List<Path> result;
		public FindJavaVisitor(List<Path> result){
			this.result = result;
		}


	}

}
