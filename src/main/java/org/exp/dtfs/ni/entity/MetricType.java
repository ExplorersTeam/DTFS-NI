package org.exp.dtfs.ni.entity;

public enum MetricType {
    STATUS("1"), PERFORMANCE("2");

    private String id;

    private MetricType(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

}
