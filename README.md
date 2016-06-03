[![Build Status](https://travis-ci.org/sendgrid/java-http-client.svg?branch=master)](https://travis-ci.org/sendgrid/java-http-client) [![Maven](https://maven-badges.herokuapp.com/maven-central/com.sendgrid/java-http-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.sendgrid/java-http-client)

**HTTP REST client, simplified for Java**

# Announcements

All updates to this project is documented in our [CHANGELOG](https://github.com/sendgrid/java-http-client/blob/master/CHANGELOG.md).

# Installation

## via Maven w/ Gradle

```groovy
...
dependencies {
  ...
  compile 'com.sendgrid:java-http-client:2.0.0'
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
    <version>2.0.0</version>
</dependency>
```

`mvn install`

## Fat Jar

[Download](http://repo1.maven.org/maven2/com/sendgrid/java-http-client/2.0.0/java-http-client-2.0.0-jar.jar)

## Dependencies

- Please see the [build.gradle file](https://github.com/sendgrid/java-http-client/blob/master/build.gradle)

# Quick Start

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
    System.out.println(response.body);
    System.out.println(response.headers);
} catch (IOException ex) {
    throw ex;
}
```

`POST /your/api/{param}/call` with headers, query parameters and a request body.

```java
Map<String,String> requestHeaders = new HashMap<String, String>();
requestHeaders.put("Authorization", "Bearer " + System.getenv("SENDGRID_API_KEY"));
requestHeaders.put("Content-Type", "application/json");
request.headers = requestHeaders;
Map<String,String> queryParams = new HashMap<String, String>();
queryParams.put("limit", "100");
queryParams.put("offset", "0");
request.queryParams = queryParams;
request.body ="{\"name\": \"My Request Body\"}";
request.method = Method.POST;
String param = "param";
request.endpoint = "/your/api/" + param + "/call";

try {
    Response response = client.api(request);
    System.out.println(response.statusCode);
    System.out.println(response.body);
    System.out.println(response.headers);
} catch (IOException ex) {
    throw ex;
}
```

# Usage

- [Example Code](https://github.com/sendgrid/java-http-client/tree/master/examples)

The example uses SendGrid, you can get your free account [here](https://sendgrid.com/free?source=java-http-client).

First, update your environment with your [SENDGRID_API_KEY](https://app.sendgrid.com/settings/api_keys).

```bash
echo "export SENDGRID_API_KEY='YOUR_API_KEY'" > sendgrid.env
echo "sendgrid.env" >> .gitignore
source ./sendgrid.env
```

```bash
mvn package
cd examples
javac -classpath ./jackson-databind-2.7.3.jar:./jackson-annotations-2.7.0.jar:./jackson-core-2.7.3.jar:/{path_to}/java-http-client-2.0.0-jar.jar:. Example.java && java -classpath ./jackson-databind-2.7.3.jar:./jackson-annotations-2.7.0.jar:./jackson-core-2.7.3.jar:/{path_to}/java-http-client-2.0.0-jar.jar:. Example
```

## Roadmap

If you are intersted in the future direction of this project, please take a look at our [milestones](https://github.com/sendgrid/java-http-client/milestones). We would love to hear your feedback.

## How to Contribute

We encourage contribution to our projects please see our [CONTRIBUTING](https://github.com/sendgrid/java-http-client/blob/master/CONTRIBUTING.md) guide for details.

Quick links:

- [Feature Request](https://github.com/sendgrid/java-http-client/blob/master/CONTRIBUTING.md#feature_request)
- [Bug Reports](https://github.com/sendgrid/java-http-client/blob/master/CONTRIBUTING.md#submit_a_bug_report)
- [Sign the CLA to Create a Pull Request](https://github.com/sendgrid/java-http-client/blob/master/CONTRIBUTING.md#cla)
- [Improvements to the Codebase](https://github.com/sendgrid/java-http-client/blob/master/CONTRIBUTING.md#improvements_to_the_codebase)

# About

java-http-client is guided and supported by the SendGrid [Developer Experience Team](mailto:dx@sendgrid.com).

java-http-client is maintained and funded by SendGrid, Inc. The names and logos for java-http-client are trademarks of SendGrid, Inc.

![SendGrid Logo]
(https://assets3.sendgrid.com/mkt/assets/logos_brands/small/sglogo_2015_blue-9c87423c2ff2ff393ebce1ab3bd018a4.png)
