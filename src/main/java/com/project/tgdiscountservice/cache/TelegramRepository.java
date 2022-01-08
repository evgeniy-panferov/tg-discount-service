package com.project.tgdiscountservice.cache;

public interface TelegramRepository<T> {

    void save(T t);

}
