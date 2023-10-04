package com.example.pastebin.controller.pasteControllers;

import com.example.pastebin.aop.aspect.Logged;
import com.example.pastebin.dtos.PasteDto;
import com.example.pastebin.entity.User;
import com.example.pastebin.packet.IResponse;
import com.example.pastebin.packet.MessageResponse;
import com.example.pastebin.service.pasteServices.WriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/paste")
public class WriteController {
    WriteService pasteWriteService;

    public WriteController(WriteService pasteWriteService) {
        this.pasteWriteService = pasteWriteService;
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> writePaste(
            @RequestBody PasteDto paste,
            @AuthenticationPrincipal User user
    ) {
        var resultPaste = pasteWriteService.savePaste(paste, user);
        return ResponseEntity.ok(new MessageResponse(resultPaste.toString()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<IResponse> updatePaste(
            @RequestBody PasteDto updatePasteDto,
            @PathVariable int id,
            @AuthenticationPrincipal User user
    ) {
        var updatedPaste = pasteWriteService.updatePaste(updatePasteDto, id, user);
        return ResponseEntity.ok(new MessageResponse(updatedPaste.toString()));
    }
}
