package org.jboss.examples.deltaspike.periodicstats;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.scheduler.api.Scheduled;
import org.apache.deltaspike.scheduler.spi.Scheduler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

@Named
@RequestScoped
public class StatsController {

    @Inject
    private Scheduler<Job> scheduler;

    @Inject
    private StatsRepository repo;

    @Inject
    private StatsJob job;

    public void startProcessing() throws SchedulerException {
        scheduler.unwrap(org.quartz.Scheduler.class).start();
    }

    public void stopProcessing() throws SchedulerException {
        scheduler.unwrap(org.quartz.Scheduler.class).standby();
    }

    public boolean isProcessing() throws SchedulerException {
        return !scheduler.unwrap(org.quartz.Scheduler.class).isInStandbyMode();
    }

    public void reset() {
        repo.clearAll();
        job.reset();
    }

    // every second
    @Scheduled(cronExpression = "* * * * * ? *", onStartup = true)
    @ApplicationScoped
    public static class StatsJob implements Job {

        @Inject
        private MessageInfoBuffer store;

        @Inject
        private StatsRepository repo;

        private Date lastArrivalTime = null;

        @Override
        public void execute(JobExecutionContext jec) throws JobExecutionException {
            List<MessageInfo> buffer = store.getBuffer();
            synchronized (buffer) {

                for (MessageInfo info : buffer) {
                    repo.add(StatsRepository.MESSAGE_WORD_COUNT, (long) info.getText().split(" ").length);

                    if (lastArrivalTime == null) {
                        lastArrivalTime = info.getArrivalTime();
                    } else {
                        repo.add(StatsRepository.INTER_ARRIVAL_TIME, info.getArrivalTime().getTime() - lastArrivalTime.getTime());
                    }
                }

                store.clear();
            }
        }

        public void reset() {
            lastArrivalTime = null;
        }

    }

}
