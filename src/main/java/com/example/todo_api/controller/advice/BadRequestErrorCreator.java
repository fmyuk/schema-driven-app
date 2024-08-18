package com.example.todo_api.controller.advice;

import com.example.todoapi.model.BadRequestError;
import com.example.todoapi.model.InvalidParam;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

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

    public static BadRequestError from(ConstraintViolationException ex) {
        List<InvalidParam> invalidParamList = createInvalidParamList(ex);

        BadRequestError error = new BadRequestError();
        error.setInvalidParams(invalidParamList);
        return error;
    }

    private static List<InvalidParam> createInvalidParamList(ConstraintViolationException ex) {
        return ex.getConstraintViolations()
                .stream()
                .map(BadRequestErrorCreator::createInvalidParam)
                .toList();
    }

    private static InvalidParam createInvalidParam(ConstraintViolation<?> violation) {
        Optional<Path.Node> parameterOpt = StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                .filter(node -> node.getKind().equals(ElementKind.PARAMETER))
                .findFirst();
        InvalidParam invalidParam = new InvalidParam();
        parameterOpt.ifPresent(p -> invalidParam.setName(p.getName()));
        invalidParam.setReason(violation.getMessage());

        return invalidParam;
    }
}
