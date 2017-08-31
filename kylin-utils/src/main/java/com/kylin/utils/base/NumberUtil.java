package com.kylin.utils.base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

/**
 * Created by thonny on 2015-8-5.
 */
public class NumberUtil {

    /**
     * 将 f 转换成 int
     * 规则：
     *      4舍 5入
     * @param f
     * @return
     */
    public static long converFloat(float f){
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        String format = decimalFormat.format(f);
        String[] split = format.split("\\.");
        char substring = split[1].charAt(0);
        if (split[0].isEmpty()) 
        	split[0] = "0";
        if (substring<'5'&&substring>='0')
            return Long.valueOf(split[0]);
        else if(substring>='5')
            return Long.valueOf(split[0])+1;
        else return 0l;
    }

        public static void main(String[] args) {
            try {
                InetAddress byName = InetAddress.getByName("localhost");
                System.out.println(byName.getHostAddress());
                InetAddress byName1 = InetAddress.getByName("127.0.0.1");
                System.out.println(byName1.getHostAddress());
                InetAddress byName2 = InetAddress.getByName("0.0.0.0");
                System.out.println(byName2.getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }


}
