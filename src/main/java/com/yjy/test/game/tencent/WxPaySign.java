package com.yjy.test.game.tencent;

import com.yjy.test.game.util.MD5;
import com.yjy.test.game.web.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * 微信支付相关算法实现
 *
 * @author gwold-stt
 * @date 2017年5月27日
 */
public class WxPaySign {
    /**
     * 获得签名
     *
     * @param map
     * @return
     * @author gwold-stt
     * @date 2017年5月27日 下午2:22:05
     */
    public static String getSign(Map<String, Object> map) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + Constants.wxpayKey;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }
}
