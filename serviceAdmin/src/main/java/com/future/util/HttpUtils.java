package com.future.util;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: HttpUtils.java
 * @Description:  http request 帮助类
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /** 默认编码方式 -UTF8 */
    private static final String DEFAULT_ENCODE = "utf-8";
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;



    /**
     * GET请求, 结果以字符串形式返回.
     *
     * @param url
     *            请求地址
     * @return 内容字符串
     */
    public static String getUrlAsString(String url) throws Exception {
        return getUrlAsString(url, null, DEFAULT_ENCODE);
    }

    /**
     * GET请求, 结果以字符串形式返回.
     *
     * @param url
     *            请求地址
     * @param params
     *            请求参数
     * @return 内容字符串
     */
    public static String getUrlAsString(String url, Map<String, String> params)
            throws Exception {
        return getUrlAsString(url, params, DEFAULT_ENCODE);
    }

    /**
     * GET请求, 结果以字符串形式返回.
     *
     * @param url
     *            请求地址
     * @param params
     *            请求参数
     * @param encode
     *            编码方式
     * @return 内容字符串
     */
    public static String getUrlAsString(String url, Map<String, String> params,
                                        String encode) throws Exception {
        // 开始时间
        long t1 = System.currentTimeMillis();
        // 获得HttpGet对象
        HttpGet httpGet = getHttpGet(url, params, encode);

        // 发送请求
        String result = executeHttpRequest(httpGet, null);
        // 结束时间
        long t2 = System.currentTimeMillis();

        // 返回结果
        return result;
    }

    /**
     * POST请求, 结果以字符串形式返回.
     *
     * @param url
     *            请求地址
     * @return 内容字符串
     */
    public static String postUrlAsString(String url) throws Exception {
        return postUrlAsString(url, null, null, null);
    }

    /**
     * POST请求, 结果以字符串形式返回.
     *
     * @param url
     *            请求地址
     * @param params
     *            请求参数
     * @return 内容字符串
     */
    public static String postUrlAsString(String url, Map<String, String> params)
            throws Exception {
        return postUrlAsString(url, params, null, null);
    }

    /**
     * POST请求, 结果以字符串形式返回.
     *
     * @param url
     *            请求地址
     * @param params
     *            请求参数
     * @param reqHeader
     *            请求头内容
     * @return 内容字符串
     * @throws Exception
     */
    public static String postUrlAsString(String url,
                                         Map<String, String> params, Map<String, String> reqHeader)
            throws Exception {
        return postUrlAsString(url, params, reqHeader, null);
    }

    /**
     * POST请求, 结果以字符串形式返回.
     *
     * @param url
     *            请求地址
     * @param params
     *            请求参数
     * @param reqHeader
     *            请求头内容
     * @param encode
     *            编码方式
     * @return 内容字符串
     * @throws Exception
     */
    public static String postUrlAsString(String url,
                                         Map<String, String> params, Map<String, String> reqHeader,
                                         String encode) throws Exception {
        // 开始时间
        long t1 = System.currentTimeMillis();
        // 获得HttpPost对象
        HttpPost httpPost = getHttpPost(url, params, encode);
        // 发送请求
        String result = executeHttpRequest(httpPost, reqHeader);
        // 结束时间
        long t2 = System.currentTimeMillis();

        // 返回结果
        return result;
    }

    /**
     * 获得HttpGet对象
     *
     * @param url
     *            请求地址
     * @param params
     *            请求参数
     * @param encode
     *            编码方式
     * @return HttpGet对象
     */
    private static HttpGet getHttpGet(String url, Map<String, String> params,
                                      String encode) {
        StringBuffer buf = new StringBuffer(url);
        if (params != null) {
            // 地址增加?或者&
            String flag = (url.indexOf('?') == -1) ? "?" : "&";
            // 添加参数
            for (String name : params.keySet()) {
                buf.append(flag);
                buf.append(name);
                buf.append("=");
                try {
                    String param = params.get(name);
                    if (param == null) {
                        param = "";
                    }
                    buf.append(URLEncoder.encode(param, encode));
                } catch (UnsupportedEncodingException e) {
                    logger.error("URLEncoder Error,encode=" + encode + ",param="
                            + params.get(name), e);
                }
                flag = "&";
            }
        }
        HttpGet httpGet = new HttpGet(buf.toString());
        return httpGet;
    }

    /**
     * 获得HttpPost对象
     *
     * @param url
     *            请求地址
     * @param params
     *            请求参数
     * @param encode
     *            编码方式
     * @return HttpPost对象
     */
    private static HttpPost getHttpPost(String url, Map<String, String> params,
                                        String encode) {
        HttpPost httpPost = new HttpPost(url);
        if (params != null) {
            List<NameValuePair> form = new ArrayList<NameValuePair>();
            for (String name : params.keySet()) {
                form.add(new BasicNameValuePair(name, params.get(name)));
            }
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form,
                        encode);
                httpPost.setEntity(entity);
            } catch (UnsupportedEncodingException e) {
                logger.error("UrlEncodedFormEntity Error,encode=" + encode
                        + ",form=" + form, e);
            }
        }
        return httpPost;
    }

    /**
     * 执行HTTP请求
     *
     * @param request
     *            请求对象
     * @param reqHeader
     *            请求头信息
     * @return 内容字符串
     */
    private static String executeHttpRequest(HttpUriRequest request,
                                             Map<String, String> reqHeader) throws Exception {
        HttpClient client = null;
        String result = null;
        try {
            // 创建HttpClient对象
            client = new DefaultHttpClient();
            // 设置连接超时时间
            client.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, 60);
            // 设置Socket超时时间
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                    36600);
            // 设置请求头信息
            if (reqHeader != null) {
                for (String name : reqHeader.keySet()) {
                    request.addHeader(name, reqHeader.get(name));
                }
            }
            // 获得返回结果
            HttpResponse response = client.execute(request);
            // 如果成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity());
            }
            // 如果失败
            else {
                StringBuffer errorMsg = new StringBuffer();
                errorMsg.append("httpStatus:");
                errorMsg.append(response.getStatusLine().getStatusCode());
                errorMsg.append(response.getStatusLine().getReasonPhrase());
                errorMsg.append(", Header: ");
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    errorMsg.append(header.getName());
                    errorMsg.append(":");
                    errorMsg.append(header.getValue());
                }
                logger.error("HttpResonse Error:" + errorMsg);
            }
        } catch (Exception e) {
            logger.error("http连接异常", e);
            throw new Exception("http连接异常");
        } finally {
            try {
                client.getConnectionManager().shutdown();
            } catch (Exception e) {
                logger.error("finally HttpClient shutdown error", e);
            }
        }
        return result;
    }


    /**
     * 
     * @Title: doPost
     * @Description: 发送json请求到指定url
     * @Author: XiaoShuai
     * @param url 发送地址
     * @param json
     * @return
     * @date :2015年11月5日上午9:02:56
     */
    public static String doPost(String url, Object json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);

        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(90000)
                .setConnectTimeout(90000).build();
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;

        try {
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine().getStatusCode());
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            logger.error("发送请求失败"+ e.getMessage());
            return null;
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 
     * @Title: doPost
     * @Description: 发送 POST 请求（HTTP），K-V形式
     * @Authe: XiaoShuai
     * @param apiUrl 请求接口url
     * @param params 参数map 
     * @return String
     * @throws
     */
    public static String doPost(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = "";
        HttpPost httpPost = new HttpPost(apiUrl);
        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(90000)
                .setConnectTimeout(90000).build();
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(),
                        entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * Json
     * @param apiUrl
     * @param json
     * @return
     */
    public static String doPostJson(String apiUrl, Object json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = "";
        HttpPost httpPost = new HttpPost(apiUrl);
        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(90000)
                .setConnectTimeout(90000).build();
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;

        try {
            StringEntity stringEntity = new StringEntity(json.toString(),"UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, "UTF-8");
            } else {
                logger.error(
                        String.format("请求url [%s],json [ %s ] statusCode[%d]",
                                apiUrl, json, response.getStatusLine().getStatusCode()));
            }
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 
     * @Title: doPostSSL
     * @Description: 发送 SSL POST 请求（HTTPS），JSON形式
     * @Authe: XiaoShuai
     * @param apiUrl
     * @param json
     * @return String
     * 2016年12月1日下午5:07:44
     * @throws
     */
    public static String doPostSSL(String apiUrl, Object json) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(),"UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }
    /**
     * 
     * @Title: doPostSSL
     * @Description: 发送 SSL POST 请求（HTTPS），K-V形式
     * @Authe: XiaoShuai
     * @param apiUrl 请求地址
     * @param params 传入参数
     * @return String
     * 2016年12月16日上午9:59:21
     * @throws
     */
    public static String doPostSSL(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 
     * @Title: createSSLConnSocketFactory
     * @Description: 创建SSL安全连接
     * @Authe: XiaoShuai
     * @return SSLConnectionSocketFactory
     * 2016年12月1日下午5:00:46
     * @throws
     */
    public static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

}
