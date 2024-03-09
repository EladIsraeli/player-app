package com.example.playersapp.utils.csv.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CsvPlayerInsertValidation {
    private Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?"); // Regex for validating numbers
    private Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}"); // Simple date format yyyy-mm-dd
    private int expectedColumns = 24; // Adjust based on your CSV structure

    public void validateRow(String[] rowData) {
        if (rowData.length != expectedColumns) {
            throw new IllegalArgumentException("Invalid row data, expectedColumns is not equal to actual length");
        }

        // Example validation: Check if the first column is a valid number
//        if (!numberPattern.matcher(rowData[0]).matches()) {
//           throw new IllegalArgumentException("Invalid row data, first column is not a number");
//        }
//
//        // Example validation: Check if the third column is a valid date (simple yyyy-mm-dd format)
//        if (!datePattern.matcher(rowData[2]).matches()) {
//            throw new IllegalArgumentException("Invalid row data, first column is not a date");
//        }
    }
}
