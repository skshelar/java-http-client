import com.sendgrid.*;
import java.io.OutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class Example
{
    public static void main(String[] args) {
        Client client = new Client();
        
        Request request = new Request();
        request.baseURL = "api.sendgrid.com";
        Map<String,String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Authorization", "Bearer " + System.getenv("SENDGRID_API_KEY"));
        requestHeaders.put("Content-Type", "application/json");
        request.requestHeaders = requestHeaders;
        
        Response response = new Response();
        
        // GET Collection
        request.method = Method.GET;
        request.endpoint = "/v3/api_keys";
        Map<String,String> queryParams = new HashMap<String, String>();
        queryParams.put("limit", "100");
        queryParams.put("offset", "0");
        request.queryParams = queryParams;
        try{
            response = client.API(request);
            System.out.println(response.statusCode);
            System.out.println(response.responseBody);
            System.out.println(response.responseHeaders);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        request.queryParams = null;
        
        // POST
        request.method = Method.POST;
        request.endpoint = "/v3/api_keys";
        request.requestBody = "{\"name\": \"My API Key\",\"scopes\": [\"mail.send\",\"alerts.create\",\"alerts.read\"]}";
        try{
            response = client.API(request);
            System.out.println(response.statusCode);
            System.out.println(response.responseBody);
            System.out.println(response.responseHeaders);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        String apiKeyID = "";
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.responseBody);
            apiKeyID = json.path("api_key_id").asText(); 
        }catch(IOException ex){
            System.out.println(ex.toString());
        }
        request.requestBody = "";
        
        // GET Single
        request.method = Method.GET;
        request.endpoint = "/v3/api_keys/" + apiKeyID;
        try{
            response = client.API(request);
            System.out.println(response.statusCode);
            System.out.println(response.responseBody);
            System.out.println(response.responseHeaders);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        // PATCH
        request.method = Method.PATCH;
        request.requestBody = "{\"name\": \"A New Hope\"}";
        try{
            response = client.API(request);
            System.out.println(response.statusCode);
            System.out.println(response.responseBody);
            System.out.println(response.responseHeaders);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        request.requestBody = "";
        
        // PUT
        request.method = Method.PUT;
        request.requestBody = "{\"name\": \"A New Hope\",\"scopes\": [\"user.profile.read\",\"user.profile.update\"]}";
        try{
            response = client.API(request);
            System.out.println(response.statusCode);
            System.out.println(response.responseBody);
            System.out.println(response.responseHeaders);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        request.requestBody = "";
        
        // DELETE
        request.method = Method.DELETE;
        try{
            response = client.API(request);
            System.out.println(response.statusCode);
            System.out.println(response.responseHeaders);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}