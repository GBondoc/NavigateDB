package com.navigatedb.ws.ui.controller;

import com.navigatedb.ws.service.EntityRelationService;
import com.navigatedb.ws.service.EntityService;
import com.navigatedb.ws.shared.dto.EntityDto;
import com.navigatedb.ws.shared.dto.EntityRelationDto;
import com.navigatedb.ws.ui.model.response.EntityRelationRest;
import com.navigatedb.ws.ui.model.response.EntityRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users/{userId}/erds/{erdId}/entityRelation")
public class EntityRelationController {

    @Autowired
    EntityRelationService entityRelationService;

    @Autowired
    EntityService entityService;

    @GetMapping(path = "/{entityName}", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}
    )
    public List<EntityRest> getEntityRelations(@PathVariable String entityName) {

        List<EntityRest> returnValue = new ArrayList<>();

        List<EntityDto> entities = entityRelationService.getEntities(entityName);
        ModelMapper modelMapper = new ModelMapper();

        for(EntityDto entityDto : entities) {
            EntityRest entityModel = modelMapper.map(entityDto, EntityRest.class);
            returnValue.add(entityModel);
        }

        return returnValue;
    }

}
