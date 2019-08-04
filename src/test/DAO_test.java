import com.entity.Users;
import com.mapper.UsersMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DAO_test {
    private ApplicationContext applicationContext;
    private UsersMapper usersMapper;
    @Test
    public void selectById(){
        applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        usersMapper=(UsersMapper) applicationContext.getBean("usersMapper");
        Users users=usersMapper.selectByPrimaryKey(1);
        System.out.println(users.getPassword());
    }
}
