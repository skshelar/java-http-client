package com.sendgrid;

import java.net.URI;
import java.io.InputStream;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.lang.NullPointerException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.Header;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

public class Client {

    private CloseableHttpClient httpClient;
    
    public Client(){
        this.httpClient = HttpClients.createDefault();
    }
    
    public Client(CloseableHttpClient httpClient){
        this.httpClient = httpClient;
    }
    
    public URI buildURL(String baseURL, String endpoint, Map<String,String> queryParams){
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https");
            builder.setHost(baseURL); 
            builder.setPath(endpoint);
            if (queryParams != null){
                for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                    builder.setParameter(entry.getKey(), entry.getValue());
                }   
            }
            
            try{
                URI url = builder.build();
                return url;
            }catch(URISyntaxException ex){
                ex.printStackTrace();
            }
            
            return null;
    }
    
    public Response getResponse(CloseableHttpResponse resp){
        Response response = new Response();
        ResponseHandler<String> handler = new BasicResponseHandler();
        String body = "";
        
        try{
            body = handler.handleResponse(resp);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        Header headers[] = resp.getAllHeaders();
        Map<String,String> responseHeaders = new HashMap<String,String>();
        for(Header h:headers){
            responseHeaders.put(h.getName(), h.getValue());
        }
        
        response = new Response(resp.getStatusLine().getStatusCode(), body , responseHeaders);
        return response;
    }
    
    public Response Get(Request request){
        Response response = new Response();
        
        URI url = buildURL(request.baseURL, request.endpoint, request.queryParams);
        HttpGet httpGet = new HttpGet(url.toString());
        
        if(request.requestHeaders != null){
            for (Map.Entry<String, String> entry : request.requestHeaders.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }            
        }
        
        try{
            CloseableHttpResponse resp = httpClient.execute(httpGet);
            response = getResponse(resp);
            resp.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return response;
    }
    
    public Response Post(Request request){
        Response response = new Response();
        
        URI url = buildURL(request.baseURL, request.endpoint, request.queryParams);
        HttpPost httpPost = new HttpPost(url.toString());
        
        if(request.requestHeaders != null){
            for (Map.Entry<String, String> entry : request.requestHeaders.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }            
        }
        
        try{
            httpPost.setEntity(new StringEntity(request.requestBody));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        try{
            CloseableHttpResponse resp = httpClient.execute(httpPost);
            response = getResponse(resp);
            resp.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return response;
    }
    
    public Response Patch(Request request){
        Response response = new Response();
        
        URI url = buildURL(request.baseURL, request.endpoint, request.queryParams);
        HttpPatch httpPatch = new HttpPatch(url.toString());
        
        if(request.requestHeaders != null){
            for (Map.Entry<String, String> entry : request.requestHeaders.entrySet()) {
                httpPatch.setHeader(entry.getKey(), entry.getValue());
            }            
        }
        
        try{
            httpPatch.setEntity(new StringEntity(request.requestBody));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        try{
            CloseableHttpResponse resp = httpClient.execute(httpPatch);
            response = getResponse(resp);
            resp.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return response;
    }

    public Response Put(Request request){
        Response response = new Response();
        
        URI url = buildURL(request.baseURL, request.endpoint, request.queryParams);
        HttpPut httpPut = new HttpPut(url.toString());
        
        if(request.requestHeaders != null){
            for (Map.Entry<String, String> entry : request.requestHeaders.entrySet()) {
                httpPut.setHeader(entry.getKey(), entry.getValue());
            }            
        }
        
        try{
            httpPut.setEntity(new StringEntity(request.requestBody));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        try{
            CloseableHttpResponse resp = httpClient.execute(httpPut);
            response = getResponse(resp);
            resp.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return response;
    }
    
    public Response Delete(Request request){
        Response response = new Response();
        
        URI url = buildURL(request.baseURL, request.endpoint, request.queryParams);
        HttpDelete httpDelete = new HttpDelete(url.toString());
        
        if(request.requestHeaders != null){
            for (Map.Entry<String, String> entry : request.requestHeaders.entrySet()) {
                httpDelete.setHeader(entry.getKey(), entry.getValue());
            }            
        }
        
        try{
            CloseableHttpResponse resp = httpClient.execute(httpDelete);
            response = getResponse(resp);
            resp.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return response;
    }
    
    public Response API(Request request) throws IOException{
        try{
            switch (request.method) {
                case GET:  
                    return Get(request);
                case POST:  
                    return Post(request);
                case PUT:  
                    return Put(request);
                case PATCH:
                    return Patch(request);
                case DELETE:  
                    return Delete(request);
                default:
                    throw new IOException("We only support GET, PUT, PATCH, POST and DELETE.");
            }
        }catch(NullPointerException ex){
            throw new IOException("You must specify the method.");
        }
    }
        
}