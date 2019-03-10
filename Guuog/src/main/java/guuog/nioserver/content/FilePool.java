package guuog.nioserver.content;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class FilePool {
    private HashMap<String, String> urimap = null;
    private HashMap<String, byte[]> files = null;

    public FilePool() {
        ReadFile reader = new ReadFile();
        HashSet<File> sets = null;
        try {
            sets = reader.getFiles();
            urimap = reader.getxmlinfo();

        } catch (Exception e) {
            e.printStackTrace();
        }

        FileTobyte fbyte = new FileTobyte();
        files = fbyte.processSets(sets);

    }

    public boolean containsFile(String name){
        return files.containsKey(name);
    }

    public byte[] getFileByte(String name){
        return files.get(name);
    }

    public boolean containsClass(String name){
        return urimap.containsKey(name);
    }

    public String getClassName(String name){
        return urimap.get(name);
    }

    public static void main(String[] args) {
        FilePool filePool = new FilePool();
        System.out.println( filePool.containsFile("/index.html") );
    }

}