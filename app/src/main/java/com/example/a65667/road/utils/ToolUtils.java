package com.example.a65667.road.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

public class ToolUtils {
    public static void add_point_in_map(AMap aMap, LatLng lng, int id) {
        MarkerOptions markerOptions = new MarkerOptions();
        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), id);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        markerOptions.icon(bitmapDescriptor);
        markerOptions.position(lng);
        markerOptions.draggable(false);
        aMap.addMarker(markerOptions);
    }
}
