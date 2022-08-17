# Redis 6.2 Quickstart

1. [Redis VS Memcached](#redis)
2. [Setup](#setup)
3. [Data Types](#data_types)
   1. [Strings](#strings)
   2. [Lists](#lists)
   3. [Sets](#sets)
   4. [Hash](#hash)
   5. [Zset](#zset)

## Redis VS Memcached  <a name="redis"></a>
Redis

* support more data types: `String, List, Set, Hash, Zset`, new types: `Bitmaps, HyperLogLog, Geospatial`.
* support persistence
* single-thread

## Setup <a name="setup"></a>
1. Host Redis at local
   * [docker-compose file](redis-6.2/docker-compose.yml)

2. Access `redis-cli`
   * `docker exec -it <container_id> redis-cli`
       * example: `docker exec -it 534f0c59c14f5fad3945396574d3eabd61f570e1e8f2d089331c94b204413063 redis-cli`

## Data Types <a name="data_types"></a>
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

### Strings <a name="strings"></a>

* Redis Strings are binary safe, this means that a Redis string can contain any kind of data, for instance a JPEG image
  or a serialized Ruby object.
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
| `getset <key><value>`                 | update<k> to new value and return old one                                          |

* String (value, not key) is stored as Simple Dynamic String(SDS) and mutable. It implements like Java ArrayList.

### Lists <a name="lists"></a>

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
| `linsert <key>  before <value><newvalue>` | insert<newvalue> after <value>                                 |
| `lrem <key><n><value>`                    | delete n values from left                                      |
| `lset<key><index><value>`                 | update value at the index                                      |

* `ziplist` is used when key has few elements
* `quicklist` is used when key has more elements

### Sets <a name="sets"></a>

* auto remove duplicates
* unordered
* `O(1)` on add / delete / get operations

| commands                        | usage                                                                 |
|---------------------------------|-----------------------------------------------------------------------|
| sadd<key><value1><value2> ..... | Add one or more values to the key. Dup values will be ignored.        |
| smembers<key>                   | Get all values from the key.                                          |
| sismember<key><value>           | Check if key contains the value. If true, returns 1. Otherwise 0.     |
| scard<key>                      | Return the count of all values for the key.                           |
| srem<key><value1><value2> ....  | Remove values from the key.                                           |
| spop<key>                       | Pop a value from the key randomly.                                    |
| srandmember<key><n>             | Get n values from the key randomly.                                   |
| smove<source><destination>value | Move the value from source_key to destination_key.                    |
| sinter<key1><key2>              | Return inter values from two keys                                     |
| sunion<key1><key2>              | Return union values from two keys.                                    |
| sdiff<key1><key2>               | Return diff values between key1 and key2. (only values exist in key1) |

* In Redis, set is implemented in data structure dict.

### Hash <a name="hash"></a>

| commands                                          | usage                                                                |
|---------------------------------------------------|----------------------------------------------------------------------|
| `hset <key><field><value>`                        | add field to key set with value                                      |
| `hget <key1><field>`                              | get field's value from key set                                       |
| `hmset <key1><field1><value1><field2><value2>...` | add multiple hashes                                                  |
| `hexists<key1><field>`                            | check if field exists in key set                                     |
| `hkeys <key>`                                     | list all fields from the key set                                     |
| `hvals <key>`                                     | list all values from the key set                                     |
| `hincrby <key><field><increment>`                 | increment 1 on value for the field in key set                        |
| `hsetnx <key><field><value>`                      | add field to key set with value only an if only field does not exist |

### Zset(sorted set) <a name="zset"></a>
* `<k, s, v>`: `<k,v>` is unique and score is used for sorting (ascending order).

| commands                                                      | usage                                                                                 |
|---------------------------------------------------------------|---------------------------------------------------------------------------------------|
| zadd<key><score1><value1><score2><value2>…                    | 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。                                              |
| zrange<key><start><stop>  [WITHSCORES]                        | 返回有序集 key 中，下标在<start><stop>之间的元素带WITHSCORES，可以让分数一起和值返回到结果集。                         |
| zrangebyscore key minmax [withscores] [limit offset count]    | 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。 |
| zrevrangebyscore key maxmin [withscores] [limit offset count] | 同上，改为从大到小排列。                                                                          |
| zincrby<key><increment><value>                                | 为元素的score加上增量                                                                         |
| zrem<key><value>                                              | 删除该集合下，指定值的元素                                                                         |
| zcount<key><min><max>                                         | 统计该集合，分数区间内的元素个数                                                                      |
| zrank<key><value>                                             | 返回该值在集合中的排名，从0开始。                                                                     |

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
* `SUBSCRIBE channel1`
* `PUBLISH channel1 hello`

## Redis new data types
* Bitmaps
* HyperLogLog
* Geospatial

### Bitmaps

`setbit<key><offset><value>设置Bitmaps中某个偏移量的值（0或1）`

`getbit<key><offset>获取Bitmaps中某个偏移量的值`

`bitcount<key>[start end] 统计字符串从start字节到end字节比特值为1的数量`

bitop and(or/not/xor) <destkey> [key…]

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
would use SCARD to check the number of elements inside the set, which are unique since SADD will not re-add an existing
element.

While you don't really add items into an HLL, because the data structure only contains a state that does not include
actual elements, the API is the same:

```
    // Every time you see a new element, you add it to the count with PFADD.
    // Every time you want to retrieve the current approximation of the unique elements added with PFADD so far, you use the PFCOUNT.

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

## Transactions

Redis does not support ACID (atomicity, consistency, isolation, and durability).

* All the commands in a transaction are serialized and executed sequentially.
  A request sent by another client will never be served in the middle of the execution of a Redis Transaction.
  This guarantees that the commands are executed as a single isolated operation.
* In `queued` phase, no commands will be executed. Only the `EXEC` command triggers the execution of all the commands in
  the transaction.
* Do not guarantee atomicity. In `EXEC` phase, Redis does not support rollbacks that all the other commands will be
  executed
  even if some command fails during the transaction.
* Optimistic locking using check-and-set

Redis Transactions allow the execution of a group of commands in a single step, they are centered around the commands
`MULTI, EXEC, DISCARD` and `WATCH`. Redis Transactions make two important guarantees:

* All the commands in a transaction are serialized and executed sequentially.
  A request sent by another client will never be served in the middle of the execution of a Redis Transaction.
  This guarantees that the commands are executed as a single isolated operation.
* The EXEC command triggers the execution of all the commands in the transaction, so if a client loses the connection to
  the server in the context of a transaction before calling the EXEC command none of the operations are performed,
  instead if
  the EXEC command is called, all the operations are performed. When using the append-only file Redis makes sure to use
  a
  single write(2) syscall to write the transaction on disk. However, if the Redis server crashes or is killed by the
  system administrator in some hard way it is possible that only a partial number of operations are registered.
  Redis will detect this condition at restart, and will exit with an error. Using the redis-check-aof tool it is
  possible to
  fix the append only file that will remove the partial transaction so that the server can start again.

```
> multi // start transaction
> command1 // queued command1, but hasn't implemented yet
> command2
> ...
> exec // execute all queued commands
```

```
> multi // start transaction
> command1 // queued command1, but hasn't implemented yet
> command2
> ...
> discard // discard all queued commands
```

* Error in queued commands

```
> multi // start transaction
> command1 // queued command1, but hasn't implemented yet
> command2 // error
> ...
> exec // execute  aborted. Transaction discarded because of previous errors
```

* Error in exec commands

```
> multi // start transaction
> command1 // queued command1, but hasn't implemented yet
> command2 // queued successfully, but will occur error in execution
> command3 // queued
> exec // will exec command1 and command3, and only rollback command2 
```

Example

![Alt text](./images/transaction-example.png?raw=true "transaction-example")

* Pessimistic Lock
  Pessimistic Locking is when you lock the record for your exclusive use until you have finished with it. It has much
  better integrity than optimistic locking but requires you to be careful with your application design to avoid
  Deadlocks. To use pessimistic locking you need either a direct connection to the database (as would typically be the
  case in a two tier client server application) or an externally available transaction ID that can be used independently
  of the connection.

![Alt text](./images/pessimistic-lock.png?raw=true "pessimistic-lock")

* Optimistic Lock

Optimistic Locking is a strategy where you read a record, take note of a version number (other methods to do this
involve dates, timestamps or checksums/hashes) and check that the version hasn't changed before you write the record
back. When you write the record back you filter the update on the version to make sure it's atomic. (i.e. hasn't been
updated between when you check the version and write the record to the disk) and update the version in one hit.

If the record is dirty (i.e. different version to yours) you abort the transaction and the user can re-start it.

This strategy is most applicable to high-volume systems and three-tier architectures where you do not necessarily
maintain a connection to the database for your session. In this situation the client cannot actually maintain database
locks as the connections are taken from a pool and you may not be using the same connection from one access to the next.

It applies to high-frequency read scenarios, example, selling tickets.

![Alt text](./images/optimistic-lock.png?raw=true "optimistic-lock")

* WATCH explained
    * `WATCH <key>`
    * `UNWATCH`

So what is WATCH really about? It is a command that will make the EXEC conditional: we are asking Redis to perform the
transaction only if none of the WATCHed keys were modified. This includes modifications made by the client, like write
commands, and by Redis itself, like expiration or eviction. If keys were modified between when they were WATCHed and
when the EXEC was received, the entire transaction will be aborted instead.

| steps | thread-1             | thread-2                                 |
|-------|----------------------|------------------------------------------|
| 1     | `set balance 100`    |                                          |
| 2     | `watch balance`      | `watch balance`                          |
| 3     | `multi`              | `multi`                                  |
| 4     | `incrby 10`          | `incryby 20`                             |
| 5     | `exec` // return 110 |                                          |
| 6     |                      | `exec` // return nil (key did not exist) |
