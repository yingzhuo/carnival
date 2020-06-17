-- 分布式信号量 (锁定)

local key = KEYS[1];
local value = ARGV[1];
local total = tonumber(ARGV[2]);
local ttl = ARGV[3];

-- 可重入
if redis.call("SISMEMBER", key, value) == 1 then
    return 1;
end

-- 数量检查
if redis.call('SCARD', key) >= total then
    return 0;
end

if redis.call('SADD', key, value) == 1 then
    if redis.call('SCARD', key) == 1 then
        redis.call('EXPIRE', key, ttl);
    end
    return 1;
else
    return 0;
end