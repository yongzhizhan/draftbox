package cn.zhanyongzhi.draftbox.java.jsoup.study;

import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BaseTest {
    @BeforeClass
    public void setUp() {

    }

    @Test
    public void testConnect() throws IOException {
        Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
        System.out.println(doc.title());

        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            System.out.println(String.format("%s\n\t%s", headline.attr("title"), headline.absUrl("href")));
        }
    }

    @Test
    public void testSession() throws Exception {
        startServer();

        Connection connection = Jsoup.connect("http://localhost:9600/foo");
        Connection.Request request = connection.request();
        request.method(Connection.Method.POST);

        //set session
        Connection.Response response = connection.execute();
        String body = response.body();
        Map cookies = response.cookies();

        assertEquals(body, "ok_foo");

        //check
        connection = Jsoup.connect("http://localhost:9600/bar");
        connection.cookies(cookies);

        request = connection.request();
        request.method(Connection.Method.POST);

        response = connection.execute();

        body = response.body();
        assertEquals(body, "ok_bar");
    }

    @Test
    public void testUserAgent() throws Exception {
        startServer();

        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";

        Connection connection = Jsoup.connect("http://localhost:9600/user-agent");
        connection.userAgent(userAgent);
        Connection.Request request = connection.request();
        request.method(Connection.Method.POST);

        //set session
        Connection.Response response = connection.execute();
        String body = response.body();

        assertEquals(body, userAgent);
    }

    @Test
    public void testProxy() throws Exception {
        startServer();
        startProxyServer();

        Connection connection = Jsoup.connect("http://localhost:9600/test");
        connection.proxy("127.0.0.1", 9601);

        Connection.Request request = connection.request();
        request.method(Connection.Method.POST);

        String body = connection.execute().body();
        assertTrue(body.startsWith("proxy server mark:"));
    }

    private void startProxyServer() throws Exception {
        Server server = new Server(9601);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addFilter(new FilterHolder(new Filter() {
            public void init(FilterConfig filterConfig) throws ServletException {

            }

            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                chain.doFilter(request, response);

                response.getOutputStream().write("proxy server mark:".getBytes());
            }

            public void destroy() {

            }
        }), "/*", EnumSet.of(DispatcherType.REQUEST));

        context.addServlet(new ServletHolder(new ProxyServlet()), "/*");

        server.start();
    }

    private void startServer() throws Exception {
        Server server = new Server(9600);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new HttpServlet() {
            protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                String key = "foo";
                String path = request.getRequestURI();

                HttpSession session = request.getSession();
                if (path.equals("/foo")) {
                    session.setAttribute(key, 1);
                    response.getWriter().print("ok_foo");
                } else if (path.equals("/bar")) {
                    Integer val = (Integer) session.getAttribute(key);
                    if (null == val) {
                        Assert.fail("cookie not valid.");
                        return;
                    }

                    Assert.assertTrue(1 == val);

                    response.getWriter().print("ok_bar");
                } else if (path.equals("/user-agent")) {
                    String userAgent = request.getHeader("User-Agent");
                    response.getWriter().print(userAgent);
                } else if (path.equals("/test")) {
                    response.getWriter().print(String.format("test host:%s, port:%d",
                            request.getRemoteHost(), request.getRemotePort()));
                }
            }
        }), "/*");

        server.start();
    }
}
