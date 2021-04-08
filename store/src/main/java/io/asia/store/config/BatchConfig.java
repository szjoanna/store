package io.asia.store.config;

import io.asia.store.domain.dao.Product;
import io.asia.store.procesor.ProductProcesor;
import io.asia.store.procesor.ProductReader;
import io.asia.store.procesor.ProductWriter;
import io.asia.store.procesor.model.ProductCSV;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@RequiredArgsConstructor
@Configuration
public class BatchConfig {
    private final ProductProcesor productProcesor;
    private final ProductReader productReader;
    private final ProductWriter productWriter;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public Job csvToDatabase(String filePath) {
        return jobBuilderFactory.get("csvToJobProcessor")
                .incrementer(new RunIdIncrementer())
                .flow(csvToDataBaseStep(filePath))
                .end()
                .build();
    }

    private Step csvToDataBaseStep(String filePath) {
        return stepBuilderFactory.get("stepDescription")
                .<ProductCSV, Product>chunk(2)
                .reader(productReader.read(filePath))
                .processor(productProcesor)
                .writer(productWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    private TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("csv_batch");
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }
}
