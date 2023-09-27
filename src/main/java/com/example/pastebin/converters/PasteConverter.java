package com.example.pastebin.converters;

import com.example.pastebin.dtos.PasteDTO;
import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.entity.Paste;
import com.example.pastebin.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PasteConverter {
    public Paste pasteDtoToEntity(PasteDTO pasteDTO) {
        var paste = new Paste(pasteDTO.getText(), pasteDTO.getHash());
        paste.setId(pasteDTO.getId());
        paste.setUser(new User(pasteDTO.getId(),
                pasteDTO.getUser().getUsername(),
                pasteDTO.getUser().getRole(),
                pasteDTO.getUser().getPassword()));
        return paste;
    }

    public PasteDTO pasteEntityToDto(Paste paste) {

        //toDo: Add users and other fields to service and converter
        PasteDTO dto = new PasteDTO();
        dto.setHash(paste.getHash());
        dto.setText(paste.getText());


        dto.setUser(new UserDTO(paste.getId(),
                paste.getUser().getUsername(),
                paste.getUser().getPassword(),
                paste.getUser().getRole()));
        return dto;
    }
}
