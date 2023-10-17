package com.example.pastebin.controller.pasteControllers;

import com.example.pastebin.aop.aspect.Logged;
import com.example.pastebin.dtos.PasteDto;
import com.example.pastebin.dtos.SimplePaste;
import com.example.pastebin.entity.User;
import com.example.pastebin.service.hashGenerator.HashGeneratorService;
import com.example.pastebin.service.pasteServices.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/paste")
public class WriteController {
    private final WriteService pasteWriteService;
    private final HashGeneratorService hashGeneratorService;

    @Autowired
    public WriteController(WriteService pasteWriteService, HashGeneratorService hashGeneratorService) {
        this.pasteWriteService = pasteWriteService;
        this.hashGeneratorService = hashGeneratorService;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @Logged
    public ResponseEntity<?> writePaste(
            @RequestBody SimplePaste paste,
            @AuthenticationPrincipal User user
    ) {
        var pasteDto = PasteDto.builder()
                .text(paste.getText())
                .hash(hashGeneratorService.generateHash(paste))
                .build();
        var resultPaste = pasteWriteService.savePaste(pasteDto, user);
        return ResponseEntity.ok(resultPaste);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePaste(
            @RequestBody PasteDto updatePasteDto,
            @PathVariable int id,
            @AuthenticationPrincipal User user
    ) {
        var updatedPaste = pasteWriteService.updatePaste(updatePasteDto, id, user);
        return ResponseEntity.ok(updatedPaste);
    }
}
