//package com.macmie.mfoodyex.Util;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//public class CheckNullRequest {
//    public static ResponseEntity<?> checkNull(Object objectToCheck, String typeObject, String nameAttributeOfObject, int valueAttributeOfObject) {
//        if (objectToCheck == null) {
//            String stringReturn = "Can't find any" + typeObject + " with " + nameAttributeOfObject + " : " + valueAttributeOfObject;
//            return new ResponseEntity<>(stringReturn, HttpStatus.NOT_FOUND);
//        }
//    }
//}
