package com.bolue.scan.common;

/**
 * Created by cty on 16/12/09.
 */
public class ApiConstants {

    private static Boolean IS_DEBUG = true;

    private static String BASE_URL = IS_DEBUG?"http://api.linked-f.com":"https://api.linked-f.com";

    private static String version = IS_DEBUG?"/test/v2/":"/v2/";

    public static String URL = BASE_URL+version;


    //获取接口类型
    public static String getHost(int hostType) {

        String host;

        switch (hostType) {
            case HostType.INDEX:
            case HostType.CAROUSEL:
            case HostType.RESERVE:
            case HostType.LABEL:
            case HostType.OFFLINE_DETAIL:
            case HostType.PARTICIPANT_DETAIL:
                host = URL;
                break;
            default:
                host="";
                break;
        }
        return host;
    }
}
