package com.project.tgdiscountservice.cache;

public interface TelegramRepository<T> {

    T find(String  t);

    void save (T t);

}
