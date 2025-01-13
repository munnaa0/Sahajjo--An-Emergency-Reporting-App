package com.sahajjoapp.sahajjo;

import android.webkit.GeolocationPermissions;

public class Problem {
    private String problemTitle;
    private String problemDescription;
    private String problemLocation;

    // Firestore requires an empty constructor
    public Problem() {
    }

    public Problem(String problemTitle, String problemDescription, String problemLocation) {
        this.problemTitle = problemTitle;
        this.problemDescription = problemDescription;
        this.problemLocation = problemLocation;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getProblemDescription() {


        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getProblemLocation() {
        return problemLocation;
    }

    public void setProblemLocation(String problemLocation) {
        this.problemLocation = problemLocation;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "problemTitle='" + problemTitle + '\'' +
                ", problemDescription='" + problemDescription + '\'' +
                ", problemLocation='" + problemLocation + '\'' +
                '}';
    }
}
