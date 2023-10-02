package com.example.pastebin.service.pasteServices;

import com.example.pastebin.converters.PasteConverter;
import com.example.pastebin.dtos.PasteDto;
import com.example.pastebin.entity.Paste;
import com.example.pastebin.entity.User;
import com.example.pastebin.exceptions.pasteExceptions.AbsencePasteException;
import com.example.pastebin.repositories.PasteRepository;
import jakarta.transaction.Transactional;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(makeFinal = true)
@Transactional
public class PasteReadService {
    PasteRepository pasteRepository;
    PasteConverter converter;

    @Autowired
    public PasteReadService(PasteRepository pasteRepository, PasteConverter converter) {
        this.pasteRepository = pasteRepository;
        this.converter = converter;
    }

    @Cacheable(value = "PasteDto", key = "#id")
    public PasteDto getPasteById(int id) {
        var pasteOptional = pasteRepository.findById(id);
        if (pasteOptional.isEmpty())
            throw new AbsencePasteException("There is no paste with provided id: " + id);
        return converter.pasteEntityToDto(pasteOptional.get());
    }

    public List<PasteDto> getAllUserPastes(User user) {
        List<Paste> pastes = pasteRepository.getPastesByUser(user.getId());
        return pastes.stream().map(converter::pasteEntityToDto).toList();
    }
}
