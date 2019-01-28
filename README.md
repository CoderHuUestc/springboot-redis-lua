# SPRING-BOOT-REDIS-LUA-STARTER

## SpringBoot引入lua注解,实现零lua脚本配置

### 使用方法
引入RedisLuaConfiguration注解
```java
@SpringBootApplication
@RedisLuaConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> template = new RedisTemplate<String, T>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
```
在方法上配置RedisLua注解，注意key参数在前，arg参数在后
demo如下
```java
 @RedisLua(lua = "local goodsKey = KEYS[1] " +
  "local buyNum = ARGV[1] " +
  "buyNum=tonumber(buyNum) " +
  "return tostring(redis.call('get',goodsKey))",
  keysCount = 1,
  argsCount = 1)
  public String testExist(String param, String buyNum);
```

```java
/**
 * 执行前现在redis执行 set param 10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class RedisLuaTest {

    @Resource
    private TestService testService;

    @Test
    public void test() {
        Assert.assertEquals("1",testService.testExist("param", "111"));
    }
}

```