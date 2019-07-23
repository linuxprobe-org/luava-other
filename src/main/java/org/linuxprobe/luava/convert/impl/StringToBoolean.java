package org.linuxprobe.luava.convert.impl;

import org.linuxprobe.luava.convert.Converter;

public class StringToBoolean implements Converter<String, Boolean> {
    @Override
    public Boolean convert(String source) {
        if (source == null) {
            return null;
        } else if (source.toLowerCase().equals("yes") || source.toLowerCase().equals("是") || source.equals("1")) {
            return true;
        } else if (source.toLowerCase().equals("no") || source.toLowerCase().equals("否") || source.equals("0")) {
            return false;
        } else {
            throw new IllegalArgumentException(source + " cat not case to boolean");
        }
    }
}
