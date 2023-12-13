package com.example.CollegeManagment.Exception;

import com.example.CollegeManagment.dto.responsedto.Responsedto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler({ItemNotFound.class, BadRequest.class})
    public ResponseEntity<Responsedto<Object>> handlecustomexception(
            RuntimeException e,
            WebRequest webRequest)
    {
      Responsedto<Object> responsedto=new Responsedto<>();
     responsedto.setSuccess(false);
     responsedto.setMessage(e.getMessage());
     if(e instanceof ItemNotFound)
         return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsedto);
     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsedto);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Responsedto<Object>> handlebuildexception(
            BindException e,
            WebRequest webRequest)
    {
        Responsedto<Object> responsedto=new Responsedto<>();
        responsedto.setSuccess(false);
        responsedto.setMessage(e.getFieldError()!=null?e.getFieldError().getDefaultMessage():"Validation error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsedto);
    }


}
