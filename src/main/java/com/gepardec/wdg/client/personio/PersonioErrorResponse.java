package com.gepardec.wdg.client.personio;

public class PersonioErrorResponse {
    private String error;

    public PersonioErrorResponse() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "PersonioErrorResponse{" +
                "\nmessage='" + error + '\'' +
                "\n}";
    }
}
