-- 分布式信号量 (解锁)

local key = KEYS[1];
local value = ARGV[1];

return redis.call('SREM', key, value) == 1;