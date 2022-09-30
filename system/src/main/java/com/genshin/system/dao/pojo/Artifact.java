package com.genshin.system.dao.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Artifact implements Serializable {
    public Artifact(){}

    public Artifact(String artifactSet, String artifactType, String artifactMainStats, int level, String artifactSubInitialStats1, double artifactSubInitialStats1Value, String artifactSubInitialStats2, double artifactSubInitialStats2Value, String artifactSubInitialStats3, double artifactSubInitialStats3Value, String artifactSubInitialStats4, double artifactSubInitialStats4Value) {
        this.artifactSet = artifactSet;
        this.artifactType = artifactType;
        this.artifactMainStats = artifactMainStats;
        this.level = level;
        this.artifactSubInitialStats1 = artifactSubInitialStats1;
        this.artifactSubInitialStats1Value = artifactSubInitialStats1Value;
        this.artifactSubInitialStats2 = artifactSubInitialStats2;
        this.artifactSubInitialStats2Value = artifactSubInitialStats2Value;
        this.artifactSubInitialStats3 = artifactSubInitialStats3;
        this.artifactSubInitialStats3Value = artifactSubInitialStats3Value;
        this.artifactSubInitialStats4 = artifactSubInitialStats4;
        this.artifactSubInitialStats4Value = artifactSubInitialStats4Value;
    }

    private String artifactSet;
    private String artifactType;
    private String artifactMainStats;
    private int level;
    private String artifactSubInitialStats1;
    private double artifactSubInitialStats1Value;
    private String artifactSubInitialStats2;
    private double artifactSubInitialStats2Value;
    private String artifactSubInitialStats3;
    private double artifactSubInitialStats3Value;
    private String artifactSubInitialStats4;
    private double artifactSubInitialStats4Value;
}
