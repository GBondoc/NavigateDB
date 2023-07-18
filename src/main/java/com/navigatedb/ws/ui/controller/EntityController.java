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

        return null;

    }

}
