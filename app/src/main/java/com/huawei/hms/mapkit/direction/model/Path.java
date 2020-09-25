
package com.huawei.hms.mapkit.direction.model;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Path {

    @SerializedName("duration")
    @Expose
    private Double duration;
    @SerializedName("durationText")
    @Expose
    private String durationText;
    @SerializedName("durationInTrafficText")
    @Expose
    private String durationInTrafficText;
    @SerializedName("durationInTraffic")
    @Expose
    private Double durationInTraffic;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("startLocation")
    @Expose
    private StartLocation startLocation;
    @SerializedName("startAddress")
    @Expose
    private String startAddress;
    @SerializedName("distanceText")
    @Expose
    private String distanceText;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("endLocation")
    @Expose
    private EndLocation endLocation;
    @SerializedName("endAddress")
    @Expose
    private String endAddress;

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public String getDurationInTrafficText() {
        return durationInTrafficText;
    }

    public void setDurationInTrafficText(String durationInTrafficText) {
        this.durationInTrafficText = durationInTrafficText;
    }

    public Double getDurationInTraffic() {
        return durationInTraffic;
    }

    public void setDurationInTraffic(Double durationInTraffic) {
        this.durationInTraffic = durationInTraffic;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public StartLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocation startLocation) {
        this.startLocation = startLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    @Override
    public String toString() {
        return "Path{" +
                "duration=" + duration +
                ", durationText='" + durationText + '\'' +
                ", durationInTrafficText='" + durationInTrafficText + '\'' +
                ", durationInTraffic=" + durationInTraffic +
                ", distance=" + distance +
                ", startLocation=" + startLocation +
                ", startAddress='" + startAddress + '\'' +
                ", distanceText='" + distanceText + '\'' +
                ", steps=" + steps +
                ", endLocation=" + endLocation +
                ", endAddress='" + endAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return Objects.equals(duration, path.duration) &&
                Objects.equals(durationText, path.durationText) &&
                Objects.equals(durationInTrafficText, path.durationInTrafficText) &&
                Objects.equals(durationInTraffic, path.durationInTraffic) &&
                Objects.equals(distance, path.distance) &&
                Objects.equals(startLocation, path.startLocation) &&
                Objects.equals(startAddress, path.startAddress) &&
                Objects.equals(distanceText, path.distanceText) &&
                Objects.equals(steps, path.steps) &&
                Objects.equals(endLocation, path.endLocation) &&
                Objects.equals(endAddress, path.endAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, durationText, durationInTrafficText, durationInTraffic, distance, startLocation, startAddress, distanceText, steps, endLocation, endAddress);
    }
}
