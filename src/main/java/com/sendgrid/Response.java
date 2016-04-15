package com.sendgrid;

import java.util.List;
import java.util.Map;

/**
  * Class Response provides a standard interface to an API's response.
  */
public class Response {
  public int statusCode;
  public String responseBody;
  public Map<String,String> responseHeaders;

  /**
    * Set the API's response.
    */ 
  public Response(int statusCode, String responseBody, Map<String,String> responseHeaders) {
    this.statusCode = statusCode;
    this.responseBody = responseBody;
    this.responseHeaders = responseHeaders;
  }
  
  public Response() {
    this.reset();
  }
    
  /**
   * Place the object into an empty state.
   */
  public void reset() {
    this.statusCode = 0;
    responseBody = "";
    responseHeaders = null;    
  }

}