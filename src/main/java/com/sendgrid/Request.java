package com.sendgrid;

import java.util.Map;
import java.util.HashMap;

/**
  * Class Response provides a standard interface to an API's HTTP request.
  */
public class Request {
  public Method method;
  public String baseUri;
  public String endpoint;
  public String body;
  private final Map<String, String> headers;
  private final Map<String, String> queryParams;

  public Request() {
    this.headers = new HashMap<String, String>();
    this.queryParams = new HashMap<String, String>();
    this.reset();
  }

  /**
  * Place the object into an empty state.
  */
  public void reset() {
    this.method = null;
    this.baseUri = "";
    this.endpoint = "";
    this.body = "";
    this.headers.clear();
    this.queryParams.clear();
  }

  public Map<String, String> getHeaders() {
    return this.headers;
  }

  public Map<String, String> getQueryParams() {
    return this.queryParams;
  }
}