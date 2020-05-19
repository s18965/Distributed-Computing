package zad1;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                FileChannel fcout = new FileOutputStream(resultFileName,true).getChannel();
                FileChannel fcin = new FileInputStream(file.toFile()).getChannel();
                ByteBuffer buf = ByteBuffer.allocate((int)fcin.size());
                fcin.read(buf);
                Charset inCharset  = Charset.forName("Cp1250"), outCharset = StandardCharsets.UTF_8;
                buf.flip();
                CharBuffer cbuf = inCharset.decode(buf);
                buf=outCharset.encode(cbuf);
                if(file.toString().toLowerCase().endsWith(".txt")){
                    fcout.write(buf);
                }
                fcin.close();
                fcout.close();
                return super.visitFile(file,attrs);
        }
    };
        try {
            Files.walkFileTree(Paths.get(dirName), fileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}