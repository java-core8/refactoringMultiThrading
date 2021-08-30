package ru.tcreator.parser;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



public class Request {
    protected String method;
    protected String source;
    protected List<NameValuePair> listOfQuery;

    public Request(String requestString) {
        // сплитим по пробелам
        String[] splitSource = requestString.split(" ");
        // устанавливаем метод запроса
        method = splitSource[0];
        if(splitSource[1].contains("?")) { // если есть query params
            String[] params = splitSource[1].split("\\?");
            // устанивливаем ресурс
            source = params[0];
            // разбираем параметры запроса
            listOfQuery = URLEncodedUtils.parse(params[1], Charset.defaultCharset());
        } else {
            source = splitSource[1];
        }
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

    /**
     * Возвращает список параметров
     * @return
     */
    public List<NameValuePair> getQueryParams() {
        List<NameValuePair> tmpCollection = new LinkedList<>();
        listOfQuery.addAll(tmpCollection);
        return tmpCollection;
    }

    /**
     * Возвращает метод запроса
     * @return
     */
    public String getMethod() {
        return method;
    }

    /**
     * Возвращает сурс
     * @return
     */
    public String getSource() {
        return source;
    }
}
