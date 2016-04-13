package com.sendgrid;

import java.net.URI;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.ByteArrayInputStream;

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
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.HttpVersion;
import org.apache.http.HttpStatus;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHeader;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.Matchers;

/**
 * Unit test for simple App.
 */
public class ClientTest extends Mockito {
    
    private CloseableHttpClient httpClient;
    
    @Before
    public void setUp() throws Exception{
        this.httpClient = mock(CloseableHttpClient.class);
    }
    
    @Test   
    public void testBuildURL()
    {
        Client client = new Client();
        String baseURL = "api.test.com";
        String endpoint = "/endpoint";
        Map<String,String> queryParams = new HashMap<String,String>();
        queryParams.put("test", "1");
        queryParams.put("test2", "2");
        URI uri = client.buildURL(baseURL, endpoint, queryParams);
        String url = uri.toString();
        Assert.assertEquals(url, "https://api.test.com/endpoint?test=1&test2=2");
    }
    
    @Test
    public void testGetResponse()
    {
        Client client = new Client();
        Response testResponse = new Response();
        Header[] mockedHeaders = null;
        try{
            CloseableHttpResponse response = mock(CloseableHttpResponse.class);
            HttpEntity entity = mock(HttpEntity.class);
            StatusLine statusline = mock(StatusLine.class);
            when(statusline.getStatusCode()).thenReturn(200);
            when(response.getStatusLine()).thenReturn(statusline);
            when(response.getEntity()).thenReturn(new InputStreamEntity(new ByteArrayInputStream("{\"message\":\"success\"}".getBytes())));
            mockedHeaders = new Header[] { new BasicHeader("headerA", "valueA") };
            when(response.getAllHeaders()).thenReturn(mockedHeaders);
            when(httpClient.execute(Matchers.any(HttpGet.class))).thenReturn(response);
            HttpGet httpGet = new HttpGet("https://api.test.com");
            CloseableHttpResponse resp = httpClient.execute(httpGet);
            testResponse = client.getResponse(resp);
            resp.close();           
        }catch(IOException ex){
            ex.printStackTrace();
        }

        Assert.assertTrue(testResponse.statusCode == 200);
        Assert.assertEquals(testResponse.responseBody, "{\"message\":\"success\"}");
        Map<String,String> headers = new HashMap<String,String>();
        for(Header h:mockedHeaders){
           headers.put(h.getName(), h.getValue());
        }
        Assert.assertEquals(testResponse.responseHeaders, headers);
    }
}
