package com.example.pastebin.controller.pasteControllers;

import com.example.pastebin.dtos.PasteDTO;
import com.example.pastebin.exceptions.pasteExceptions.AbsencePasteException;
import com.example.pastebin.exceptions.userExceptions.AbsenceUserException;
import com.example.pastebin.packet.ErrorResponse;
import com.example.pastebin.packet.IResponse;
import com.example.pastebin.packet.MessageResponse;
import com.example.pastebin.service.pasteServices.PasteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class WriteController {
    PasteService pasteService;

    public WriteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping("/v1/paste/add")
    public ResponseEntity<IResponse> writePaste(@RequestBody PasteDTO paste) {
        try {
            //toDo: add logger
            var resultPaste = pasteService.savePaste(paste);
            return ResponseEntity.ok(new MessageResponse(resultPaste.toString()));
        } catch (AbsenceUserException | DataIntegrityViolationException ex) {
            log.error("Unable to create paste: {}", paste);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PutMapping("/v1/paste/update/{id}")
    public ResponseEntity<IResponse> updatePaste(@RequestBody PasteDTO updatePasteDto, @PathVariable int id) {
        try {
            var updatedPaste = pasteService.updatePaste(updatePasteDto, id);
            return ResponseEntity.ok(new MessageResponse(updatedPaste.toString()));
        } catch (AbsencePasteException | DataIntegrityViolationException ex) {
            log.error("Unable to update paste: {}", updatePasteDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        }
    }
}
