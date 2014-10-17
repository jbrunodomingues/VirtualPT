package com.brn.homebrew.service;

/**
 * @author Bruno Domingues
 */
public interface MappingService {

    void map(Object source, Object destination);

    <T> T map(Object source, Class<T> destinationClass);
}