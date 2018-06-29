# Micro Service With MapR-DB Using Spring Boot + Drill

A Spring Boot service which connect to MapR cluster and demonstrate querying using
 apache Drill.
 
 ## Overview
 My love affair with Spring Framework which  started almost 18 year back and is never ending... why not? as it allows me to express my logical thoughts in simplest, quick & easiest way... check out my github project that builds a data service API using Spring Boot + MapR + Apache Drill and demonstrates following concepts:

1. Query massive amount of data (BigData) in MapRDB using Apache Drill

2. Querying MapRDB with Apache Drill with command line interface

3. ......

**Note: What is Apache Drill?** Apache Drill is a low latency distributed ANSI SQL compliant query engine for large-volume datasets, including structured and semi-structured/nested data which is Inspired by Google’s Dremel. At the heart of it, Drill is a distributed SQL query engine. The core daemon in Drill is called "Drillbit." This is a service that is installed on all the data nodes in the Hadoop cluster. It's not a requirement to install Drill on all the data nodes. You can do certainly a partial set of nodes, but installing on all the nodes gives Drill the ability to achieve data locality at the query execution time.
Data locality is the ability to push down the processing to the node where the data lives, rather than trying to bring the data over the network at the query execution time.

**Query EXECUTION**

The SQL where it comes in from a client - this could be a JDBC client, ODBC, CLI, or a REST endpoint. The query comes in, and the query is accepted by a Drillbit. The Drillbit that accepts the request acts as a foreman or coordinator for that specific request. As a client application, you could directly submit to a Drillbit, or you could talk to your ZooKeeper quorum, which in turn would draw the request to a specific Drillbit.
Once a Drillbit receives the request, it passes the query and then it determines what is the best way to execute this particular query, or what is the most optimal way to execute the query.

**Query OPTIMIZAION**

Drill allows a variety of rule-based as well as cost-based optimizations, in addition to being aware of the data locality during the query planning.
Once the best query plan is determined, Drill splits the query plan into a number of pieces called fragments, query plan fragments. So the Drillbit, the coordinator Drillbit, talks to the ZooKeeper, determines what are the other Drillbits that are available in the cluster. Then it gets the location of the data. By combining that information, it determines what are other Drillbits that can handle this particular list of query plan fragments. Then it distributes the work to the other Drillbits in the cluster. Each Drillbit does its own processing of the query plan fragment. They return the results back to the original Drillbit, and the Drillbit returns the results to the client.




**Advantages of using MapR DRILL**

1. High Performance SQL Queries with Scale-Out Architecture

2. Schemaless Query Execution for Data Exploration

3. In-Place Analytics across Historical and Operational Data

4. Connectivity to Popular BI Tools through QDBC and JDBC interfaces

5. ANSI SQL Compliance

6. Integration with MapR-DB Secondary Indexes for Operational Analytics

7. Integration with Hive for Interactive Queries on Existing Hive Tables

8. Cluster Health and Resource Monitoring through MapR Control System

9. Integration into MapR Data Science Refinery for Augmenting Data Science Workflows

10. End-To-End Security for Data Accessed, Processed, and Analyzed

Big Data (ML,AI) is the future. What I think is one of the most important aspect in it, is to define a repeatable programming model & exposing data APIs (hence adding business value) My github project aims to demonstrate some of these aspects by combining data power of MapR with opinionated framework Spring Boot. Data Scientists/Engineers tends to ignore these aspects, as they are more into solving data aspects and data related business value addition ... which indeed is there prime goal. Let’s bridge this gap.

#dataapis #springbootdrill #springmapr #maprdrill #mapr #datamicroservices
 
## Prerequisites
Follow the steps specified in my github project - [SpringBootMapR](https://github.com/mgorav/SpringBootMapR)
to setup user.json

## Play time

Hit the URL - http://localhost:7777/users/Shawn/2010-07-09

output:

```yaml
[
    {
        "name": "Shawn",
        "yelpingSince": "2010-07-09",
        "support": null
    }
]
```

## Querying Using Sqlline console

Log into the container and issue following command:

```bash
$ sqlline -u jdbc:drill:zk=localhost:5181 -n mapr

0: jdbc:drill:zk=localhost:5181> select _id, name, yelping_since, support from dfs.`/apps/user` where yelping_since = '2010-07-09' and  name = 'Shawn';

```

Output:

```bash
+-------------------------+--------+----------------+----------+
|           _id           |  name  | yelping_since  | support  |
+-------------------------+--------+----------------+----------+
| -9BWZzz76TJs3BaaoS2Sgg  | Shawn  | 2010-07-09     | null     |
+-------------------------+--------+----------------+----------+
```
### todo - integrate with spring jdbc configurable from application.yml/properties
