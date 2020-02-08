# springdata-v4-elasticsearch-barebones
Barebones repository demo for Springdata 4.x, Elasticsearch 7.5.1 and Springboot 2.3.x

## Requesites
1. Docker
 + Elasticsearch 7.5.1
 + Tomcat 9
2. Maven

## Setup
### Tomcat
Start the webserver at localhost port 8080
```
docker run -it --rm -p 8888:8080 tomcat:9.0
```
Test by accessing it through a browser. _A 404 status will be displayed_
```
http://container-ip:8888
```
### Elasticsearch
Download Elasticsearch 7.5.1 image from docker
```
docker pull docker.elastic.co/elasticsearch/elasticsearch:7.5.1
```

Start Elasticsearch from docker as single-node.
_Using docker-compose for multiple clusters is beyond this repo._
```
docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.5.2
```

### Maven Dependencies

Add maven dependencies then run
```
mvn clean install package
```

### Copy .war to docker
To see the changes in the web server
```
docker cp api-container.target. mycontainer:/foo.txt
```
