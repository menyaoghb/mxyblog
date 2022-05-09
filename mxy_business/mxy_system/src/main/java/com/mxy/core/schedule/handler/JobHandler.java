package com.mxy.core.schedule.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 孟耀
 */
@Component
@Slf4j
public class JobHandler {

    private static String INDEX = "person";

    @XxlJob("jobHandler")
    public void jobHandler() throws Exception {



        XxlJobHelper.log("test" + new Date());
    }

}
