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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public EntityDto updateEntity(String entityId, EntityDto entity) {

        EntityEntity entityEntity = entityRepository.findByEntityId(entityId);

        if(entityEntity == null)
            throw new EntityServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        entityEntity.setName(entity.getName());
        entityEntity.setRowCount(entity.getRowCount());

        EntityEntity updatedEntityDetails = entityRepository.save(entityEntity);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(updatedEntityDetails, EntityDto.class);
    }

    @Override
    public void deleteEntity(String entityId) {
        EntityEntity entityEntity = entityRepository.findByEntityId(entityId);

        if(entityEntity == null)
            throw new EntityServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        entityRepository.delete(entityEntity);
    }

    @Override
    public List<EntityDto> getEntities(int page, int limit) {
        List<EntityDto> returnValue = new ArrayList<>();

        if(page > 0) page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<EntityEntity> entitiesPage = entityRepository.findAll(pageableRequest);
        List<EntityEntity> entities = entitiesPage.getContent();
        ModelMapper modelMapper = new ModelMapper();

        for(EntityEntity entityEntity : entities) {
            EntityDto entityDto = modelMapper.map(entityEntity, EntityDto.class);
            returnValue.add(entityDto);
        }

        return returnValue;
    }

    @Override
    public EntityDto getEntityByName(String entityName) {

        EntityEntity entityEntity = entityRepository.findByName(entityName);

        if(entityEntity == null)
            throw new EntityServiceException("Entity with name: " + entityName + " not found");

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(entityEntity, EntityDto.class);

    }

    @Override
    public List<EntityDto> getEntitiesforErdByUser(String userId, String erdId, int page, int limit) {
        List<EntityDto> returnValue = new ArrayList<>();

        if(page > 0) page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<EntityEntity> entitiesPage = entityRepository.findByErdDetailsErdIdAndErdDetailsUserDetailsUserId(erdId, userId, pageableRequest);
        List<EntityEntity> entities = entitiesPage.getContent();
        ModelMapper modelMapper = new ModelMapper();

        for(EntityEntity entityEntity : entities) {
            EntityDto entityDto = modelMapper.map(entityEntity, EntityDto.class);
            returnValue.add(entityDto);
        }

        return returnValue;

    }

}
