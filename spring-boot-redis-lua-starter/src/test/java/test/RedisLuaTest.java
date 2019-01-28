package test;

import com.michaelfreeman.redis.lua.test.Application;
import com.michaelfreeman.redis.lua.test.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 执行前现在redis执行 set param 10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@SuppressWarnings("unchecked")
public class RedisLuaTest {

    @Resource
    private TestService testService;

    @Test
    public void test1() {
        System.out.println("michael   " + testService.saleGoods("param", "3"));
    }

    @Test
    public void test2() {
        System.out.println("michael   " + testService.testExist("param", "111"));
    }
}
