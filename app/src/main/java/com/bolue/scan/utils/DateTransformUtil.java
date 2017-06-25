package com.bolue.scan.utils;

/**
 * Created by cty on 2017/6/25.
 */

public class DateTransformUtil {

    public static String transFormDate(long startTime,long endTime){
        String result = "";
        String compareY = "yyyy";
        String compareM = "MM";
        String compareD = "dd";
        String showAll = "yyyy年MM月dd日";
        String showMonth = "MM月dd日";
        String showDay = "dd日";
        if(!SystemTool.getDataTime(compareY,startTime).equals(SystemTool.getDataTime(compareY,endTime))){
            result = SystemTool.getDataTime(showAll,startTime)+"-"+SystemTool.getDataTime(showAll,endTime);
        }else if(!SystemTool.getDataTime(compareM,startTime).equals(SystemTool.getDataTime(compareM,endTime))){
            result = SystemTool.getDataTime(showAll,startTime)+"-"+SystemTool.getDataTime(showMonth,endTime);
        }else if(!SystemTool.getDataTime(compareD,startTime).equals(SystemTool.getDataTime(compareD,endTime))){
            result = SystemTool.getDataTime(showAll,startTime)+"-"+SystemTool.getDataTime(showDay,endTime);
        }else{
            result = SystemTool.getDataTime(showAll,startTime);
        }
        return result;
    }
}
