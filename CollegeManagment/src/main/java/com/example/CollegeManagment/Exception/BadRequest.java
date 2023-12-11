package com.example.CollegeManagment.Exception;

public class BadRequest extends RuntimeException {
    public BadRequest(String msg){
        super(msg);
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
