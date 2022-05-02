package ru.avtomaton.wikifacts.infra;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.entity.User;
import ru.avtomaton.wikifacts.service.FactService;
import ru.avtomaton.wikifacts.service.UserService;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Anton Akkuzin
 */
@Service
public class DataBaseCleaner {

    @Autowired
    private FactService factService;
    @Autowired
    private UserService userService;


    public DataBaseCleaner() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new NamedDaemonThreadFactory("DataBaseCleaner"));
        executorService.scheduleAtFixedRate(this::clean, 1, 1, TimeUnit.MINUTES);
    }

    private void clean() {
        List<Fact> allWithDeletedMark = factService.findAllWithDeletedMark();
        allWithDeletedMark.forEach(fact -> {
            User author = fact.getAuthor();
            userService.save(author);
        });
        factService.deleteAll(allWithDeletedMark);
    }

    private static class NamedDaemonThreadFactory implements ThreadFactory {

        private final String name;

        public NamedDaemonThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            final Thread thread = new Thread(r, name + "-thread");
            thread.setDaemon(true);

            return thread;
        }
    }
}
