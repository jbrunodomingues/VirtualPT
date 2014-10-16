package com.brn.homebrew.service.impl;

import com.brn.homebrew.service.MappingService;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

/**
 * @author Bruno Domingues
 */
public class MappingServiceImpl implements MappingService {

    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

    @Override
    public void map(Object source, Object destination) {
        mapper.map(source, destination);
    }
}
