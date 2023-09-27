package com.example.pastebin.controller.pasteControllers;

import com.example.pastebin.packet.IResponse;
import com.example.pastebin.packet.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
public class ReadController {
    @GetMapping("/v1/paste/get/{id}")
    public ResponseEntity<IResponse> getPasteById(@PathVariable int id) {
        return ResponseEntity.ok(new MessageResponse("Got paste"));
    }

    @GetMapping("/v1/paste/getAll")
    public ResponseEntity<List<?>> getUsersAllPastes() {
        return null;
    }
}
