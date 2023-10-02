package com.example.pastebin.service.pasteServices;

import com.example.pastebin.converters.PasteConverter;
import com.example.pastebin.dtos.PasteDto;
import com.example.pastebin.entity.Paste;
import com.example.pastebin.entity.User;
import com.example.pastebin.exceptions.pasteExceptions.AbsencePasteException;
import com.example.pastebin.exceptions.pasteExceptions.EmptyFieldsException;
import com.example.pastebin.repositories.PasteRepository;
import jakarta.transaction.Transactional;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(makeFinal = true)
@Transactional
public class PasteWriteService {

    PasteRepository pasteRepository;
    PasteConverter converter;

    @Autowired
    public PasteWriteService(PasteRepository pasteRepository, PasteConverter converter) {
        this.pasteRepository = pasteRepository;
        this.converter = converter;
    }


    public PasteDto savePaste(PasteDto pasteDTO, User user) {
        if (!validatePaste(pasteDTO)) {
            throw new EmptyFieldsException("Paste doesn't contain not-null fields Text and Hash");
        }
        Paste paste = converter.pasteDtoToEntity(pasteDTO);
        paste.setUser(user);
        paste = pasteRepository.save(paste);
        log.info("Saved paste {}", paste);
        return converter.pasteEntityToDto(paste);
    }

    @CachePut(value = "Paste", key = "#id")
    public PasteDto updatePaste(PasteDto updatePasteDto, int id, User user) throws AbsencePasteException {
        var existingPasteOptional = pasteRepository.findPasteByHash(updatePasteDto.getHash());
//        var existingPasteOptional = pasteRepository.findById(id);
        if (existingPasteOptional.isEmpty()) {
            throw new AbsencePasteException("There is no paste with provided id: " + id);
        }
        if (!validatePaste(updatePasteDto)) {
            throw new EmptyFieldsException("Paste doesn't contain not-null fields Text and Hash");
        }

        var existingPaste = existingPasteOptional.get();
        existingPaste.setText(updatePasteDto.getText());
        existingPaste.setHash(updatePasteDto.getHash());
        existingPaste.setUser(user);


        // Save the updated Paste entity
        var savedPaste = pasteRepository.save(existingPaste);

        log.info("Updated paste {}", savedPaste);

        // Convert the updated Paste entity to DTO and return it
        return converter.pasteEntityToDto(savedPaste);
    }

    private boolean validatePaste(PasteDto paste) {
        return paste.getText() != null && paste.getHash() != null;
    }
}
