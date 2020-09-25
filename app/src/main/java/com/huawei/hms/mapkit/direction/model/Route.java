
package com.huawei.hms.mapkit.direction.model;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Route {

    @SerializedName("paths")
    @Expose
    private List<Path> paths = null;
    @SerializedName("bounds")
    @Expose
    private Bounds bounds;

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public String toString() {
        return "Route{" +
                "paths=" + paths +
                ", bounds=" + bounds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(paths, route.paths) &&
                Objects.equals(bounds, route.bounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paths, bounds);
    }
}
