package com.sendgrid;

import org.apache.http.Header;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Hack to get DELETE to accept a request body
@NotThreadSafe
class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }
}

/**
  * Class Client allows for quick and easy access any REST or REST-like API.
  */
public class Client {

  private CloseableHttpClient httpClient;
  private Boolean test;

  /**
  * Constructor for using the default CloseableHttpClient.
  */
  public Client() {
    this.httpClient = HttpClients.createDefault();
    this.test = false;
  }

  /**
  * Constructor for passing in an httpClient for mocking.
  *
  * @param httpClient an Apache CloseableHttpClient
  */
  public Client(CloseableHttpClient httpClient) {
    this.httpClient = httpClient;
    this.test = false;
  }

  /**
  * Constructor for passing in a test parameter to allow for http calls
  *
  * @param test is a Bool
  */
  public Client(Boolean test) {
    this.httpClient = HttpClients.createDefault();
    this.test = test;
  }

  /**
    * Add query parameters to a URL.
    *
    * @param baseUri (e.g. "api.sendgrid.com")
    * @param endpoint (e.g. "/your/endpoint/path")
    * @param queryParams map of key, values representing the query parameters
    */
  public URI buildUri(String baseUri, String endpoint, Map<String,String> queryParams)
      throws URISyntaxException {
    URIBuilder builder = new URIBuilder();
    URI uri;

    if (this.test == true) {
      builder.setScheme("http");
    } else {
      builder.setScheme("https");
    }

    builder.setHost(baseUri);
    builder.setPath(endpoint);

    if (queryParams != null) {
      for (Map.Entry<String, String> entry : queryParams.entrySet()) {
        builder.setParameter(entry.getKey(), entry.getValue());
      }
    }

    try {
      uri = builder.build();
    } catch (URISyntaxException ex) {
      throw ex;
    }

    return uri;
  }

  /**
    * Prepare a Response object from an API call via Apache's HTTP client.
    *
    * @param response from a call to a CloseableHttpClient
    */
  public Response getResponse(CloseableHttpResponse response) throws IOException {
    ResponseHandler<String> handler = new BasicResponseHandler();
    String responseBody = "";

    int statusCode = response.getStatusLine().getStatusCode();

    try {
      responseBody = handler.handleResponse(response);
    } catch (IOException ex) {
      throw ex;
    }

    Header[] headers = response.getAllHeaders();
    Map<String,String> responseHeaders = new HashMap<String,String>();
    for (Header h:headers) {
      responseHeaders.put(h.getName(), h.getValue());
    }

    return new Response(statusCode, responseBody, responseHeaders);
  }

  /**
    * Make a GET request and provide the status code, response body and response headers.
    */
  public Response get(Request request) throws URISyntaxException, IOException {
    CloseableHttpResponse serverResponse = null;
    Response response = new Response();
    URI uri = null;
    HttpGet httpGet = null;

    try {
      uri = buildUri(request.baseUri, request.endpoint, request.queryParams);
      httpGet = new HttpGet(uri.toString());
    } catch (URISyntaxException ex) {
      throw ex;
    }

    if (request.headers != null) {
      for (Map.Entry<String, String> entry : request.headers.entrySet()) {
        httpGet.setHeader(entry.getKey(), entry.getValue());
      }
    }

    try {
      serverResponse = httpClient.execute(httpGet);
      response = getResponse(serverResponse);
    } catch (IOException ex) {
      throw ex;
    } finally {
      if (serverResponse != null) {
        serverResponse.close();
      }
    }

    return response;
  }

  /**
    * Make a POST request and provide the status code, response body and response headers.
    */
  public Response post(Request request) throws URISyntaxException, IOException {
    CloseableHttpResponse serverResponse = null;
    Response response = new Response();
    URI uri = null;
    HttpPost httpPost = null;

    try {
      uri = buildUri(request.baseUri, request.endpoint, request.queryParams);
      httpPost = new HttpPost(uri.toString());
    } catch (URISyntaxException ex) {
      throw ex;
    }

    if (request.headers != null) {
      for (Map.Entry<String, String> entry : request.headers.entrySet()) {
        httpPost.setHeader(entry.getKey(), entry.getValue());
      }
    }

    try {
      httpPost.setEntity(new StringEntity(request.body));
    } catch (IOException ex) {
      throw ex;
    }

    try {
      serverResponse = httpClient.execute(httpPost);
      response = getResponse(serverResponse);
      serverResponse.close();
    } catch (IOException ex) {
      throw ex;
    } finally {
      if (serverResponse != null) {
        serverResponse.close();
      }
    }

    return response;
  }

  /**
    * Make a PATCH request and provide the status code, response body and response headers.
    */
  public Response patch(Request request) throws URISyntaxException, IOException {
    CloseableHttpResponse serverResponse = null;
    Response response = new Response();
    URI uri = null;
    HttpPatch httpPatch = null;

    try {
      uri = buildUri(request.baseUri, request.endpoint, request.queryParams);
      httpPatch = new HttpPatch(uri.toString());
    } catch (URISyntaxException ex) {
      throw ex;
    }

    if (request.headers != null) {
      for (Map.Entry<String, String> entry : request.headers.entrySet()) {
        httpPatch.setHeader(entry.getKey(), entry.getValue());
      }
    }

    try {
      httpPatch.setEntity(new StringEntity(request.body));
    } catch (IOException ex) {
      throw ex;
    }

    try {
      serverResponse = httpClient.execute(httpPatch);
      response = getResponse(serverResponse);
      serverResponse.close();
    } catch (IOException ex) {
      throw ex;
    } finally {
      if (serverResponse != null) {
        serverResponse.close();
      }
    }

    return response;
  }

  /**
    * Make a PUT request and provide the status code, response body and response headers.
    */
  public Response put(Request request) throws URISyntaxException, IOException {
    CloseableHttpResponse serverResponse = null;
    Response response = new Response();
    URI uri = null;
    HttpPut httpPut = null;

    try {
      uri = buildUri(request.baseUri, request.endpoint, request.queryParams);
      httpPut = new HttpPut(uri.toString());
    } catch (URISyntaxException ex) {
      throw ex;
    }

    if (request.headers != null) {
      for (Map.Entry<String, String> entry : request.headers.entrySet()) {
        httpPut.setHeader(entry.getKey(), entry.getValue());
      }
    }


    try {
      httpPut.setEntity(new StringEntity(request.body));
    } catch (IOException ex) {
      throw ex;
    }

    try {
      serverResponse = httpClient.execute(httpPut);
      response = getResponse(serverResponse);
      serverResponse.close();
    } catch (IOException ex) {
      throw ex;
    } finally {
      if (serverResponse != null) {
        serverResponse.close();
      }
    }

    return response;
  }

  /**
    * Make a DELETE request and provide the status code and response headers.
    */
  public Response delete(Request request) throws URISyntaxException, IOException {
    CloseableHttpResponse serverResponse = null;
    Response response = new Response();
    URI uri = null;
    HttpDeleteWithBody httpDelete = null;

    try {
      uri = buildUri(request.baseUri, request.endpoint, request.queryParams);
      httpDelete = new HttpDeleteWithBody(uri.toString());
    } catch (URISyntaxException ex) {
      throw ex;
    }

    if (request.headers != null) {
      for (Map.Entry<String, String> entry : request.headers.entrySet()) {
        httpDelete.setHeader(entry.getKey(), entry.getValue());
      }
    }

    try {
      httpDelete.setEntity(new StringEntity(request.body));
    } catch (IOException ex) {
      throw ex;
    }

    try {
      serverResponse = httpClient.execute(httpDelete);
      response = getResponse(serverResponse);
      serverResponse.close();
    } catch (IOException ex) {
      throw ex;
    } finally {
      if (serverResponse != null) {
        serverResponse.close();
      }
    }

    return response;
  }

  /**
    * A thin wrapper around the HTTP methods.
    */
  public Response api(Request request) throws IOException {
    try {
      if (request.method == null) {
        throw new IOException("We only support GET, PUT, PATCH, POST and DELETE.");
      }
      switch (request.method) {
        case GET:
          return get(request);
        case POST:
          return post(request);
        case PUT:
          return put(request);
        case PATCH:
          return patch(request);
        case DELETE:
          return delete(request);
        default:
          throw new IOException("We only support GET, PUT, PATCH, POST and DELETE.");
      }
    } catch (IOException ex) {
      throw ex;
    } catch (URISyntaxException ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      throw new IOException(errors.toString());
    }
  }
}
