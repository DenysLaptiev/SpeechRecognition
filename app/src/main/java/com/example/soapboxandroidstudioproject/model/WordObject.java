package com.example.soapboxandroidstudioproject.model;

import java.util.List;

public class WordObject {
    /*
    "word_breakdown": [{
      "quality_score": 83.0,
      "end": 2.79,
      "start": 2.04,
      "phone_breakdown": [],
      "word": "like",
      "target_transcription": "l ay k"
    }]
     */

    private double quality_score;
    private double end;
    private double start;
    private List<PhoneBreakdown> phone_breakdown;
    private String word;
    private String target_transcription;

    public WordObject() {
    }

    public WordObject(double quality_score, double end, double start, List<PhoneBreakdown> phone_breakdown, String word, String target_transcription) {
        this.quality_score = quality_score;
        this.end = end;
        this.start = start;
        this.phone_breakdown = phone_breakdown;
        this.word = word;
        this.target_transcription = target_transcription;
    }

    public double getQuality_score() {
        return quality_score;
    }

    public void setQuality_score(double quality_score) {
        this.quality_score = quality_score;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public List<PhoneBreakdown> getPhone_breakdown() {
        return phone_breakdown;
    }

    public void setPhone_breakdown(List<PhoneBreakdown> phone_breakdown) {
        this.phone_breakdown = phone_breakdown;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTarget_transcription() {
        return target_transcription;
    }

    public void setTarget_transcription(String target_transcription) {
        this.target_transcription = target_transcription;
    }

    @Override
    public String toString() {
        return "WordObject{" +
                "quality_score=" + quality_score +
                ", end=" + end +
                ", start=" + start +
                ", phone_breakdown=" + phone_breakdown +
                ", word='" + word + '\'' +
                ", target_transcription='" + target_transcription + '\'' +
                '}';
    }
}
