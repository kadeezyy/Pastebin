package com.example.pastebin.service.pasteServices;

import com.example.pastebin.aop.aspect.Logged;
import com.example.pastebin.converters.PasteConverter;
import com.example.pastebin.dtos.PasteDto;
import com.example.pastebin.entity.Paste;
import com.example.pastebin.entity.User;
import com.example.pastebin.exceptions.pasteExceptions.PasteNotFoundException;
import com.example.pastebin.exceptions.pasteExceptions.EmptyFieldsException;
import com.example.pastebin.exceptions.userExceptions.UserDoesNotOwnPaste;
import com.example.pastebin.repositories.PasteRepository;
import com.example.pastebin.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true)
@Transactional
public class WriteService {

    private final PasteRepository pasteRepository;
    private final UserRepository userRepository;
    private final PasteConverter converter;

    @Autowired
    public WriteService(PasteRepository pasteRepository, UserRepository userRepository, PasteConverter converter) {
        this.pasteRepository = pasteRepository;
        this.userRepository = userRepository;
        this.converter = converter;
    }


    @Logged
    public PasteDto savePaste(PasteDto pasteDTO, User user) throws EmptyFieldsException {
        if (!validatePaste(pasteDTO))
            throw new EmptyFieldsException("Paste doesn't contain not-null fields Text");
        Paste paste = converter.pasteDtoToEntity(pasteDTO);
        paste.setUser(user);
        paste = pasteRepository.save(paste);
        return converter.pasteEntityToDto(paste);
    }

    @CachePut(value = "Paste", key = "#id")
    @Logged
    public PasteDto updatePaste(PasteDto updatePasteDto, int id, User user) throws PasteNotFoundException {
        var existingPasteOptional = pasteRepository.findById(id);
        if (existingPasteOptional.isEmpty())
            throw new PasteNotFoundException("There is no paste with provided id: " + id);

        if (!validatePaste(updatePasteDto))
            throw new EmptyFieldsException("Paste doesn't contain not-null fields Text and Hash");
        var pasteUser = userRepository.findUserByUsername(user.getUsername());
        if (pasteUser.isEmpty() || !user.getUsername().equals(pasteUser.get().getUsername()))
            throw new UserDoesNotOwnPaste();

        var existingPaste = existingPasteOptional.get();
        existingPaste.setText(updatePasteDto.getText());

        // Save the updated Paste entity
        var savedPaste = pasteRepository.save(existingPaste);

        // Convert the updated Paste entity to DTO and return it
        return converter.pasteEntityToDto(savedPaste);
    }

    private boolean validatePaste(PasteDto paste) {
        return paste.getText() != null &&
                !paste.getText().isEmpty();
    }
}
