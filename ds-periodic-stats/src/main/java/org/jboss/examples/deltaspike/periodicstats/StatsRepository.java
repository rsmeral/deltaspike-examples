package org.jboss.examples.deltaspike.periodicstats;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class StatsRepository implements Serializable {

    public static final String INTER_ARRIVAL_TIME = "interarrival_time";

    public static final String MESSAGE_WORD_COUNT = "message_word_count";

    private final Map<String, List<Long>> repo = new HashMap<String, List<Long>>();

    public StatsRepository() {
    }

    public void add(String key, Long value) {
        synchronized (repo) {
            List<Long> list = repo.get(key);
            if (list == null) {
                repo.put(key, new ArrayList<Long>());
            }
            repo.get(key).add(value);
        }
    }

    public double getAverage(String key) {
        synchronized (repo) {
            List<Long> values = repo.get(key);
            if (values == null || values.isEmpty()) {
                return 0;
            }
            double result = 0;
            for (long val : values) {
                result += val;
            }
            return result / values.size();
        }
    }

    public int getCount(String key) {
        List<Long> list = repo.get(key);
        return (list == null) ? 0 : list.size();
    }

    public void clear(String key) {
        synchronized (repo) {
            repo.get(key).clear();
        }
    }

    public void clearAll() {
        synchronized (repo) {
            repo.clear();
        }
    }

}
