package me.jkong.flink.transformation.udf;

import org.apache.flink.table.functions.ScalarFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * TODO
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/26 15:58.
 */
public class StringToDate extends ScalarFunction {

    public static String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public Date eval(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return new Date(sdf.parse(dateStr).getTime());
    }

    public Date eval(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return new Date(sdf.parse(dateStr).getTime());
    }
}