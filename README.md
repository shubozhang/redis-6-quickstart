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


### String
* Redis Strings are binary safe, this means that a Redis string can contain any kind of data, for instance a JPEG image or 
a serialized Ruby object. 
* A String value can be at max 512 Megabytes in length.

| commands                       | usage                                                                              |
|--------------------------------|------------------------------------------------------------------------------------|
| `set <key> <value>`            | upsert a <key, value>                                                              |
| `get <key>`                    | get a value by key                                                                 |         
| `append <key> <value>`         | append value to the end of original value                                          |
| `strlen <key>`                 | get the length of the key                                                          |
| `setnx <key> <value>`          | only insert when key does not exist                                                |
| `incr  <key>`                  | (only works for number) increase value by 1. If pre_value is null, new value is 1  |
| `decr  <key>`                  | (only works for number) decrease value by 1. If pre_value is null, new value is -1 |
| `incrby / decrby  <key><step>` | (only works for number) increase / decrease value by step.                         |
