package com.example.pastebin.controller;

import com.example.pastebin.dtos.PasteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class WriteController {
    @PostMapping("/v1/paste/add")
    public ResponseEntity<String> writePaste(@RequestBody PasteDTO paste) {
        System.out.println(paste.getText());
        System.out.println(paste.getHash());
        return ResponseEntity.ok("Hello");
    }

    @PutMapping("/v1/paste/update")
    public ResponseEntity<String> updatePaste(@RequestBody PasteDTO pasteDTO) {
        return ResponseEntity.ok("Successful");
    }

}
