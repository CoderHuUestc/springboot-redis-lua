package com.michaelfreeman.redis.lua.test;

import com.michaelfreeman.redis.lua.RedisLua;

@RedisLua
public interface TestService {
    @RedisLua(lua = "local goodsKey = KEYS[1] local buyNum = ARGV[1] buyNum=tonumber(buyNum)" +
            " local goodsNum = redis.call('get',goodsKey) goodsNum=tonumber(goodsNum)" +
            "if goodsNum >= buyNum then return tostring(redis.call('decrby',goodsKey,buyNum))" +
            " else return '-1'" +
            " end",
            keysCount = 1,
            argsCount = 1)
    public String saleGoods(String param, String buyNum);

    @RedisLua(lua = "local goodsKey = KEYS[1] local buyNum = ARGV[1] buyNum=tonumber(buyNum) return tostring(redis.call('get',goodsKey))",keysCount = 1,argsCount = 1)
    public String testExist(String param, String buyNum);
}
