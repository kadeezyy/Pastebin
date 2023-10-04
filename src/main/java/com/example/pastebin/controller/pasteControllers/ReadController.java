package com.example.pastebin.controller.pasteControllers;

import com.example.pastebin.aop.aspect.Logged;
import com.example.pastebin.entity.User;
import com.example.pastebin.packet.IResponse;
import com.example.pastebin.packet.MessageResponse;
import com.example.pastebin.service.pasteServices.ReadService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/paste")
@AllArgsConstructor
public class ReadController {
    private final ReadService pasteReadService;

    @GetMapping("/get/{id}")
    public ResponseEntity<IResponse> getPasteById(@PathVariable int id) {
        var paste = pasteReadService.getPasteById(id);
        return ResponseEntity.ok(new MessageResponse(paste.toString()));
    }

    @GetMapping("/getAllUserPastes")
    public ResponseEntity<List<?>> getUsersAllPastes(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(pasteReadService.getAllUserPastes(user));
    }
}
