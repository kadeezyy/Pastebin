package com.example.pastebin.repositories;

import com.example.pastebin.entity.Paste;
import com.example.pastebin.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PasteRepository extends CrudRepository<Paste, Integer>{

    @Transactional
    @Modifying
    @Query("UPDATE Paste p SET p = :updatedPaste WHERE p.id = :id")
    void updateById(@Param("id") int id, @Param("updatedPaste") Paste paste);

    Optional<Paste> findPasteByHash(String hash);
    @Modifying
    @Query("select p from Paste p where p.user.id = :userID")
    List<Paste> getPastesByUser(int userID);
}
