package com.parga.messagingapp.message;

import com.parga.messagingapp.dto.OutMessageDTO;
import com.parga.messagingapp.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping(path="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createMessage(
            @RequestBody OutMessageDTO dto,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws MaxUploadSizeExceededException{
        try{
            Message message = messageService.createMessage(dto, file);
            return new ResponseEntity(new ResponseDTO("Created Message Successfully", message), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(new ResponseDTO("Failed To Create Message", e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
