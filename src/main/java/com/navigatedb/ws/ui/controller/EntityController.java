package com.navigatedb.ws.ui.controller;


import com.navigatedb.ws.exceptions.EntityServiceException;
import com.navigatedb.ws.service.EntityService;
import com.navigatedb.ws.service.ErdService;
import com.navigatedb.ws.service.UserService;
import com.navigatedb.ws.shared.dto.EntityDto;
import com.navigatedb.ws.shared.dto.ErdDto;
import com.navigatedb.ws.shared.dto.UserDto;
import com.navigatedb.ws.ui.model.request.EntityDetailsRequestModel;
import com.navigatedb.ws.ui.model.response.EntityRest;
import com.navigatedb.ws.ui.model.response.ErrorMessages;
import com.navigatedb.ws.ui.model.response.OperationStatusModel;
import com.navigatedb.ws.ui.model.response.RequestOperationStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users/{userId}/erds/{erdId}/entities")
@PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
public class EntityController {

    @Autowired
    EntityService entityService;

    @Autowired
    ErdService erdService;

    @Autowired
    UserService userService;

    @GetMapping(path = "/{entityId}", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}
    )
    public EntityRest getEntity(@PathVariable String entityId) {

        EntityDto entityDto = entityService.getEntity(entityId);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(entityDto, EntityRest.class);
    }

    @GetMapping(path = "/name/{entityName}", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}
    )
    public EntityRest getEntityByName(@PathVariable String entityName) {

        EntityDto entityDto = entityService.getEntityByName(entityName);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(entityDto, EntityRest.class);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public EntityRest createEntity(@PathVariable String userId,
                                   @PathVariable String erdId,
                                   @RequestBody EntityDetailsRequestModel entityDetails) {

        if(entityDetails.getName().isEmpty())
            throw new EntityServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto user = userService.getUserByUserId(userId);

        if(user == null)
            throw new EntityServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());

        ErdDto erd = erdService.getErdByErdId(erdId);

        if(erd == null || !erd.getUserDetails().getUserId().equals(user.getUserId()))
            throw new EntityServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());

        EntityDto entity = new EntityDto();
        entity.setName(entityDetails.getName());
        entity.setRowCount(entityDetails.getRowCount());
        entity.setErdDetails(erd);

        EntityDto savedEntity = entityService.createEntity(entity);

        erd.getEntities().add(savedEntity);

        erdService.updateErd(erdId, erd);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(savedEntity, EntityRest.class);
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public EntityRest updateEntity(@PathVariable String id,
                                   @RequestBody EntityDetailsRequestModel entityDetails) throws EntityServiceException {

        if(entityDetails.getName().isEmpty())
            throw new EntityServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        EntityDto entityDto = modelMapper.map(entityDetails, EntityDto.class);

        EntityDto updateEntity = entityService.updateEntity(id, entityDto);

        return modelMapper.map(updateEntity, EntityRest.class);
    }

    @DeleteMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public OperationStatusModel deleteEntity(@PathVariable String id) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        entityService.deleteEntity(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    /*
    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<EntityRest> getEntities(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "limit", defaultValue = "50") int limit) {
        List<EntityRest> returnValue = new ArrayList<>();

        List<EntityDto> entities = entityService.getEntities(page, limit);
        ModelMapper modelMapper = new ModelMapper();

        for(EntityDto entityDto : entities) {
            EntityRest entityModel = modelMapper.map(entityDto, EntityRest.class);
            returnValue.add(entityModel);
        }

        return returnValue;
    }
    */
    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<EntityRest> getEntities(@PathVariable String userId,
                                        @PathVariable String erdId,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "limit", defaultValue = "50") int limit) {
        List<EntityRest> returnValue = new ArrayList<>();

        List<EntityDto> entities = entityService.getEntitiesforErdByUser(userId, erdId, page, limit);
        ModelMapper modelMapper = new ModelMapper();

        for(EntityDto entityDto : entities) {
            EntityRest entityModel = modelMapper.map(entityDto, EntityRest.class);
            returnValue.add(entityModel);
        }

        return returnValue;
    }

}
