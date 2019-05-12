package guuog.nioserver.content;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class FileToByte {

    public HashMap<String, byte[]> processSets(HashSet<File> fileSets) {
        Iterator<File> iter = fileSets.iterator();
        HashMap<String, byte[]> maps = new HashMap<>();
        while (iter.hasNext()) {
            File file = iter.next();
            try {
                byte[] bytes = transferFileToBytes(file);
                String name = '/' + file.getName();
                maps.put(name, bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return maps;
    }

    public static byte[] transferFileToBytes(File file) throws IOException {
        long length = 0;
        if (file.exists() && file.isFile()) {
            length = file.length();
        }
        /*
         * 相关内容的文件考虑为静态页面，所以设置为 只读模式
         */
        RandomAccessFile randomFile = null;
        try {
            randomFile = new RandomAccessFile(file, "r");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (length == 0 || randomFile == null)
            return new byte[0];

        ByteBuffer buffer = ByteBuffer.allocate((int) length);
        FileChannel channel = randomFile.getChannel();

        int byteNum = channel.read(buffer);
        assert (byteNum == length);
        return buffer.array();

    }

}