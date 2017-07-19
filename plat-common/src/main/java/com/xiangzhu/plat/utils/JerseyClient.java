package com.xiangzhu.plat.utils;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

import static com.xiangzhu.plat.utils.JerseyClient.Protocol.HTTP;
import static com.xiangzhu.plat.utils.JerseyClient.Protocol.HTTPS;

/**
 * this service class is for invoking the restful interface something as resources
 * Created by liluoqi on 15/9/28
 */
public class JerseyClient {

    private String host;
    private String contextPath;
    private Protocol protocol;

    private static Client client = Client.create();
    private static Logger logger = LoggerFactory.getLogger(JerseyClient.class);


    private JerseyClient() {
    }

    private JerseyClient(String host, String contextPath) {
        this.host = host;
        this.contextPath = contextPath;
    }

    public static JerseyClient buildHttpJerseyClient(String host, String contextPath) {
        return new JerseyClient(host, contextPath).withProtocol(HTTP);
    }

    public static JerseyClient buildHttpsJerseyClient(String host, String contextPath) {
        return new JerseyClient(host, contextPath).withProtocol(HTTPS);
    }

    private JerseyClient withProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public <T> T get(String uri, Map map, Class<T> clazz) {
        try {
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            if (map != null && map.size() > 0) {
                for (Object key : map.keySet()) {
                    queryParams.add(key, map.get(key));
                }
            }
            WebResource webResource = client.resource(url(uri));
            return webResource.get(clazz);
        } catch (Exception e) {
            logger.error(String.format("GET请求restful服务:%s的时候报错:%s", uri, e.getMessage()));
        }
        return null;
    }

    public ClientResponse get(String uri, Map map) {
        try {
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            if (map != null && map.size() > 0) {
                for (Object key : map.keySet()) {
                    queryParams.add(key, map.get(key));
                }
            }
            WebResource webResource = client.resource(buildQueryUrl(uri, map));
            return webResource.get(ClientResponse.class);
        } catch (Exception e) {
            logger.error(String.format("GET请求restful服务:%s的时候报错:%s", uri, e.getMessage()));
        }
        return null;
    }

    public String getForString(String uri, Map map) {
        try {
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            if (map != null && map.size() > 0) {
                for (Object key : map.keySet()) {
                    queryParams.add(key, map.get(key));
                }
            }
            WebResource webResource = client.resource(buildQueryUrl(uri, map));
            ClientResponse clientResponse = webResource.get(ClientResponse.class);
            if (clientResponse != null && clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                return clientResponse.getEntity(String.class);
            }
        } catch (Exception e) {
            logger.error(String.format("GET请求restful服务:%s的时候报错:%s", uri, e.getMessage()));
        }
        return null;
    }

    public <T> T post(String uri, Map map, Class<T> clazz) {
        try {
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            if (map != null && map.size() > 0) {
                for (Object key : map.keySet()) {
                    queryParams.add(key, map.get(key));
                }
            }
            WebResource webResource = client.resource(url(uri));
            ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, queryParams);
            if (clientResponse != null && clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                return new Gson().fromJson(clientResponse.getEntity(String.class), clazz);
            }
        } catch (Exception e) {
            logger.error(String.format("POST请求restful服务:%s的时候报错:%s", uri, e.getMessage()));
        }
        return null;
    }

    public String postForString(String uri, Map map) {
        try {
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            if (map != null && map.size() > 0) {
                for (Object key : map.keySet()) {
                    queryParams.add(key, map.get(key));
                }
            }
            WebResource webResource = client.resource(url(uri));
            ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, queryParams);
            if (clientResponse != null && clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                return clientResponse.getEntity(String.class);
            }
        } catch (Exception e) {
            logger.error(String.format("POST请求restful服务:%s的时候报错:%s", uri, e.getMessage()));
        }
        return null;
    }

    public String postForString(String uri) {
        try {
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            WebResource webResource = client.resource(url(uri));
            ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, queryParams);
            if (clientResponse != null && clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                return clientResponse.getEntity(String.class);
            }
        } catch (Exception e) {
            logger.error(String.format("POST请求restful服务:%s的时候报错:%s", uri, e.getMessage()));
        }
        return null;
    }


    public ClientResponse post(String uri, Map map) {
        try {
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            if (map != null && map.size() > 0) {
                for (Object key : map.keySet()) {
                    queryParams.add(key, map.get(key));
                }
            }
            WebResource webResource = client.resource(url(uri));
            return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, queryParams);
        } catch (UniformInterfaceException e) {
            return e.getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    private String url(String uri) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!host.endsWith("/")) {
            host = String.format("%s%s", host, "/");
        }
        if (contextPath.startsWith("/")) {
            contextPath = contextPath.substring(1);
        }
        if (contextPath.endsWith("/") && uri.startsWith("/")) {
            contextPath = contextPath.substring(0, contextPath.length() - 1);
        }
        if (!contextPath.endsWith("/") && !uri.startsWith("/")) {
            contextPath = String.format("%s%s", contextPath, "/");
        }
        return stringBuilder.append(this.protocol.getValue()).append(host).append(contextPath).append(uri).toString();
    }

    private String buildQueryUrl(String uri, Map<Object, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Object, Object> param : map.entrySet()) {
            stringBuilder.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        String string = stringBuilder.toString();
        int lastSpecialChar = string.lastIndexOf("&");
        return String.format("%s?%s", url(uri), string.substring(0, lastSpecialChar));
    }

    enum Protocol {
        HTTP("http://"),
        HTTPS("https://");
        private String value;

        Protocol(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
