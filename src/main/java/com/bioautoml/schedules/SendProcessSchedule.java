package com.bioautoml.schedules;

import com.bioautoml.domain.schedules.SendProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SendProcessSchedule {

    @Autowired
    private SendProcessService sendProcessService;

    @Scheduled(fixedDelay = 20000)
    public void checkIfSendProcessToExec() {
        this.sendProcessService.check();
    }

}
