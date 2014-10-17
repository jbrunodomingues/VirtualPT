package com.brn.homebrew.controller;

import java.lang.reflect.Field;

/**
 * @author Bruno Domingues
 */
public class ControllerTestsHelper {

    public static void setDependencyUsingReflection(Object targetObject, String targetFieldName, Object fieldObject) {
        final Field field;
        try {
            field = targetObject.getClass().getDeclaredField(targetFieldName);
            field.setAccessible(true);
            field.set(targetObject, fieldObject);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
