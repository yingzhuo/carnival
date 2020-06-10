-- 分布式锁 (锁定)

local key = KEYS[1];
local value = ARGV[1];
local ttl = ARGV[2];

if redis.call('SETNX', key, value) == 1 then
    redis.call('EXPIRE', key, ttl);
    return 1;
else
    return 0;
end