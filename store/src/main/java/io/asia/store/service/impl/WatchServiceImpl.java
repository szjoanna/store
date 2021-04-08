package io.asia.store.service.impl;

import io.asia.store.config.BatchConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.nio.file.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class WatchServiceImpl implements Runnable{
    private final JobLauncher jobLauncher;
    private final BatchConfig batchConfig;

    @Override
    public void run() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("C:\\Users\\szela\\Desktop\\java\\store-app");
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey watchKey;
            while((watchKey = watchService.take()) != null) {
                watchKey.pollEvents().forEach(event -> {
                    Path filePath = Paths.get("C:\\Users\\szela\\Desktop\\java\\store-app", event.context().toString());
                    if(Files.isRegularFile(filePath)) {
                        try {
                            jobLauncher.run(batchConfig.csvToDatabase(filePath.toString()), new JobParametersBuilder()
                                    .addString("JobID",  String.valueOf(System.currentTimeMillis())).toJobParameters());
                        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
                            log.error(e.getMessage(), e);
                        }
                    }
                });
                watchKey.reset();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
