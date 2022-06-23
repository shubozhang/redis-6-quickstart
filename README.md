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























