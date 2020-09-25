
package com.huawei.hms.mapkit.direction.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class StartLocation {

    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("lat")
    @Expose
    private Double lat;

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "StartLocation{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartLocation that = (StartLocation) o;
        return Objects.equals(lng, that.lng) &&
                Objects.equals(lat, that.lat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lng, lat);
    }
}
