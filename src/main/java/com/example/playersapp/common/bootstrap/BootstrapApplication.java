package com.example.playersapp.common.bootstrap;

import com.example.playersapp.utils.csv.PlayerStatements;
import com.example.playersapp.utils.csv.CsvImporter;
import com.example.playersapp.utils.health.ApplicationReadinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class BootstrapApplication implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapApplication.class);

    @Autowired
    @Qualifier("ChunksCsvImporter")
    private CsvImporter csvImporter;

    @Value("${app.csv.filePath}")
    private Resource csvFileResource;

    @Autowired
    private ApplicationReadinessService readinessService;
    @Override
    public void run(ApplicationArguments args) {
        try {
            this.csvImporter.processAndInsertCsv(this.csvFileResource, PlayerStatements.INSERT_PLAYERS);
            this.readinessService.setReady(true);
        } catch (Exception e) {
            LOGGER.error("Critical error during CSV processing: {}", e.getMessage());
            throw new RuntimeException("Failed to process CSV, terminating application.", e);
        }
    }
}
