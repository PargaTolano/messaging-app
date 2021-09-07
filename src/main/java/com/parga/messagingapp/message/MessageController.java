package com.parga.messagingapp.message;

import com.parga.messagingapp.DTO.CreateMessageDTO;
import com.parga.messagingapp.DTO.CreateReplyDTO;
import com.parga.messagingapp.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping(path="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createMessage(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "chatId") String chatId,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws MaxUploadSizeExceededException{
        try{
            CreateMessageDTO dto = new CreateMessageDTO();
            dto.setText(text);
            dto.setChatId(chatId);
            dto.setUserId(userId);
            Message message = messageService.createMessage(dto, file);
            return new ResponseEntity(new ResponseDTO("Created Message Successfully", message), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(new ResponseDTO("Failed To Create Message", e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="create-reply")
    public ResponseEntity createReply(
            @RequestParam(value = "file") MultipartFile file,
            @ModelAttribute CreateReplyDTO dto
    ) throws MaxUploadSizeExceededException{
        try{
            Message message = messageService.createReply(dto, file);
            return new ResponseEntity(new ResponseDTO("Created Message Successfully", message), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(new ResponseDTO("Failed To Create Message", e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
