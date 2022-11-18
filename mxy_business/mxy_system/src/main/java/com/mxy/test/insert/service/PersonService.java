package com.mxy.test.insert.service;

import com.mxy.test.insert.mapper.PersonMapper;
import com.mxy.test.insert.entity.Person;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mengyao
 */
@Component
public class PersonService {

    private static final int THREAD_COUNT = 4;
    @Resource
    private PersonMapper personMapper;
    @Autowired
    private ThreadPoolExecutor executor;
    private AtomicInteger integer = new AtomicInteger();
    private Random random = new Random();
    private String[] names = {"紫南", "安蕾", "春雁", "凌香", "凡梦", "雅琴", "寻文", "青筠", "小萱","访烟", "妙竹", "千青", "幻灵", "天荷", "春竹", "之瑶", "代云", "芷蕾"};
    private String[] addrs = {"冷泉亭", "涵秋馆", "凌虚阁", "倒影楼", "起云台", "凝辉殿", "沉心堂", "春泽斋", "听雨轩", "寒碧山庄", "玉兰山房", "松陵酒家", "九孔桥", "银锄湖", "洞天深处"};
    private String[] companys = {"缕月云开", "山高水长", "上下天光", "菊院荷风", "坐石临流", "水木明瑟", "雷峰夕照", "一碧万顷", "梧竹幽居", "夹镜鸣琴"};

    @XxlJob("insertList")
    public void insertList() {
        XxlJobHelper.log("本轮任务开始，总任务数：" + THREAD_COUNT);
        long start = System.currentTimeMillis();
        AtomicLong end = new AtomicLong();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(() -> {
                try {
                    for (int j = 0; j < 20; j++) {
                        personMapper.insertList(getPersonList(5000));
                    }
                    end.set(System.currentTimeMillis());
                    XxlJobHelper.log("本轮任务耗时：" + (end.get() - start) + "____已执行" + integer.addAndGet(1) + "个任务" + "____当前队列任务数" + executor.getQueue().size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            try {
                executor.execute(thread);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ArrayList<Person> getPersonList(int count) {
        ArrayList<Person> persons = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            persons.add(getPerson());
        }
        return persons;
    }

    private Person getPerson() {
        Person person = Person.builder()
                .name(names[random.nextInt(names.length)])
                .phone(18800000000L + random.nextInt(88888888))
                .salary(new BigDecimal(random.nextInt(99999)))
                .company(companys[random.nextInt(companys.length)])
                .ifSingle(random.nextInt(2))
                .sex(random.nextInt(2))
                .address(addrs[random.nextInt(addrs.length)])
                .createUser(names[random.nextInt(names.length)]).build();
        return person;
    }
}
