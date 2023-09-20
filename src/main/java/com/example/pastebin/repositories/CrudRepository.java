package com.example.pastebin.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.OptionalInt;


@NoRepositoryBean
public interface CrudRepository<T, ID> extends Repository<T, ID> {
    <S extends T> S save(S entity);

    <S extends T> Iterable<S> saveAll(Iterable<S> list);

    long count();

    Optional<T> findById(ID id);

    void deleteById(ID id);

    void delete(T entity);
}
