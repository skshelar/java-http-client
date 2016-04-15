package com.sendgrid;

import java.util.Map;

/**
  * Class Response provides a standard interface to an API's HTTP request.
  */
public class Request {
  public Method method;
  public String baseUri;
  public String endpoint;
  public String requestBody;
  public Map<String,String> requestHeaders;
  public Map<String,String> queryParams;
  
  public Request() {
    this.reset();
  }
  
  /**
  * Place the object into an empty state.
  */
  public void reset() {
    this.method = null;
    this.baseUri = "";
    this.endpoint = "";
    this.requestBody = "";
    this.requestHeaders = null;
    this.queryParams = null; 
  }
}