package com.parga.messagingapp.message;

import com.parga.messagingapp.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class MessageExceptionAdvice {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity handleMaxSizeException(
            MaxUploadSizeExceededException e,
            HttpServletRequest request,
            HttpServletResponse response) {
//        ModelAndView modelAndView = new ModelAndView("file");
//        modelAndView.getModel().put("message", "File too large!");
        return new ResponseEntity( new ResponseDTO(e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
