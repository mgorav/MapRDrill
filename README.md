# Micro Service With MapR-DB Using Spring Boot + Drill

A Spring Boot service which connect to MapR cluster and demonstrate querying using
 apache Drill
 
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
