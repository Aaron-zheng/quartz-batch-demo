package com.sightcorner.listener;


import com.sightcorner.quartz.model.ScheduleTask;
import com.sightcorner.quartz.service.IScheduleTaskService;
import com.sightcorner.quartz.service.ScheduleTaskService;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StartupListener implements ServletContextListener {


    private ScheduleTaskService scheduleTaskService = new ScheduleTaskService();

    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskCode("hellworld");
        scheduleTask.setTaskName("testname");
        scheduleTask.setTaskConf("0/10 * * * * ?");
        scheduleTask.setTaskClass("com.sightcorner.batch.impl.hello.HelloJob");
        scheduleTask.setTaskServerIp("127.0.0.1");
        scheduleTask.setStatus("1");
        List<ScheduleTask> list = new ArrayList<>();
        list.add(scheduleTask);

        try {

            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            scheduleTaskService.setScheduler(scheduler);
            scheduleTaskService.proceedTask(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
