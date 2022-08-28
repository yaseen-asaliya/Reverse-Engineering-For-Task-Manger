package com.exalt.springboot.timeconflict;

import com.exalt.springboot.domain.aggregate.Task;
import com.exalt.springboot.domain.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimeConflict {
    private final int NOT_CONFLICT = 0;
    public final Logger LOGGER = LoggerFactory.getLogger(TimeConflict.class.getName());

    private ITaskService iTaskService;

    public TimeConflict(ITaskService iTaskService) {
        this.iTaskService = iTaskService;
    }

    public boolean isConflict(String start,String finish,int userId,int taskId) throws ParseException {
        LOGGER.info("Checking conflict...");
        boolean overlap = false;
        List<Task> tasks = iTaskService.getTasks(userId);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date startDate = dateFormat.parse(start);
        Date endDate = dateFormat.parse(finish);

        for (int i=0 ;i < tasks.size(); i++) {
            if(tasks.get(i).getId() != taskId){
                Date newStart =  dateFormat.parse(tasks.get(i).getStart());
                Date newEnd =  dateFormat.parse(tasks.get(i).getFinish());
                overlap = newStart.compareTo(endDate) <= NOT_CONFLICT && newEnd.compareTo(startDate) >= NOT_CONFLICT;
                if(overlap){
                    break;
                }
            }

        }
        return overlap;
    }
}
