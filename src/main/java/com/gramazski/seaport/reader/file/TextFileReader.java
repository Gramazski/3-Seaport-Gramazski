package com.gramazski.seaport.reader.file;

import com.gramazski.seaport.exception.DataReaderException;
import com.gramazski.seaport.reader.AbstractReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by gs on 20.12.2016.
 */
public class TextFileReader extends AbstractReader {
    public String read(String sourceName) throws DataReaderException {
        File dataFile = new File(sourceName);

        if (!dataFile.exists()){
            throw new DataReaderException("Data file does not exist. File name: " + sourceName);
        }

        if (!dataFile.canRead()){
            throw new DataReaderException("File can not be read. File name: " + sourceName);
        }

        if (!dataFile.isFile()){
            throw new DataReaderException("Is not a file! File name: " + sourceName);
        }

        Scanner fileScanner;

        try {
            fileScanner = new Scanner(dataFile);
        } catch (FileNotFoundException ex) {
            throw new DataReaderException("File not found: " + sourceName, ex);
        }

        StringBuilder stringBuilder = new StringBuilder();

        while (fileScanner.hasNext()){
            stringBuilder.append(fileScanner.nextLine());
        }

        fileScanner.close();

        return stringBuilder.toString();
    }
}
