package com.tubesbookwise.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timesStamp;
    private int status;
    private String error;
    private String messsage;
    private String path;

    public ErrorResponse(LocalDateTime timeStamp, int status, String error, String messsage, String path) {
        this.timesStamp = timeStamp;
        this.status = status;
        this.error = error;
        this.messsage = messsage;
        this.path = path;
    }

    public LocalDateTime getTimesStamp() {
        return timesStamp;
    }

    public void setTimesStamp(LocalDateTime timesStamp) {
        this.timesStamp = timesStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}



