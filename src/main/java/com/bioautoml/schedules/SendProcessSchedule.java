package com.bioautoml.schedules;

import com.bioautoml.domain.process.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SendProcessSchedule {

    @Autowired
    private ProcessService processService;

    @Scheduled(fixedDelay = 20000)
    public void checkIfSendProcess() {
        processService.check();
    }

}
