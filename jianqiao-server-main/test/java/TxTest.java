import com.MainApplication;
import com.zc.main.dubbo.service.user.DubboUserService;
import com.zc.main.entity.member.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xwolf
 * @since 1.8
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@EnableAutoConfiguration
@EnableCaching
public class TxTest {


    @Autowired
    private DubboUserService dubboUserService;

    @Test
    public void save(){

        Member user = new Member();
        user.setNickname("testdsd----");
        user.setHunterNum(32L);
        user.setName("test....");
        dubboUserService.insert(user);

    }


}
