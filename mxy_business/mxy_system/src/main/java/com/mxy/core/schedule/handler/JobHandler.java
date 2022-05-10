package com.mxy.core.schedule.handler;

import com.mxy.common.core.entity.SysEsData;
import com.mxy.common.core.utils.AutoNameUtils;
import com.mxy.common.core.utils.DateUtils;
import com.mxy.core.elasticsearch.EsServiceImpl;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 孟耀
 */
@Component
@Slf4j
public class JobHandler {

    private static String INDEX_NAME = "person";

    @Resource
    private EsServiceImpl esService;

    private AtomicInteger integer = new AtomicInteger();

    private String[] names = {"紫南", "安蕾", "春雁", "凌香", "凡梦", "雅琴", "寻文", "青筠", "小萱", "访烟", "妙竹", "千青", "幻灵", "天荷", "春竹", "之瑶", "代云", "芷蕾"};
    private String[] addrs = {"冷泉亭", "涵秋馆", "凌虚阁", "倒影楼", "起云台", "凝辉殿", "沉心堂", "春泽斋", "听雨轩", "寒碧山庄", "玉兰山房", "松陵酒家", "九孔桥", "银锄湖", "洞天深处"};
    private String[] companys = {"缕月云开", "山高水长", "上下天光", "菊院荷风", "坐石临流", "水木明瑟", "雷峰夕照", "一碧万顷", "梧竹幽居", "夹镜鸣琴"};

    @XxlJob("esDataInsOne")
    public void esDataIns() throws Exception {
        for (int i = 0; i < 137; i++) {
            Random random = new Random();
            XxlJobHelper.log(DateUtils.getFromNow(Integer.parseInt(DateUtils.getRandom(2))));
            XxlJobHelper.log(String.valueOf(Integer.parseInt(DateUtils.getRandom(9))));
            esService.insertRequest(INDEX_NAME, UUID.randomUUID().toString().replace("-",""), SysEsData.builder()
                    .dataId(UUID.randomUUID().toString().replace("-",""))
                    .name(AutoNameUtils.autoSurAndName())
                    .phone(18700000000L + random.nextInt(88888888))
                    .salary(new BigDecimal(random.nextInt(99999)))
                    .company(companys[random.nextInt(companys.length)])
                    .sex(random.nextInt(2))
                    .address(addrs[random.nextInt(addrs.length)] + companys[random.nextInt(companys.length)])
                    .createUser(names[random.nextInt(names.length)])
                    .createTime(new Date())
                    .status("0")
                    .isDelete("0")
                    .fieldOne(DateUtils.getNo(6))
                    .fieldTwo(DateUtils.getWeekStr(DateUtils.getStringDateShort()))
                    .fieldThree(DateUtils.getSeqWeek())
                    .fieldFour(DateUtils.getFromNow(Integer.parseInt(DateUtils.getRandom(1))))
                    .fieldFive(DateUtils.getTodayShort())
                    .fieldSix(DateUtils.getHour())
                    .fieldSeven(DateUtils.getTime())
                    .fieldEight(DateUtils.getTimeShort())
                    .fieldNine(Integer.parseInt(DateUtils.getRandom(9)))
                    .fieldTen(DateUtils.strToDate(DateUtils.dateToStr(new Date())))
                    .build());
        }
    }

    @XxlJob("esDataInsTwo")
    public void esDataInsTwo() throws Exception {
        for (int i = 0; i < 63; i++) {
            Random random = new Random();
            XxlJobHelper.log(DateUtils.getFromNow(Integer.parseInt(DateUtils.getRandom(2))));
            XxlJobHelper.log(String.valueOf(Integer.parseInt(DateUtils.getRandom(9))));
            esService.insertRequest(INDEX_NAME, UUID.randomUUID().toString().replace("-",""), SysEsData.builder()
                    .dataId(UUID.randomUUID().toString().replace("-",""))
                    .name(AutoNameUtils.autoSurAndName())
                    .phone(15100000000L + random.nextInt(88888888))
                    .salary(new BigDecimal(random.nextInt(99999)))
                    .company(companys[random.nextInt(companys.length)])
                    .sex(random.nextInt(2))
                    .address(addrs[random.nextInt(addrs.length)])
                    .createUser(names[random.nextInt(names.length)])
                    .createTime(new Date())
                    .status("0")
                    .isDelete("0")
                    .fieldOne(DateUtils.getNo(6))
                    .fieldTwo(DateUtils.getWeekStr(DateUtils.getStringDateShort()))
                    .fieldThree(DateUtils.getSeqWeek())
                    .fieldFour(DateUtils.getFromNow(Integer.parseInt(DateUtils.getRandom(2))))
                    .fieldFive(DateUtils.getTodayShort())
                    .fieldSix(DateUtils.getHour())
                    .fieldSeven(DateUtils.getTime())
                    .fieldEight(DateUtils.getTimeShort())
                    .fieldNine(Integer.parseInt(DateUtils.getRandom(9)))
                    .fieldTen(DateUtils.strToDate(DateUtils.dateToStr(new Date())))
                    .build());

        }
    }

}
