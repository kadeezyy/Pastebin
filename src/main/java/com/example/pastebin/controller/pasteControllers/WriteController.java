package com.example.pastebin.controller.pasteControllers;

import com.example.pastebin.dtos.PasteDTO;
import com.example.pastebin.service.pasteServices.PasteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WriteController {
    PasteService pasteService;

    public WriteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping("/v1/paste/add")
    public ResponseEntity<PasteDTO> writePaste(@RequestBody PasteDTO paste) {
        var resultPaste = pasteService.savePaste(paste);
        if (resultPaste == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //toDo: add logger
        return ResponseEntity.ok(resultPaste);
    }

    @PutMapping("/v1/paste/update")
    public ResponseEntity<String> updatePaste(@RequestBody PasteDTO pasteDTO) {
        return ResponseEntity.ok("Successful");
    }

}
