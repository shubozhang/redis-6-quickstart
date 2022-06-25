# Redis 6.2 Quickstart

## Redis VS Memcached

Redis

* support more data types: `String, List, Set, Hash, Zset,` etc.
* support persistence
* single-thread

## Setup

1. Host Redis at local

* [docker-compose file](redis-6.2/docker-compose.yml)

2. Access `redis-cli`

* `docker exec -it <container_id> redis-cli`
  * example: `docker exec -it 534f0c59c14f5fad3945396574d3eabd61f570e1e8f2d089331c94b204413063 redis-cli`

## Data Types

`key commands`

| commands          | usage                                                                                 |
|-------------------|---------------------------------------------------------------------------------------|
| `keys * `         | list all keys                                                                         |
| `exists <key>`    | check if key exists                                                                   |
| `type <key>`      | check key's type                                                                      |
| `del <key>`       | delete key                                                                            |
| `unlink <key>`    | delete keys from keyspace and will do real deletion asynchronizely later              |
| `expire <key> 10` | setup ttl=10 seconds for a key                                                        |
| `ttl <key>`       | check key's expiration time in seconds.`-1` means never expire and `-2` means expired |
| `select <id>`     | select db                                                                             |
| `dbsize`          | check the number of keys                                                              |
| `flushdb`         | truncate current DB                                                                   |
| `flushall`        | truncate all DBs                                                                      |


### Strings
* Redis Strings are binary safe, this means that a Redis string can contain any kind of data, for instance a JPEG image or 
a serialized Ruby object. 
* A String value can be at max 512 Megabytes in length.

| commands                              | usage                                                                              |
|---------------------------------------|------------------------------------------------------------------------------------|
| `set <key> <value>`                   | upsert a <key, value>                                                              |
| `mset <k1><k2><v1><v2>...`            | upsert multiple <key, value>: atomic                                               |
| `get <key>`                           | get a value by key                                                                 |         
| `mget <k1><k2><...>`                  | get multiple value by key                                                          |         
| `append <key> <value>`                | append value to the end of original value                                          |
| `strlen <key>`                        | get the length of the key                                                          |
| `setnx <key> <value>`                 | upsert <k,v> when key does not exist                                               |
| `msetnx <key> <value>`                | upsert multiple <k, v> when key does not exist: atomic                             |
| `incr  <key>`                         | (only works for number) increase value by 1. If pre_value is null, new value is 1  |
| `decr  <key>`                         | (only works for number) decrease value by 1. If pre_value is null, new value is -1 |
| `incrby / decrby  <key><step>`        | (only works for number) increase / decrease value by step.                         |
| `getrange <key> <start><end>`         | get values by key range                                                            |
| `setrange  <key><start><value>`       | override value from index_start                                                    |
| `setex <key><expiration_time><value>` | upsert a <k, v> with ttl                                                           |
| `getset <key><value>`                 | update <k> to new value and return old one                                         |


* String (value, not key) is stored as Simple Dynamic String(SDS) and mutable. It implements like Java ArrayList.


### Lists
Redis Lists are simply lists of strings, sorted by insertion order. It is possible to add elements to a Redis List 
pushing new elements on the head (on the left) or on the tail (on the right) of the list, like double-linked list.

* The max length of a list is 2^32 - 1 elements (4294967295, more than 4 billion of elements per list).


| commands                                  | usage                                                          |
|-------------------------------------------|----------------------------------------------------------------|
| `lpush/rpush <k> <v1><v2>...`             | insert multiple values from left / right                       |
| `lrange <k> <start> <end>`                | get values from index_start to index_end (from left to right)  |
| `lpop/rpop  <key>`                        | pop a value from left / right                                  |
| `rpoplpush  <key1><key2>`                 | <key1> pops a value from right and inserts it into <key2> left |
| `lindex <key><index>`                     | get value by index (from left to right)                        |
| `llen <key>`                              | get the length of the key                                      |
| `linsert <key>  before <value><newvalue>` | insert <newvalue> after <value>                                |                                                               
| `lrem <key><n><value>`                    | delete n values from left                                      |                                                               
| `lset<key><index><value>`                 | update value at the index                                      |                                                               


* `ziplist` is used when key has few elements
* `quicklist` is used when key has more elements



### Sets
* auto remove duplicates
* unordered
* `O(1)` on add / delete / get operations


| commands                         | usage                                           |
|----------------------------------|-------------------------------------------------|
| sadd <key><value1><value2> ..... | 将一个或多个 member 元素加入到集合 key 中，已经存在的 member 元素将被忽略 |       
| smembers <key>                   | 取出该集合的所有值。                                      |                                                 
| sismember <key><value>           | 判断集合<key>是否为含有该<value>值，有1，没有0                  |                                                 
| scard<key>                       | 返回该集合的元素个数。                                     |                                                 
| srem <key><value1><value2> ....  | 删除集合中的某个元素。                                     |                                                 
| spop <key>                       | 随机从该集合中吐出一个值。                                   |                                                 
| srandmember <key><n>             | 随机从该集合中取出n个值。不会从集合中删除 。                         |                                                 
| smove <source><destination>value | 把集合中一个值从一个集合移动到另一个集合                            |                                                 
| sinter <key1><key2>              | 返回两个集合的交集元素。                                    |                                                 
| sunion <key1><key2>              | 返回两个集合的并集元素。                                    |                                                 
| sdiff <key1><key2>               | 返回两个集合的差集元素(key1中的，不包含key2中的)                   |                                                 


* In Redis, set is implemented in data structure  dict.


### Hash

| commands                                          | usage                                             |
|---------------------------------------------------|---------------------------------------------------|
| `hset <key><field><value>`                        | 给<key>集合中的  <field>键赋值<value>                     |
| `hget <key1><field>`                              | 从<key1>集合<field>取出 value                          |
| `hmset <key1><field1><value1><field2><value2>...` | 批量设置hash的值                                        |
| `hexists<key1><field>`                            | 查看哈希表 key 中，给定域 field 是否存在。                       |
| `hkeys <key>`                                     | 列出该hash集合的所有field                                 |
| `hvals <key>`                                     | 列出该hash集合的所有value                                 |
| `hincrby <key><field><increment>`                 | 为哈希表 key 中的域 field 的值加上增量 1   -1                  |
| `hsetnx <key><field><value>`                      | 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在 . |



### Zset(sorted set) 

* `<k, s, v>`: `<k,v>` is unique and score is used for sorting (ascending order).

| commands                                                      | usage                                                                                 |
|---------------------------------------------------------------|---------------------------------------------------------------------------------------|
| zadd  <key><score1><value1><score2><value2>…                  | 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。                                              |
| zrange <key><start><stop>  [WITHSCORES]                       | 返回有序集 key 中，下标在<start><stop>之间的元素带WITHSCORES，可以让分数一起和值返回到结果集。                         |
| zrangebyscore key minmax [withscores] [limit offset count]    | 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。 |
| zrevrangebyscore key maxmin [withscores] [limit offset count] | 同上，改为从大到小排列。                                                                          |
| zincrby <key><increment><value>                               | 为元素的score加上增量                                                                         |
| zrem  <key><value>                                            | 删除该集合下，指定值的元素                                                                         |
| zcount <key><min><max>                                        | 统计该集合，分数区间内的元素个数                                                                      |
| zrank <key><value>                                            | 返回该值在集合中的排名，从0开始。                                                                     |


* data structure for zset
  * hash is used to connect `value` and `score`, and keeps uniqueness of `<value, score>`
  * skip list: sorting values based on score


## Configuration
* unit: only support bytes
* case insensitive
* network:
  * bind:
  * protected-mode:
  * tcp-backlog:
  * timeout: 0. If 0, always alive. 
  * tcp-keepalive: 300 second. health check
* general:
  * daemonize: run as a daemon
  * pidfile
  * loglevel: debug / verbose / notice(default) /warning
  * logfile
  * databases: 16. Default is 0.
* security:
  * requirepass:
* limits:
  * maxclients: 10_000 default
  * maxmemory: 
  * maxmemory-policy:
    * volatile-lru: applied lru on keys with ttl
    * allkeys-lru: applied lru on all keys
    * volatile-random: random remove key from key set with ttl
    * allkeys-random: random remove key from all keys
    * volatile-ttl: remove min ttl keys
    * noeviction: no removal. Will return error when write is full.
  * maxmemory-samples: 


## Redis pub / sub

`SUBSCRIBE channel1`
`PUBLISH channel1 hello`


## Redis new data types
* Bitmaps
* HyperLogLog
* Geospatial

### Bitmaps
`setbit<key><offset><value>设置Bitmaps中某个偏移量的值（0或1）`

`getbit<key><offset>获取Bitmaps中某个偏移量的值`

`bitcount<key>[start end] 统计字符串从start字节到end字节比特值为1的数量`


bitop  and(or/not/xor) <destkey> [key…]

bitop是一个复合操作， 它可以做多个Bitmaps的and（交集） 、 or（并集） 、 not（非） 、 xor（异或） 操作并将结果保存在destkey中。

example:
`bitop and unique:users:and:20201104_03 unique:users:20201103unique:users:20201104`



### HyperLogLog

A HyperLogLog is a probabilistic data structure used in order to count unique things (technically this is referred to 
estimating the cardinality of a set). Usually counting unique items requires using an amount of memory proportional 
to the number of items you want to count, because you need to remember the elements you have already seen in the past 
in order to avoid counting them multiple times. However there is a set of algorithms that trade memory for precision: 
you end with an estimated measure with a standard error, which in the case of the Redis implementation is less than 1%. 
The magic of this algorithm is that you no longer need to use an amount of memory proportional to the number of items 
counted, and instead can use a constant amount of memory! 12k bytes in the worst case, or a lot less if your HyperLogLog 
(We'll just call them HLL from now) has seen very few elements.

HLLs in Redis, while technically a different data structure, are encoded as a Redis string, so you can call GET to 
serialize a HLL, and SET to deserialize it back to the server.

Conceptually the HLL API is like using Sets to do the same task. You would SADD every observed element into a set, and 
would use SCARD to check the number of elements inside the set, which are unique since SADD will not re-add an existing element.

While you don't really add items into an HLL, because the data structure only contains a state that does not include 
actual elements, the API is the same:

```
    Every time you see a new element, you add it to the count with PFADD.

    Every time you want to retrieve the current approximation of the unique elements added with PFADD so far, you use the PFCOUNT.

      > pfadd hll a b c d
      (integer) 1
      > pfcount hll
      (integer) 4

```
`pfadd <key>< element> [element ...]   add element HyperLogLog 中`

`pfcount<key> [key ...] count unique elements`

`pfmerge<destkey><sourcekey> [sourcekey ...]  merge one or multiple keys' elements into one key`

An example of use case for this data structure is counting unique queries performed by users in a search form every day.

### Geospatial

`geoadd<key>< longitude><latitude><member> [longitude latitude member...]   添加地理位置（经度，纬度，名称）`


`geopos  <key><member> [member...]  获得指定地区的坐标值`

`geodist<key><member1><member2>  [m|km|ft|mi ]  获取两个位置之间的直线距离`


`georadius<key>< longitude><latitude>radius  m|km|ft|mi   以给定的经纬度为中心，找出某一半径内的元素`


## Jedis




































