package com.sendgrid;

import java.util.Map;
import java.util.List;

public class Response {
    public int statusCode;
    public String responseBody;
    public Map<String,String> responseHeaders;

    public Response(int statusCode, String responseBody, Map<String,String> responseHeaders){
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.responseHeaders = responseHeaders;
    }
    
    public Response(){
        this.reset();
    }
    
    public void reset(){
        this.statusCode = 0;
        responseBody = "";
        responseHeaders = null;    
    }

}