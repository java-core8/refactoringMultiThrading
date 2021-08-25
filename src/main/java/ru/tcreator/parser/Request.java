package ru.tcreator.parser;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import java.util.List;


public class Request {

    public Request(String requestString) {
        List<NameValuePair> listOfQuery = URLEncodedUtils.parse(requestString, Charset.defaultCharset());

        listOfQuery.forEach(queries -> System.out.println(" Ключ: " + queries.getName()
                + " значение: " + queries.getValue()));
    }
}
