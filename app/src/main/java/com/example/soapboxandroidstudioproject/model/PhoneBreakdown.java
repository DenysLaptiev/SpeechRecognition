package com.example.soapboxandroidstudioproject.model;

public class PhoneBreakdown {

    private String phone;
    private double quality_score;
    private double start;
    private double end;

    public PhoneBreakdown() {
    }

    public PhoneBreakdown(String phone, double quality_score, double start, double end) {
        this.phone = phone;
        this.quality_score = quality_score;
        this.start = start;
        this.end = end;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getQuality_score() {
        return quality_score;
    }

    public void setQuality_score(double quality_score) {
        this.quality_score = quality_score;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "PhoneBreakdown{" +
                "phone='" + phone + '\'' +
                ", quality_score=" + quality_score +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
