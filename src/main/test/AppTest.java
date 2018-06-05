import com.springboot.Application;
import com.springboot.model.Message;
import com.springboot.mq.MsgProducer;
import com.springboot.service.IMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 测试
 *
 * @author hb
 * @create 2018-06-05 13:13
 **/
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class AppTest {

    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Autowired
    @Qualifier("messageService")
    private IMessageService messageService;

    @Autowired
    private MsgProducer msgProducer;

    @Test
    public void testFindMessage(){
        Message message = messageService.selectByPrimaryKey(1);
        logger.debug("nickName: {}", message.getNickName());
    }

    @Test
    public void testMsgProduce() {
        final String msg = "aaa";
        msgProducer.send(msg);
    }
}
