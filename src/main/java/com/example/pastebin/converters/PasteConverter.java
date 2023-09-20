package com.example.pastebin.converters;

import com.example.pastebin.dtos.PasteDTO;
import com.example.pastebin.entity.Paste;
import org.springframework.stereotype.Component;

@Component
public class PasteConverter {
    public Paste pasteDtoToEntity(PasteDTO pasteDTO) {
        return new Paste(pasteDTO.getText(), pasteDTO.getHash());
    }

    public PasteDTO pasteEntityToDto(Paste paste) {

        //toDo: Add users and other fields to service and converter
        PasteDTO dto = new PasteDTO();
        dto.setHash(paste.getHash());
        dto.setText(paste.getText());

        return dto;
    }
}
