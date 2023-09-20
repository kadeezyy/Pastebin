package com.example.pastebin.controller;

import com.example.pastebin.dtos.PasteDTO;
import com.example.pastebin.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
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
        //toDo: add logger
        return ResponseEntity.ok(pasteService.savePaste(paste));
    }

    @PutMapping("/v1/paste/update")
    public ResponseEntity<String> updatePaste(@RequestBody PasteDTO pasteDTO) {
        return ResponseEntity.ok("Successful");
    }

}
