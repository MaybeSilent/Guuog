package guuog.nioserver.content;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadFile {

    /**
     * System.getProperty("user.dir")返回当前工作目录 在作者本机电脑上显示为 e:\workfile\develop\Guuog
     * resourcePath表示当前目录
     */
    public static String resourcePath = System.getProperty("user.dir") + File.separator + "Guuog" + File.separator
            + "webroot";

    public static String configFile = resourcePath + File.separator + "WEB-INF" + File.separator + "web.xml";

    /**
     * @return HashSet<File>包含了resource文件夹下所有文件，不包括相应的目录，所有静态文件目录位置默认在此
     */
    public HashSet<File> getFiles() throws Exception {
        HashSet<File> webContent = new HashSet<>();
        File file = new File(resourcePath);
        if (!file.exists() || file.isFile()) {
            throw new Exception("资源文件夹不存在");
        } else {
            File[] webs = file.listFiles();
            for (File webfile : webs) {
                if (webfile.isDirectory())
                    continue;
                else {
                    webContent.add(webfile);
                }
            }
        }

        return webContent;
    }

    /**
     * 对相应的配置文件进行解析
     * 
     * @return
     */
    public HashMap<String, String> getxmlinfo() throws Exception {
        HashMap<String, String> classmap = new HashMap<>();
        HashMap<String, String> urimap = new HashMap<>();

        System.out.println(configFile);
        File file = new File(configFile);
        if (file.exists()) {
            // 打开web.xml , 打开方式为整体进行打开
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList webapp = document.getElementsByTagName("web-app");
            NodeList servlets = webapp.item(0).getChildNodes();
            for (int i = 0; i < servlets.getLength(); i++) {
                // 主要是对servlet和servlet-mapping节点的处理
                Node nowNode = servlets.item(i);
                if (nowNode.hasChildNodes()) {
                    if (nowNode.getNodeName().equals("servlet")) {

                        getContent(nowNode, "servlet-name", classmap);

                    } else if (nowNode.getNodeName().equals("servlet-mapping")) {
                        NodeList classServlet = nowNode.getChildNodes();
                        for (int j = 0; j < classServlet.getLength(); j++) {
                            if (classServlet.item(j).getNodeName().equals("servlet-name")) {
                                String name = classServlet.item(j).getTextContent();
                                if (j + 2 < classServlet.getLength() && classmap.containsKey(name)) {
                                    urimap.put(classServlet.item(j + 2).getTextContent(), classmap.get(name));
                                }
                            }
                        }
                    }
                }
            }

        } else {
            throw new Exception("web.xml配置文件不存在或者路径不正确：正确路径为WebRoot\\WEB-INF\\web.xml");
        }

        return urimap;
    }

    private void getContent(Node listNode, String nodename, HashMap<String, String> contentmap) {
        NodeList classServlet = listNode.getChildNodes();
        for (int j = 0; j < classServlet.getLength(); j++) {
            if (classServlet.item(j).getNodeName().equals(nodename)) {
                if (j + 2 < classServlet.getLength()) {
                    contentmap.put(classServlet.item(j).getTextContent(), classServlet.item(j + 2).getTextContent());
                }
            }
        }
    }

    public static void main(String[] args) {
        ReadFile read = new ReadFile();
        try {
            HashMap<String,String> maps  = read.getxmlinfo();
            HashSet<File> sets = read.getFiles();
            for(File file : sets){
                System.out.println(file.getName());
            }

            Iterator<String> iter = maps.keySet().iterator();
            while(iter.hasNext()){
                String key = iter.next();
                System.out.println(key);
                System.out.println(maps.get(key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}