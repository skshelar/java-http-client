[![Build Status](https://travis-ci.org/sendgrid/java-http-client.svg?branch=master)](https://travis-ci.org/sendgrid/java-http-client) [![Maven](https://maven-badges.herokuapp.com/maven-central/com.sendgrid/java-http-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.sendgrid/java-http-client)

**Quickly and easily access any RESTful or RESTful-like API.**

If you are looking for the SendGrid API client library, please see [this repo](https://github.com/sendgrid/sendgrid-java).

# Announcements

All updates to this project is documented in our [CHANGELOG](https://github.com/sendgrid/java-http-client/blob/master/CHANGELOG.md).

# Installation

## Prerequisites

- Java version Oracle JDK 7, 8 or OpenJDK 7

## Install via Maven w/ Gradle

```groovy
...
dependencies {
  ...
  compile 'com.sendgrid:java-http-client:2.3.4'
}

repositories {
  mavenCentral()
}
...
```

### Maven

```xml
<dependency>
    <groupId>com.sendgrid</groupId>
    <artifactId>java-http-client</artifactId>
    <version>2.3.4</version>
</dependency>
```

`mvn install`

## Install via Fat Jar

[Download](http://repo1.maven.org/maven2/com/sendgrid/java-http-client/2.3.4/java-http-client-2.3.4-jar.jar)

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
requestHeaders.put("Authorization", "Bearer YOUR_API_KEY");
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
javac -classpath {path_to}/sendgrid-java-http-client-2.3.4-jar.jar:. Example.java && java -classpath {path_to}/sendgrid-java-http-client-2.3.4-jar.jar:. Example
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
(https://uiux.s3.amazonaws.com/2016-logos/email-logo%402x.png)
