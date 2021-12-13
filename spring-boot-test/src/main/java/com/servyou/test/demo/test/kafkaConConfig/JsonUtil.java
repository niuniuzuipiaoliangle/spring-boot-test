package com.servyou.test.demo.test.kafkaConConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 * Created by syf on 2017/5/2.
 */
public class JsonUtil {

    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
    }


    public static Map parseJSONMap(String text) {
        try {
            try {
                return (Map) JSON.parse(text);
            } catch (Exception e) {
                String njs = string2Json(text);
                return (Map) JSON.parse(njs);
            }
        } catch (Exception e) {
            throw new RuntimeException("Json对象解析失败。Err： " + e.getMessage(), e);
        }
    }

    public static final <T> T parseJSON(String text, Class<T> clazz) {
        try {
            try {
                return JSON.parseObject(text, clazz);
            } catch (Exception e) {
                String njs = string2Json(text);
                return JSON.parseObject(njs, clazz);
            }
            // JSON.parseObject(text, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Json对象解析失败。Err： " + e.getMessage(), e);
        }
    }

    public static JSONArray parseJSONArray(String text) {
        return JSON.parseArray(text);
    }

    public static <T> List<T> parseJSONList(String jsonString, Class<T> clazz) {
        try {
            try {
                return JSON.parseArray(jsonString, clazz);
            } catch (Exception e) {
                String njs = string2Json(jsonString);
                return JSON.parseArray(njs, clazz);
            }
        } catch (Exception e) {
            throw new RuntimeException("Json对象解析失败。Err： " + e.getMessage(), e);
        }
    }


    /**
     * 过滤特殊字符
     *
     * @param s
     * @return
     */
    public static String string2Json(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.toCharArray()[i];
            switch (c) {
              /*  case '\"':
                    sb.append("\\\"");
                    break;
*/
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if ((c >= 0 && c <= 31) || c == 127)//在ASCⅡ码中，第0～31号及第127号(共33个)是控制字符或通讯专用字符
                    {
                    } else {
                        sb.append(c);
                    }
                    break;
            }

        }

        return sb.toString();

    }
}

