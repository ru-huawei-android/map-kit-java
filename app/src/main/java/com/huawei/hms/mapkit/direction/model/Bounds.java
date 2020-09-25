
package com.huawei.hms.mapkit.direction.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Bounds {

    @SerializedName("southwest")
    @Expose
    private Southwest southwest;
    @SerializedName("northeast")
    @Expose
    private Northeast northeast;

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "southwest=" + southwest +
                ", northeast=" + northeast +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bounds bounds = (Bounds) o;
        return Objects.equals(southwest, bounds.southwest) &&
                Objects.equals(northeast, bounds.northeast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(southwest, northeast);
    }
}
