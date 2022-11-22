import com.alibaba.fastjson.JSONObject;
import com.mxy.common.core.entity.SysBookmarks;
import com.mxy.system.service.SysBookmarksService;
import com.mxy.system.service.SysUserRoleService;
import com.mxy.system.service.SysUserService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author 孟小耀
 * @Date 2021/8/13 0013 10:40
 */
@Component
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysBookmarksService sysBookmarksService;

    /**
     * 注册用户
     */
        public void main(String[] args){
            File file = new File("C:\\Users\\mengyao\\Documents\\bookmarks.txt");
            String json = txt2String(file);
            String[] data = json.split("`");
            List<Map<String,String>> maps = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                String datum = data[i];
                String newT = datum.substring(datum.indexOf("*")+1,datum.indexOf("*",datum.indexOf("*") + 1));
                String newL = datum.substring(datum.lastIndexOf("*",datum.lastIndexOf("*")-1));
                String newR = newL.substring(newL.indexOf("*")+1,newL.indexOf("*",newL.indexOf("*") + 1));
                SysBookmarks sysBookmarks = new SysBookmarks();
                sysBookmarks.setUrl(newT);
                sysBookmarks.setName(newR);
                sysBookmarks.setCreateUser("admin");
                sysBookmarks.setCreateTime(new Date());
                sysBookmarksService.save(sysBookmarks);
                System.out.println("数据[" + i + "]：" + JSONObject.toJSONString(sysBookmarks));
            }
            System.out.println("数据执行完毕");
        }


    public String txt2String(File file){
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result = result + "\n" +s;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



}
