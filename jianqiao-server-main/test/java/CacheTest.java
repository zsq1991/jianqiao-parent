/*
import com.MainApplication;
import com.zc.main.dubbo.service.user.DubboUserService;
import com.zc.main.entity.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

*/
/**
 * @author xwolf
 * @since 1.8
 **//*


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@EnableAutoConfiguration
@EnableCaching
public class CacheTest {


    @Autowired
    private DubboUserService dubboUserService1;

    @Test
    public void save(){
        User user = new User();
        user.setUserName("测试....");
        user.setUserPassword("324345");
        user.setGender(1);
        user.setRealName("方式发送到");
        user.setAddress("fsdfsdf防守打法");
        dubboUserService1.saveAndModify(user);
    }


}
*/
