package com.example.pastebin.service.pasteServices;

import com.example.pastebin.converters.PasteConverter;
import com.example.pastebin.dtos.PasteDTO;
import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.entity.Paste;
import com.example.pastebin.entity.User;
import com.example.pastebin.exceptions.pasteExceptions.AbsencePasteException;
import com.example.pastebin.exceptions.pasteExceptions.EmptyFieldsException;
import com.example.pastebin.exceptions.userExceptions.AbsenceUserException;
import com.example.pastebin.repositories.PasteRepository;
import com.example.pastebin.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true)
@Transactional
public class PasteService {
    PasteRepository pasteRepository;
    PasteConverter converter;
    UserRepository userRepository;

    @Autowired
    public PasteService(PasteRepository pasteRepository, PasteConverter converter, UserRepository userRepository) {
        this.pasteRepository = pasteRepository;
        this.converter = converter;
        this.userRepository = userRepository;
    }

    public PasteDTO savePaste(PasteDTO pasteDTO) {
        if (!validatePaste(pasteDTO)) {
            throw new EmptyFieldsException("Paste doesn't contain not-null fields Text and Hash");
        }
        Optional<User> user;
        if (pasteDTO.getUser() != null) {
            user = userRepository.findUserById(pasteDTO.getUser().getId());
        } else {
            throw new AbsenceUserException("You must also provide user credentials");
        }
        Paste paste = converter.pasteDtoToEntity(pasteDTO);
        user.ifPresent(paste::setUser);
        paste = pasteRepository.save(paste);
        return converter.pasteEntityToDto(paste);
    }

    public PasteDTO updatePaste(PasteDTO updatePasteDto, int id) throws AbsencePasteException {
        var existingPasteOptional = pasteRepository.findById(id);
        if (existingPasteOptional.isEmpty()) {
            throw new AbsencePasteException("There is no paste with provided id: " + id);
        }
        if (!validatePaste(updatePasteDto)) {
            throw new EmptyFieldsException("Paste doesn't contain not-null fields Text and Hash");
        }

        var existingPaste = existingPasteOptional.get();
        existingPaste.setText(updatePasteDto.getText());
        existingPaste.setHash(updatePasteDto.getHash());
        Optional<User> user;
        if (existingPaste.getUser() != null) {
            user = userRepository.findUserById(existingPaste.getUser().getId());
        } else {
            user = userRepository.findUserById(updatePasteDto.getUser().getId());
            System.out.println(user);
        }
        user.ifPresent(existingPaste::setUser);


        // Save the updated Paste entity
        pasteRepository.save(existingPaste);

        // Convert the updated Paste entity to DTO and return it
        return converter.pasteEntityToDto(existingPaste);
    }

    private boolean validatePaste(PasteDTO paste) {
        return paste.getText() != null && paste.getHash() != null;
    }
}
