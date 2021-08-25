package ru.tcreator.parser;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;



public class Request {
    protected String method;
    protected String source;
    protected List<NameValuePair> listOfQuery;

    public Request(String requestString) {
        listOfQuery = URLEncodedUtils.parse(requestString, Charset.defaultCharset());
        listOfQuery.forEach(System.out::println);
    }

    /**
     * Возвращает NameValuePair query параметр или null если по ключу ничего не найдено
     * @param nameQuery
     * @return
     */
    public NameValuePair getQueryParam(String nameQuery) {
        NameValuePair tmpQuery = null;
        for (NameValuePair query: listOfQuery) {
            if (query.getName().contains(nameQuery)) {
                tmpQuery = query;
                break;
            }
        }
        return tmpQuery;
    }

    public List<NameValuePair> getQueryParams() {
        List<NameValuePair> tmpCollection = new LinkedList<>();
        listOfQuery.addAll(tmpCollection);
        return tmpCollection;
    }

}
