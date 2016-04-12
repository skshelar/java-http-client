```bash
cd http
mvn package
cd ../examples
javac -classpath ./commons-logging-1.2.jar:./httpcore-4.4.4.jar:./httpclient-4.5.2.jar:./jackson-databind-2.7.3.jar:./jackson-annotations-2.7.0.jar:./jackson-core-2.7.3.jar:../http/target/http-1.0-SNAPSHOT.jar:. Example.java && java -classpath ./commons-logging-1.2.jar:./httpcore-4.4.4.jar:./httpclient-4.5.2.jar:./jackson-databind-2.7.3.jar:./jackson-annotations-2.7.0.jar:./jackson-core-2.7.3.jar:../http/target/http-1.0-SNAPSHOT.jar:. Example
```