
package com.huawei.hms.mapkit.direction.model;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("duration")
    @Expose
    private Double duration;
    @SerializedName("orientation")
    @Expose
    private Integer orientation;
    @SerializedName("durationText")
    @Expose
    private String durationText;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("startLocation")
    @Expose
    private StartLocation startLocation;
    @SerializedName("instruction")
    @Expose
    private String instruction;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("distanceText")
    @Expose
    private String distanceText;
    @SerializedName("endLocation")
    @Expose
    private EndLocation endLocation;
    @SerializedName("polyline")
    @Expose
    private List<Polyline> polyline = null;
    @SerializedName("roadName")
    @Expose
    private String roadName;

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public List<Polyline> getPolyline() {
        return polyline;
    }

    public void setPolyline(List<Polyline> polyline) {
        this.polyline = polyline;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    @Override
    public String toString() {
        return "Step{" +
                "duration=" + duration +
                ", orientation=" + orientation +
                ", durationText='" + durationText + '\'' +
                ", distance=" + distance +
                ", startLocation=" + startLocation +
                ", instruction='" + instruction + '\'' +
                ", action='" + action + '\'' +
                ", distanceText='" + distanceText + '\'' +
                ", endLocation=" + endLocation +
                ", polyline=" + polyline +
                ", roadName='" + roadName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return Objects.equals(duration, step.duration) &&
                Objects.equals(orientation, step.orientation) &&
                Objects.equals(durationText, step.durationText) &&
                Objects.equals(distance, step.distance) &&
                Objects.equals(startLocation, step.startLocation) &&
                Objects.equals(instruction, step.instruction) &&
                Objects.equals(action, step.action) &&
                Objects.equals(distanceText, step.distanceText) &&
                Objects.equals(endLocation, step.endLocation) &&
                Objects.equals(polyline, step.polyline) &&
                Objects.equals(roadName, step.roadName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, orientation, durationText, distance, startLocation, instruction, action, distanceText, endLocation, polyline, roadName);
    }
}
