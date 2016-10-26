package com.github.yongzhizhan.draftbox.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * TODO:
 *      1.支持重试
 *      2.支持https
 */

@ThreadSafe
public class HttpPool {
    private static Logger log = Logger.getLogger(HttpPool.class.getName());

    private PoolingHttpClientConnectionManager connectionManager;
    private volatile int maxPerRoute;
    private volatile RequestConfig requestConfig;

    public HttpPool(int maxPoolCount, int socketTimeoutMS, int connTimeoutMS, int maxPerRoute) {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxPoolCount);
        connectionManager.setDefaultMaxPerRoute(Math.round(maxPerRoute / 2));

        setConfig(socketTimeoutMS, connTimeoutMS, maxPerRoute);
    }

    public void setConfig(int socketTimeoutMS, int connTimeoutMS, int maxPerRoute){
        this.maxPerRoute = maxPerRoute;
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeoutMS).setConnectTimeout(connTimeoutMS).build();
    }

    private CloseableHttpClient getHttpClient(String urlStr){
        URL url;

        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            log.error(e);
            return null;
        }

        HttpHost httpHost = new HttpHost(url.getHost(), url.getPort());
        connectionManager.setMaxPerRoute(new HttpRoute(httpHost), maxPerRoute);

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

        return httpClient;
    }

    public int get(String url, StringBuilder responseStr) {
        HttpGet get = new HttpGet(url);

        get.setConfig(requestConfig);

        try (CloseableHttpClient httpClient = getHttpClient(url)){
            CloseableHttpResponse response = httpClient.execute(get);

            HttpEntity entity = response.getEntity();
            responseStr.append(EntityUtils.toString(entity, "utf-8"));
            EntityUtils.consume(entity);

            return response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            log.error(e);
        }finally {
            get.releaseConnection();
        }

        return -1;
    }

    public int post(String url, byte[] content, StringBuilder responseStr){
        HttpPost httpPost = new HttpPost(url);

        httpPost.setConfig(requestConfig);

        if(null != content){
            EntityBuilder entityBuilder = EntityBuilder.create();
            entityBuilder.setBinary(content);

            httpPost.setEntity(entityBuilder.build());
        }

        try (CloseableHttpClient httpClient = getHttpClient(url)){
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            responseStr.append(EntityUtils.toString(entity, "utf-8"));
            EntityUtils.consume(entity);

            return response.getStatusLine().getStatusCode();
        } catch (IOException e) {
            log.error(e);
        } finally {
            httpPost.releaseConnection();
        }

        return -1;
    }
}
