package guuog.nioserver.classloaders;

import java.io.File;
import java.lang.reflect.Method;

import guuog.nioserver.content.FileTobyte;
import guuog.nioserver.content.ReadFile;

public class Servletclassloader extends ClassLoader {
    private String path = ReadFile.resourcePath + File.separator + "WEB-INF" + File.separator + "classes"
            + File.separator;

    private final String fileType = ".class";

    public Servletclassloader() {
        super();
    }

    public Servletclassloader(ClassLoader parent) {
        super(parent);
    }
    
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String filename = name.replace('.', '\\');
        File file = new File(path + filename + fileType);
        byte[] classbyte = null;
        System.out.println(path + name + fileType);
        if (file.exists() && file.isFile()) {
            try {
                classbyte = FileTobyte.transferFileToBytes(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (classbyte != null) {
                return defineClass(name, classbyte, 0, classbyte.length);
            } else {
                throw new ClassNotFoundException("读取文件出错");
            }
        } else {
            throw new ClassNotFoundException("类文件未找到");
        }
    }

    public static void main(String[] args) {
        Servletclassloader serClassLoader = new Servletclassloader();
        Class<?> c = null;
        try {
            c = serClassLoader.loadClass("main.simpleServlet");
            Object obj = c.newInstance();
            Method[] methods = c.getMethods();
            for(Method method : methods){
                System.out.println(method.getName());
            }

            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        
    }
}