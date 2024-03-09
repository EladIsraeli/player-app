package com.example.playersapp.utils.csv;

import com.example.playersapp.utils.csv.validation.CsvPlayerInsertValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Component("ChunksCsvImporter")
public class ChunksCsvImporter implements CsvImporter {

    @Autowired
    private CsvPlayerInsertValidation csvPlayerInsertValidation;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChunksCsvImporter.class);

    @Value("${app.jdbc.url}")
    private String jdbcUrl;

    @Value("${app.jdbc.username}")
    private String username;

    @Value("${app.jdbc.password}")
    private String password;

    @Value("${app.chunk.size}")
    private int chunkSize;

    @Value("${app.csv.separator:,}")
    private String csvSeparator;
    private List<String> errorRows = new ArrayList<>();
    private int successfulInserts = 0;

    public void processAndInsertCsv(Resource filePath, String insertSql) {
        long startTime = System.currentTimeMillis();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             BufferedReader reader = new BufferedReader(new InputStreamReader(filePath.getInputStream()));) {
            connection.setAutoCommit(false);
            List<String[]> batch = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = parseLine(line);
                    if (data.length > 0) {
                        csvPlayerInsertValidation.validateRow(data);
                        batch.add(data);

                        if (batch.size() == chunkSize) {
                            processBatch(batch, insertSql, connection);
                            batch.clear();
                        }
                    }
                } catch (Exception e) {
                    errorRows.add(line);
                    LOGGER.error("Corrupted row: {} | Error: {}", line, e.getMessage(), e);
                }
            }

            // Process any remaining rows
            if (!batch.isEmpty()) {
                processBatch(batch, insertSql, connection);
            }

            connection.commit();

        } catch (Exception e) {
            LOGGER.error("Error processing CSV file: {}", e.getMessage(), e);
        } finally {
            printSummary();
            long endTime = System.currentTimeMillis();
            LOGGER.info("CSV import took: {} ms", (endTime - startTime));
        }
    }

    private void processBatch(List<String[]> batch, String insertSql, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
            for (String[] data : batch) {
                statement.setString(1, data[0]); // playerID is String

                if (data[1].isEmpty()) {
                    statement.setNull(2, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(2, Integer.parseInt(data[1])); // birthYear
                }

                if (data[2].isEmpty()) {
                    statement.setNull(3, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(3, Integer.parseInt(data[2])); // birthMonth
                }

                if (data[3].isEmpty()) {
                    statement.setNull(4, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(4, Integer.parseInt(data[3])); // birthDay
                }

                statement.setString(5, data[4]); // birthCountry
                statement.setString(6, data[5]); // birthState
                statement.setString(7, data[6]); // birthCity

                if (data[7].isEmpty()) {
                    statement.setNull(8, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(8, Integer.parseInt(data[7])); // deathYear
                }

                if (data[8].isEmpty()) {
                    statement.setNull(9, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(9, Integer.parseInt(data[8])); // deathMonth
                }

                if (data[9].isEmpty()) {
                    statement.setNull(10, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(10, Integer.parseInt(data[9])); // deathDay
                }

                statement.setString(11, data[10]); // deathCountry
                statement.setString(12, data[11]); // deathState
                statement.setString(13, data[12]); // deathCity
                statement.setString(14, data[13]); // nameFirst
                statement.setString(15, data[14]); // nameLast
                statement.setString(16, data[15]); // nameGiven

                if (data[16].isEmpty()) {
                    statement.setNull(17, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(17, Integer.parseInt(data[16])); // weight
                }

                if (data[17].isEmpty()) {
                    statement.setNull(18, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(18, Integer.parseInt(data[17])); // height
                }

                statement.setString(19, data[18]); // bats
                statement.setString(20, data[19]); // throws
                statement.setString(21, data[20]); // debut
                statement.setString(22, data[21]); // finalGame
                statement.setString(23, data[22]); // retroID
                statement.setString(24, data[23]); // bbrefID

                statement.addBatch();
            }
            statement.executeBatch();
            successfulInserts += batch.size();
        } catch (NumberFormatException e) {
            LOGGER.error("Error converting string to integer: {}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error processing batch: {}", e.getMessage(), e);
        }
    }
    private String[] parseLine(String line) {
        return line.split(csvSeparator);
    }

    private void printSummary() {
        LOGGER.info("CSV Import Summary:");
        LOGGER.info("Successful inserts: {}", successfulInserts);
        LOGGER.info("Failed inserts: {}", errorRows.size());
        if (!errorRows.isEmpty()) {
            String allErrors = String.join("\n", errorRows);
            LOGGER.info("Failed to process rows:\n{}", allErrors);
        }
    }
}
