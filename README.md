[![Build Status](https://travis-ci.org/sendgrid/java-http-client.svg?branch=master)](https://travis-ci.org/sendgrid/java-http-client) [![Maven](https://maven-badges.herokuapp.com/maven-central/com.sendgrid/java-http-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.sendgrid/java-http-client)

**HTTP REST client, simplified for Java**

Here is a quick example:

`GET /your/api/{param}/call`

```java
Client client = new Client();

Request request = new Request();
request.baseUri = "api.test.com";
request.method = Method.GET;
String param = "param";
request.endpoint = "/your/api/" + param + "/call";

try {
    Response response = client.api(request);
    System.out.println(response.statusCode);
    System.out.println(response.responseBody);
    System.out.println(response.responseHeaders);
} catch (IOException ex) {
    throw ex;
}
```

`POST /your/api/{param}/call` with headers, query parameters and a request body.

```java
Map<String,String> requestHeaders = new HashMap<String, String>();
requestHeaders.put("Authorization", "Bearer " + System.getenv("SENDGRID_API_KEY"));
requestHeaders.put("Content-Type", "application/json");
request.requestHeaders = requestHeaders;
Map<String,String> queryParams = new HashMap<String, String>();
queryParams.put("limit", "100");
queryParams.put("offset", "0");
request.queryParams = queryParams;
request.requestBody ="{\"name\": \"My Request Body\"}";
request.method = Method.POST;
String param = "param";
request.endpoint = "/your/api/" + param + "/call";

try {
    Response response = client.api(request);
    System.out.println(response.statusCode);
    System.out.println(response.responseBody);
    System.out.println(response.responseHeaders);
} catch (IOException ex) {
    throw ex;
}
```

# Installation

## via Maven w/ Gradle

```groovy
...
dependencies {
  ...
  compile 'com.sendgrid:java-http-client:1.0.0'
}

repositories {
  mavenCentral()
}
...
```

## Maven

```xml
<dependency>
    <groupId>com.sendgrid</groupId>
    <artifactId>java-http-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

`mvn install`

## Fat Jar

[Download](http://repo1.maven.org/maven2/com/sendgrid/java-http-client/1.0.0/java-http-client-1.0.0-jar.jar)

## Usage ##

Import the library in the file appropriate to your Java project.

```java
import com.sendgrid.*;
```

Following is an example using SendGrid. You can get your free account [here](https://sendgrid.com/free?source=java-http-client).

First, update your environment with your [SENDGRID_API_KEY](https://app.sendgrid.com/settings/api_keys).

```bash
echo "export SENDGRID_API_KEY='YOUR_API_KEY'" > sendgrid.env
echo "sendgrid.env" >> .gitignore
source ./sendgrid.env
```

Following is an abridged example, here is the [full working code](https://github.com/sendgrid/java-http-client/blob/master/examples/Example.java).

Change '{path_to}' and you can run with the following command

```bash
mvn package
cd examples
javac -classpath ./jackson-databind-2.7.3.jar:./jackson-annotations-2.7.0.jar:./jackson-core-2.7.3.jar:/{path_to}/java-http-client-1.0.0-jar.jar:. Example.java && java -classpath ./jackson-databind-2.7.3.jar:./jackson-annotations-2.7.0.jar:./jackson-core-2.7.3.jar:/{path_to}/java-http-client-1.0.0-jar.jar:. Example
```

```java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sendgrid.*;

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
    try {
      response = client.api(request);
    } catch (IOException ex) {
      throw ex;
    }
    request.queryParams = null;
    
    // POST
    request.method = Method.POST;
    request.endpoint = "/v3/api_keys";
    request.requestBody =
        "{\"name\": \"My api Key\",\"scopes\": [\"mail.send\",\"alerts.create\",\"alerts.read\"]}";
    try {
      response = client.api(request);
    } catch (IOException ex) {
      throw ex;
    }
    String apiKeyId = "";
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode json = mapper.readTree(response.responseBody);
      apiKeyId = json.path("api_key_id").asText(); 
    } catch (IOException ex) {
      throw ex;
    }
    request.requestBody = "";
    
    // GET Single
    request.method = Method.GET;
    request.endpoint = "/v3/api_keys/" + apiKeyId;
    try {
      response = client.api(request);
    } catch (IOException ex) {
      throw ex;
    }
    
    // PATCH
    request.method = Method.PATCH;
    request.requestBody = "{\"name\": \"A New Hope\"}";
    try {
      response = client.api(request);
    } catch (IOException ex) {
      throw ex;
    }
    request.requestBody = "";
    
    // PUT
    request.method = Method.PUT;
    request.requestBody =
          "{\"name\": \"A New Hope\",\"scopes\": [\"user.profile.read\",\"user.profile.update\"]}";
    try {
      response = client.api(request);
    } catch (IOException ex) {
      throw ex;
    }
    request.requestBody = "";
    
    // DELETE
    request.method = Method.DELETE;
    try {
      response = client.api(request);
    } catch (IOException ex) {
      throw ex;
    }
  }
}
```

# Announcements

[2016.04.15] - We hit version 1!

# Roadmap

[Milestones](https://github.com/sendgrid/java-http-client/milestones)

# How to Contribute

We encourage contribution to our libraries, please see our [CONTRIBUTING](https://github.com/sendgrid/java-http-client/blob/master/CONTRIBUTING.md) guide for details.

* [Feature Request](https://github.com/sendgrid/java-http-client/blob/master/CONTRIBUTING.md#feature_request)
* [Bug Reports](https://github.com/sendgrid/java-http-client/blob/master/CONTRIBUTING.md#submit_a_bug_report)
* [Improvements to the Codebase](https://github.com/sendgrid/java-http-client/blob/master/CONTRIBUTING.md#improvements_to_the_codebase)

# About

![SendGrid Logo]
(https://assets3.sendgrid.com/mkt/assets/logos_brands/small/sglogo_2015_blue-9c87423c2ff2ff393ebce1ab3bd018a4.png)

java-http-client is guided and supported by the SendGrid [Developer Experience Team](mailto:dx@sendgrid.com).

java-http-client is maintained and funded by SendGrid, Inc. The names and logos for java-http-client are trademarks of SendGrid, Inc.