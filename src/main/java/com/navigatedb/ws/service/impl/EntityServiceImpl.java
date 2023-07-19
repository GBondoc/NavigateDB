package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.exceptions.EntityServiceException;
import com.navigatedb.ws.io.entity.EntityEntity;
import com.navigatedb.ws.repository.EntityRepository;
import com.navigatedb.ws.service.EntityService;
import com.navigatedb.ws.shared.Utils;
import com.navigatedb.ws.shared.dto.EntityDto;
import com.navigatedb.ws.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityServiceImpl implements EntityService {

    @Autowired
    EntityRepository entityRepository;

    @Autowired
    Utils utils;

    public EntityDto createEntity(EntityDto entity) throws EntityServiceException {

        if(entityRepository.findByName(entity.getName()) != null)
            throw new EntityServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        EntityEntity entityEntity = modelMapper.map(entity, EntityEntity.class);

        String publicEntityId = utils.generateEntityId(30);
        entityEntity.setEntityId(publicEntityId);

        EntityEntity storedEntityDetails = entityRepository.save(entityEntity);

        return modelMapper.map(storedEntityDetails, EntityDto.class);
    }

    @Override
    public EntityDto getEntity(String entityId) throws EntityServiceException {

        EntityEntity entityEntity = entityRepository.findByEntityId(entityId);

        if(entityEntity == null)
            throw new EntityServiceException("Entity with ID: " + entityId + " not found");

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(entityEntity, EntityDto.class);
    }

}
