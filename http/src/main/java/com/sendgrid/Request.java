package com.sendgrid;

import java.util.Map;

public class Request {
    public Method method;
    public String baseURL;
    public String endpoint;
    public String requestBody;
    public Map<String,String> requestHeaders;
    public Map<String,String> queryParams;
    
    public Request(){
        this.reset();
    }
    
    public void reset(){
        this.method = null;
        this.baseURL = "";
        this.endpoint = "";
        this.requestBody = "";
        this.requestHeaders = null;
        this.queryParams = null;        
    }
}