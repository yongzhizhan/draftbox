package cn.zhanyongzhi.draftbox.java.jsoup.study;

import org.eclipse.jetty.server.Server;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static org.testng.Assert.assertEquals;

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
                }
            }
        }), "/*");

        server.start();
    }
}
