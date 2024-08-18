package com.example.todo_api.controller.advice;

import com.example.todoapi.model.BadRequestError;
import com.example.todoapi.model.InvalidParam;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public class BadRequestErrorCreator {

    public static BadRequestError from(MethodArgumentNotValidException ex) {
        List<InvalidParam> invalidParamList = createInvalidParamList(ex);

        BadRequestError error = new BadRequestError();
        error.setInvalidParams(invalidParamList);
        return error;
    }

    private static List<InvalidParam> createInvalidParamList(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors()
                .stream()
                .map(BadRequestErrorCreator::createInvalidParam)
                .toList();
    }

    private static InvalidParam createInvalidParam(FieldError fieldError) {
        InvalidParam invalidParam = new InvalidParam();
        invalidParam.setName(fieldError.getField());
        invalidParam.setReason(fieldError.getDefaultMessage());
        return invalidParam;
    }
}
