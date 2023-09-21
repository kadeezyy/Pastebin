package com.example.pastebin.service.pasteServices;

import com.example.pastebin.converters.PasteConverter;
import com.example.pastebin.dtos.PasteDTO;
import com.example.pastebin.entity.Paste;
import com.example.pastebin.repositories.PasteRepository;
import jakarta.transaction.Transactional;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

//    @Transactional(dontRollbackOn = DataIntegrityViolationException.class)
    public PasteDTO savePaste(PasteDTO pasteDTO) {
        try {
            Paste savedPaste = pasteRepository.save(converter.pasteDtoToEntity(pasteDTO));
            return converter.pasteEntityToDto(savedPaste);
        } catch (DataIntegrityViolationException violationException) {
            System.out.println(violationException.getMessage());
            return null;
        }
    }
}
