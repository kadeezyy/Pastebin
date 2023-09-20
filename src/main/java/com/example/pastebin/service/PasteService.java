package com.example.pastebin.service;

import com.example.pastebin.converters.PasteConverter;
import com.example.pastebin.dtos.PasteDTO;
import com.example.pastebin.entity.Paste;
import com.example.pastebin.repositories.PasteRepository;
import jakarta.transaction.Transactional;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@FieldDefaults(makeFinal = true)
public class PasteService {
    PasteRepository pasteRepository;
    PasteConverter converter;

    @Autowired
    public PasteService(PasteRepository pasteRepository, PasteConverter converter) {
        this.pasteRepository = pasteRepository;
        this.converter = converter;
    }

    public PasteDTO savePaste(PasteDTO pasteDTO) {
        Paste paste = converter.pasteDtoToEntity(pasteDTO);
        System.out.println(paste);
        Paste savedPaste = pasteRepository.save(paste);
        return converter.pasteEntityToDto(savedPaste);
    }
}
