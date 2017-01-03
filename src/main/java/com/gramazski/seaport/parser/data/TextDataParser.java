package com.gramazski.seaport.parser.data;

import com.gramazski.seaport.parser.AbstractDataParser;

/**
 * Created by gs on 03.01.2017.
 */
public class TextDataParser extends AbstractDataParser {
    public String[] parse(String data, String delimiter) {
        String[] parsingData = data.split(delimiter);

        return parsingData;
    }
}
