package com.example.soapboxandroidstudioproject.model;

import java.util.List;

public class Result {
    /*
"results": [{
    "hypothesis_score": 88.0,
    "category": "i like stripes",
    "end": 4.62,
    "start": 1.17,
    "word_breakdown": []
  }]
    */

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
    private double hypothesis_score;
    private String category;
    private double end;
    private double start;
    private List<WordObject> word_breakdown;

    public Result() {
    }

    public Result(double hypothesis_score, String category, double end, double start, List<WordObject> word_breakdown) {
        this.hypothesis_score = hypothesis_score;
        this.category = category;
        this.end = end;
        this.start = start;
        this.word_breakdown = word_breakdown;
    }

    public double getHypothesis_score() {
        return hypothesis_score;
    }

    public void setHypothesis_score(double hypothesis_score) {
        this.hypothesis_score = hypothesis_score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public List<WordObject> getWord_breakdown() {
        return word_breakdown;
    }

    public void setWord_breakdown(List<WordObject> word_breakdown) {
        this.word_breakdown = word_breakdown;
    }

    @Override
    public String toString() {
        return "Result{" +
                "hypothesis_score=" + hypothesis_score +
                ", category='" + category + '\'' +
                ", end=" + end +
                ", start=" + start +
                ", word_breakdown=" + word_breakdown +
                '}';
    }
}
