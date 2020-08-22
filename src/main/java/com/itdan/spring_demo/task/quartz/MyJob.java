package com.itdan.spring_demo.task.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class MyJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("quartz MyJob date:" + new Date().getTime());
    }
}
