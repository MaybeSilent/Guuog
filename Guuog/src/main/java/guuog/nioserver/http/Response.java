package guuog.nioserver.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import guuog.nioserver.connector.Channel;

public class Response implements HttpServletResponse {

    public static final String HTTPOK = "HTTP/1.1 200 OK";
    public static final String NEWLINE = "\r\n";
    public static final String NOTFOUND = "HTTP/1.1 404 Not Find";
    public static final String SERVERERROR = "HTTP/1.1 500 Internal Server Error";
    public static final String CONTENTLENGTH = "Content-Length";
    public static final String CONTENTTYPE = "Content-Type";

    private Channel channel;
    private HashMap<String, String> header = new HashMap<>();
    private String reskind ;


    public void setOK(){
        reskind = HTTPOK;
    }
    public void set404(){
        reskind = NOTFOUND;
    }
    public void set500(){
        reskind = SERVERERROR;
    }

    public byte[] getbyte(){
        StringBuilder strb = new StringBuilder(256);
        strb.append(reskind).append(NEWLINE);
        Iterator<String> iter = header.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            strb.append(key).append(": ").append(header.get(key)).append(NEWLINE);
        }
        strb.append(NEWLINE);
        return strb.toString().getBytes();
    }

    @Override
    public void addHeader(String name, String value) {
        header.put(name, value);
    }

    @Override
    public void addIntHeader(String name, int value) {
        
    }
    /**
     * 实现httpServletResponse接口的方法，自动生成相应的getter与setter方法
     */
    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public void addDateHeader(String name, long date) {

    }

    @Override
    public boolean containsHeader(String name) {
        return false;
    }

    @Override
    public String encodeRedirectURL(String url) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String url) {
        return null;
    }

    @Override
    public String encodeURL(String url) {
        return null;
    }

    @Override
    public String encodeUrl(String url) {
        return null;
    }

    @Override
    public void sendError(int sc) throws IOException {

    }

    @Override
    public void sendError(int sc, String msg) throws IOException {

    }

    @Override
    public void sendRedirect(String location) throws IOException {

    }

    @Override
    public void setDateHeader(String name, long date) {

    }

    @Override
    public void setHeader(String name, String value) {

    }

    @Override
    public void setIntHeader(String name, int value) {

    }

    @Override
    public void setStatus(int sc) {

    }

    @Override
    public void setStatus(int sc, String sm) {

    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setLocale(Locale loc) {

    }

}