package com.finwin.cristal.custmate.SupportingClass;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsGetSet {

    private static ArrayList<HashMap<String, String>> newsDataList;

    public static ArrayList<HashMap<String, String>> getNewsValue() {
       return newsDataList;
    }

    public static ArrayList<HashMap<String, String>> setNewsValue(ArrayList<HashMap<String, String>> _newsDataList) {
        return newsDataList = _newsDataList;
    }

}
