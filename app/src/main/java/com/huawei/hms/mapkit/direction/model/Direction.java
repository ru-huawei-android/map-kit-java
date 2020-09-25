
package com.huawei.hms.mapkit.direction.model;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Direction {

    @SerializedName("routes")
    @Expose
    private List<Route> routes = null;
    @SerializedName("returnCode")
    @Expose
    private String returnCode;
    @SerializedName("returnDesc")
    @Expose
    private String returnDesc;

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "routes=" + routes +
                ", returnCode='" + returnCode + '\'' +
                ", returnDesc='" + returnDesc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return Objects.equals(routes, direction.routes) &&
                Objects.equals(returnCode, direction.returnCode) &&
                Objects.equals(returnDesc, direction.returnDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routes, returnCode, returnDesc);
    }
}
