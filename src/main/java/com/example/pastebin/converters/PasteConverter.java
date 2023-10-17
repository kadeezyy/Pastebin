package com.example.pastebin.converters;

import com.example.pastebin.dtos.PasteDto;
import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.entity.Paste;
import com.example.pastebin.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PasteConverter {
    public Paste pasteDtoToEntity(PasteDto pasteDTO) {
        return Paste.builder()
//                .id(pasteDTO.getId())
                .hash(pasteDTO.getHash())
                .text(pasteDTO.getText())
                .build();
    }

    public PasteDto pasteEntityToDto(Paste paste) {
        return PasteDto.builder()
                .id(paste.getId())
                .text(paste.getText())
                .hash(paste.getHash())
                .build();
    }
}
