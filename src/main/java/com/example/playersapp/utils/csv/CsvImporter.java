package com.example.playersapp.utils.csv;

import org.springframework.core.io.Resource;

public interface CsvImporter {
    void processAndInsertCsv(Resource csvFilePath, String sqlStatement);
}
