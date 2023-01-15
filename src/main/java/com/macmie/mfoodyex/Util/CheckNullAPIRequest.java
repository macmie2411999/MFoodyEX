package com.macmie.mfoodyex.Util;

import java.util.List;

public class CheckNullAPIRequest {
    public static boolean isListNull(List<?> list){
        if(list.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isObjectNull(Object object){
        if(object == null){
            return true;
        }
        return false;
    }
}
