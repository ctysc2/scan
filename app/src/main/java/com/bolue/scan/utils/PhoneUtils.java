package com.bolue.scan.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by cty on 2017/7/6.
 */

public class PhoneUtils {

    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        return imei;
    }

    /**
     * 获取手机haoma
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String phone = telephonyManager.getLine1Number();
        return phone;
    }
}
