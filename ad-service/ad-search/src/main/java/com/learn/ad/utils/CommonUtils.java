package com.learn.ad.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
public class CommonUtils {
    public static <K,V> V getOrCreate(K key, Map<K,V> map, Supplier<V> factory){
        return map.computeIfAbsent(key,K->factory.get());
    }
    public  static String stringConcat(String... args){
        StringBuilder res= new StringBuilder();
        for(String arg: args){
            res.append(arg);
            res.append("-");
        }
        res.deleteCharAt(res.length()-1);
        return res.toString();
    }

    public static Date parseStringDate(String dateString){
        try {
            DateFormat dateFormat= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyy");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            log.error("ParseStringDateError: {}", dateString);
            e.printStackTrace();
            return null;
        }
    }
}
