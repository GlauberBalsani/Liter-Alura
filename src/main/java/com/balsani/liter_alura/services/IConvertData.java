package com.balsani.liter_alura.services;

public interface IConvertData {
    <T> T getData(String json, Class<T> tClass);
}
