package com.sdk;

import com.util.LogFormatter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * http方法调用
 */
public class HttpRequestHelper {
    private static final Logger mainLogger = LoggerFactory.getLogger("OAPI_HTTP_LOGGER");
    private static Integer connectionRequestTimeout = 3000;
    private static Integer socketTimeOut = 3000;
    private static Integer connectTimeout = 3000;


    public static String httpPost(String url, String jsonContent) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");
        StringEntity requestEntity = new StringEntity(jsonContent, "utf-8");
        httpPost.setEntity(requestEntity);
        try {
            response = httpClient.execute(httpPost, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "response code is :"+response.getStatusLine().getStatusCode(),
                        LogFormatter.KeyValue.getNew("url", url),
                        LogFormatter.KeyValue.getNew("jsonContent", jsonContent)
                ));
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                mainLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        LogFormatter.KeyValue.getNew("url", url),
                        LogFormatter.KeyValue.getNew("jsonContent", jsonContent),
                        LogFormatter.KeyValue.getNew("return", resultStr)
                ));
                if(!resultStr.contains("\"errcode\":0")){
                    mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                            "oapi not success",
                            LogFormatter.KeyValue.getNew("url", url),
                            LogFormatter.KeyValue.getNew("jsonContent", jsonContent),
                            LogFormatter.KeyValue.getNew("return", resultStr)
                    ));
                }
                return resultStr;
            } else {
                mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "response.getEntity is null",
                        LogFormatter.KeyValue.getNew("url", url),
                        LogFormatter.KeyValue.getNew("jsonContent", jsonContent)
                ));
                return null;
            }
        } catch (Exception e) {
            mainLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "http post json failed"+e.toString(),
                    LogFormatter.KeyValue.getNew("url", url),
                    LogFormatter.KeyValue.getNew("jsonContent", jsonContent)
            ),e);
            return null;
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                mainLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "close http connection failed"+e.toString(),
                        LogFormatter.KeyValue.getNew("url", url),
                        LogFormatter.KeyValue.getNew("jsonContent", jsonContent)
                ),e);
            }
        }
    }

    public static String httpGet(String url){
        //单位毫秒
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout).setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeOut).build();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "response code is :" + response.getStatusLine().getStatusCode(),
                        LogFormatter.KeyValue.getNew("url", url)
                ));
                return null;
            } else {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                mainLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                        LogFormatter.KeyValue.getNew("url", url),
                        LogFormatter.KeyValue.getNew("return", result)
                ));
                if(!result.contains("\"errcode\":0")){
                    mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                            "oapi not success",
                            LogFormatter.KeyValue.getNew("url", url),
                            LogFormatter.KeyValue.getNew("return", result)
                    ));
                }
                return result;
            }
        } catch (Exception e) {
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "http getfailed" + e.toString(),
                    LogFormatter.KeyValue.getNew("url", url)
            ), e);
        } finally {
            IOUtils.closeQuietly(response);
        }
        return null;
    }
}
