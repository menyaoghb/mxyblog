import com.mxy.system.service.SysUserRoleService;
import com.mxy.system.service.SysUserService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Scanner;

/**
 * @Description
 * @Author 孟小耀
 * @Date 2021/8/13 0013 10:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 注册用户
     */
        public static void main(String[] args){
            Scanner sc = new Scanner(System.in);
            while(sc.hasNextInt()){
                int bottle = sc.nextInt();
                if(bottle==0){
                    break;
                }
                System.out.println(bottle/2);
            }
        }

}
