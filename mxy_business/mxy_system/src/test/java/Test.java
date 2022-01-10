import com.mxy.common.core.entity.SysUser;
import com.mxy.common.core.entity.SysUserRole;
import com.mxy.system.service.SysUserRoleService;
import com.mxy.system.service.SysUserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void contextLoads() {
        // 注册用户
        SysUser sysUserEntity = new SysUser();
        sysUserEntity.setUserName("mxy");
        sysUserEntity.setPassword(bCryptPasswordEncoder.encode("123456"));
        // 设置用户状态
        sysUserEntity.setStatus("NORMAL");
        sysUserService.save(sysUserEntity);
        // 分配角色 1:ADMIN 2:USER
        SysUserRole sysUserRoleEntity = new SysUserRole();
        sysUserRoleEntity.setRoleId(2L);
        sysUserRoleEntity.setUserId(Long.parseLong(sysUserEntity.getUserId()));
        sysUserRoleService.save(sysUserRoleEntity);
    }
}
