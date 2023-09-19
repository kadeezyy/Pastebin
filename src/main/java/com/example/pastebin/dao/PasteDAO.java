package com.example.pastebin.dao;

import com.example.pastebin.entity.Paste;

public interface PasteDAO {
    void addPaste(Paste paste);

    void removePaste(Paste paste);

    Paste getPasteById(int id);

    void updatePaste(Paste paste);
}
