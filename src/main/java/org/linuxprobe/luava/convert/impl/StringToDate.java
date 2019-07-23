package org.linuxprobe.luava.convert.impl;

import org.linuxprobe.luava.convert.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StringToDate implements Converter<String, Date> {
    public Map<String, SimpleDateFormat> dateFormatMap;

    public StringToDate() {
        this.dateFormatMap = new HashMap<>();
        this.dateFormatMap.put("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3}Z$",
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        this.dateFormatMap.put("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3}\\sUTC$",
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        this.dateFormatMap.put("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3}$",
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        this.dateFormatMap.put("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}\\.[0-9]{1,3}$",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
        this.dateFormatMap.put("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        this.dateFormatMap.put("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$", new SimpleDateFormat("yyyy-MM-dd"));
        this.dateFormatMap.put("^[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}$", new SimpleDateFormat("MM/dd/yyyy"));
        this.dateFormatMap.put("^[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$",
                new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"));
    }

    @Override
    public Date convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        if (source.matches("^[0-9]+$")) {
            return new Date(Long.valueOf(source));
        }
        Set<String> regexs = this.dateFormatMap.keySet();
        SimpleDateFormat simpleDateFormat = null;
        for (String regex : regexs) {
            if (source.matches(regex)) {
                if (source.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}Z$")) {
                    source = source.replace("Z", " UTC");
                }
                simpleDateFormat = this.dateFormatMap.get(regex);
                break;
            }
        }
        if (simpleDateFormat == null) {
            return null;
        }
        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
