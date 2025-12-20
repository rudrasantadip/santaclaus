package com.api.santaclaus.santaclaus.services.utils;

import java.util.function.Consumer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceExecutor 
{
    @SafeVarargs
    public static <T,ID> T save(JpaRepository<T,ID> repo, T entity, Consumer<T> ...preSave){
        Consumer<T> combined = t ->{};
        for(Consumer<T> logic:preSave){
           combined=combined.andThen(logic);
        }
        combined.accept(entity);
        return repo.save(entity);
    }
}
