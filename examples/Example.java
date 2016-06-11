import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sendgrid.Client;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Example {
  public static void main(String[] args) throws IOException {
    Client client = new Client();

    Request request = new Request();
    request.baseUri = "api.sendgrid.com";
    Map<String,String> requestHeaders = new HashMap<String, String>();
    requestHeaders.put("Authorization", "Bearer " + System.getenv("SENDGRID_API_KEY"));
    request.headers = requestHeaders;

    Response response = new Response();

    // GET Collection
    request.method = Method.GET;
    request.endpoint = "/v3/api_keys";
    Map<String,String> queryParams = new HashMap<String, String>();
    queryParams.put("limit", "100");
    queryParams.put("offset", "0");
    request.queryParams = queryParams;
    try {
      response = client.api(request);
      System.out.println(response.statusCode);
      System.out.println(response.body);
      System.out.println(response.headers);
    } catch (IOException ex) {
      throw ex;
    }
    request.queryParams = null;

    // POST
    request.method = Method.POST;
    request.endpoint = "/v3/api_keys";
    request.body =
        "{\"name\": \"My api Key\",\"scopes\": [\"mail.send\",\"alerts.create\",\"alerts.read\"]}";
    try {
      response = client.api(request);
      System.out.println(response.statusCode);
      System.out.println(response.body);
      System.out.println(response.headers);
    } catch (IOException ex) {
      throw ex;
    }
    String apiKeyId = "";
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode json = mapper.readTree(response.body);
      apiKeyId = json.path("api_key_id").asText();
    } catch (IOException ex) {
      throw ex;
    }
    request.body = "";

    // GET Single
    request.method = Method.GET;
    request.endpoint = "/v3/api_keys/" + apiKeyId;
    try {
      response = client.api(request);
      System.out.println(response.statusCode);
      System.out.println(response.body);
      System.out.println(response.headers);
    } catch (IOException ex) {
      throw ex;
    }

    // PATCH
    request.method = Method.PATCH;
    request.body = "{\"name\": \"A New Hope\"}";
    try {
      response = client.api(request);
      System.out.println(response.statusCode);
      System.out.println(response.body);
      System.out.println(response.headers);
    } catch (IOException ex) {
      throw ex;
    }
    request.body = "";

    // PUT
    request.method = Method.PUT;
    request.body =
          "{\"name\": \"A New Hope\",\"scopes\": [\"user.profile.read\",\"user.profile.update\"]}";
    try {
      response = client.api(request);
      System.out.println(response.statusCode);
      System.out.println(response.body);
      System.out.println(response.headers);
    } catch (IOException ex) {
      throw ex;
    }
    request.body = "";

    // DELETE
    request.method = Method.DELETE;
    try {
      response = client.api(request);
      System.out.println(response.statusCode);
      System.out.println(response.headers);
    } catch (IOException ex) {
      throw ex;
    }
  }
}