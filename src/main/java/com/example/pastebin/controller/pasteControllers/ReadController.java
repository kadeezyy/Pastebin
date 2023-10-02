package com.example.pastebin.controller.pasteControllers;

import com.example.pastebin.entity.User;
import com.example.pastebin.exceptions.pasteExceptions.AbsencePasteException;
import com.example.pastebin.packet.ErrorResponse;
import com.example.pastebin.packet.IResponse;
import com.example.pastebin.packet.MessageResponse;
import com.example.pastebin.service.pasteServices.PasteReadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
@AllArgsConstructor
public class ReadController {
    private final PasteReadService pasteReadService;
    @GetMapping("/v1/paste/get/{id}")
    public ResponseEntity<IResponse> getPasteById(@PathVariable int id) {
        try {
            var paste = pasteReadService.getPasteById(id);
            return ResponseEntity.ok(new MessageResponse(paste.toString()));
        } catch (AbsencePasteException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/v1/paste/getAllUserPastes")
    public ResponseEntity<List<?>> getUsersAllPastes(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(pasteReadService.getAllUserPastes(user));
    }
}
