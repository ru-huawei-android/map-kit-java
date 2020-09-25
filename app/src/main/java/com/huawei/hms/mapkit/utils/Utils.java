package com.huawei.hms.mapkit.utils;

import java.util.Arrays;

public class Utils {
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    public static int[] append(int[] arr, int element) {
        int[] array = Arrays.copyOf(arr,arr.length + 1);
        array[arr.length] = element;
        return array;
    }
}
