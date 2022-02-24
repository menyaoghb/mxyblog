import com.alibaba.fastjson.JSONObject;
import com.mxy.system.info.config.Server;
import com.mxy.system.info.utils.MonitorServerUtil;
import com.mxy.system.service.SysUserRoleService;
import com.mxy.system.service.SysUserService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
            Server server = MonitorServerUtil.getServerRunInfo();
            System.out.println(JSONObject.toJSONString(server));
        }

}
