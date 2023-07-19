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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/{userId}/erds/{erdId}/entities")
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

        if(erd == null || erd.getUserDetails().getUserId() != user.getUserId())
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

}
