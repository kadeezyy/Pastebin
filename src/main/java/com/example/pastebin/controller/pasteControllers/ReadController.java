package com.example.pastebin.controller.pasteControllers;

import com.example.pastebin.dtos.PasteDTO;
import com.example.pastebin.dtos.PingPongDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
public class ReadController {
    @GetMapping("/pingpong")
    public ResponseEntity<String> pingPong(@RequestParam PingPongDTO message) {
        if (message.getMessage().equals("ping"))
            return ResponseEntity.ok("pong");
        return ResponseEntity.ok("Hello There!");
    }

    @GetMapping("/v1/paste/get/{id}")
    public ResponseEntity<String> getPasteById(@PathVariable int id) {
        return ResponseEntity.ok("Got paste");
    }

    @GetMapping("/v1/paste/getAll")
    public ResponseEntity<List<PasteDTO>> getUsersAllPastes() {
        return null;
    }
}
