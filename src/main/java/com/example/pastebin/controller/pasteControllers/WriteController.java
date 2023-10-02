package com.example.pastebin.controller.pasteControllers;

import com.example.pastebin.dtos.PasteDto;
import com.example.pastebin.entity.User;
import com.example.pastebin.exceptions.pasteExceptions.AbsencePasteException;
import com.example.pastebin.exceptions.userExceptions.AbsenceUserException;
import com.example.pastebin.packet.ErrorResponse;
import com.example.pastebin.packet.IResponse;
import com.example.pastebin.packet.MessageResponse;
import com.example.pastebin.service.pasteServices.PasteWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class WriteController {
    PasteWriteService pasteWriteService;

    public WriteController(PasteWriteService pasteWriteService) {
        this.pasteWriteService = pasteWriteService;
    }

    @PostMapping("/v1/paste/add")
    public ResponseEntity<IResponse> writePaste(
            @RequestBody PasteDto paste,
            @AuthenticationPrincipal User user
    ) {
        try {
            var resultPaste = pasteWriteService.savePaste(paste, user);
            return ResponseEntity.ok(new MessageResponse(resultPaste.toString()));
        } catch (AbsenceUserException | DataIntegrityViolationException ex) {
            log.error("Unable to create paste: {}", paste);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PutMapping("/v1/paste/update/{id}")
    public ResponseEntity<IResponse> updatePaste(
            @RequestBody PasteDto updatePasteDto,
            @PathVariable int id,
            @AuthenticationPrincipal User user
    ) {
        try {
            var updatedPaste = pasteWriteService.updatePaste(updatePasteDto, id, user);
            return ResponseEntity.ok(new MessageResponse(updatedPaste.toString()));
        } catch (AbsencePasteException | DataIntegrityViolationException ex) {
            log.error("Unable to update paste: {}", updatePasteDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        }
    }
}
