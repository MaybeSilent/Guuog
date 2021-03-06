package guuog.nioserver.http;

import guuog.nioserver.logging.ReadLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Request implements HttpServletRequest {
    private RequestStream reqStream;
    private HashMap<String, String> headerContent = new HashMap<>();
    private String kind = null;
    private String uri = null;
    private String httpKind = null;
    private static String regex=".*[a-zA-Z]+.*";

    /**
     * 避免处理的请求中没有相应的
     */
    public boolean judgeContainsStr(String cardNum) {
    	
	    Matcher m=Pattern.compile(regex).matcher(cardNum);
	    return m.matches();
    }

    public void parse() {
        String line = null;
        ReadLogger.getLogger().info("Begin Parsing A Request");
        int count = 0;
        while ((line = reqStream.readLine()) != null) {
            if (count == 0 && judgeContainsStr(line)) {
                String[] strs = line.split(" ");
                assert (strs.length == 3);
                kind = strs[0];
                uri = strs[1];
                httpKind = strs[2];
                ReadLogger.getLogger().info("Kind: " + kind + " Uri: " + uri + " HttpKind: " + httpKind);
            } else if(judgeContainsStr(line)) {
                String[] strs = line.split(": ");
                assert (strs.length == 2);
                headerContent.put(strs[0], strs[1]);
                ReadLogger.getLogger().info(strs[0] + " : " + strs[1]);
            }
            count ++;
        }
    }

    public String getUri(){
        return uri;
    }

    @Override
    public String getMethod() {
        return kind;
    }

    public Request(RequestStream stream) {
        this.reqStream = stream;
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return null;
    }

    @Override
    public long getDateHeader(String name) {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Enumeration getHeaderNames() {
        return null;
    }

    @Override
    public Enumeration getHeaders(String name) {
        return null;
    }

    @Override
    public int getIntHeader(String name) {
        return 0;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return null;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return null;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration getLocales() {
        return null;
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public Map getParameterMap() {
        return null;
    }

    @Override
    public Enumeration getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRealPath(String path) {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public void setAttribute(String name, Object o) {

    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

    }

}