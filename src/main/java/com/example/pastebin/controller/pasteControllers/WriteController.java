package com.example.pastebin.controller.pasteControllers;

import com.example.pastebin.dtos.PasteDTO;
import com.example.pastebin.exceptions.pasteExceptions.AbsencePasteException;
import com.example.pastebin.exceptions.pasteExceptions.EmptyFieldsException;
import com.example.pastebin.service.pasteServices.PasteService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WriteController {
    PasteService pasteService;

    public WriteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping("/v1/paste/add")
    public ResponseEntity<String> writePaste(@RequestBody PasteDTO paste) {
        try {
            //toDo: add logger
            var resultPaste = pasteService.savePaste(paste);
            return ResponseEntity.ok(resultPaste.toString());
        } catch (EmptyFieldsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hash Field is not unique");
        }
    }

    @PutMapping("/v1/paste/update/{id}")
    public ResponseEntity<String> updatePaste(@RequestBody PasteDTO updatePasteDto, @PathVariable int id) {
        try {
            var updatedPaste = pasteService.updatePaste(updatePasteDto, id);
            return ResponseEntity.ok(updatedPaste.toString());
        } catch (AbsencePasteException | EmptyFieldsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hash Field is not unique");
        }
    }
}
