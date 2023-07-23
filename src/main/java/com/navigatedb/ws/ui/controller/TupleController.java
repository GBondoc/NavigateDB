package com.navigatedb.ws.ui.controller;

import com.navigatedb.ws.exceptions.TupleServiceException;
import com.navigatedb.ws.service.EntityService;
import com.navigatedb.ws.service.ErdService;
import com.navigatedb.ws.service.TupleService;
import com.navigatedb.ws.service.UserService;
import com.navigatedb.ws.shared.dto.EntityDto;
import com.navigatedb.ws.shared.dto.ErdDto;
import com.navigatedb.ws.shared.dto.TupleDto;
import com.navigatedb.ws.shared.dto.UserDto;
import com.navigatedb.ws.ui.model.request.TupleDetailsRequestModel;
import com.navigatedb.ws.ui.model.response.ErrorMessages;
import com.navigatedb.ws.ui.model.response.OperationStatusModel;
import com.navigatedb.ws.ui.model.response.TupleRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/{userId}/erds/{erdId}/entities/{entityId}/tuples")
public class TupleController {


    @Autowired
    UserService userService;

    @Autowired
    ErdService erdService;

    @Autowired
    EntityService entityService;

    @Autowired
    TupleService tupleService;


    @GetMapping(path = "/{tupleId}",
            produces = { MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE}
    )
    public TupleRest getEntityTuple(@PathVariable String tupleId) {

        TupleDto tupleDto = tupleService.getTuple(tupleId);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(tupleDto, TupleRest.class);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public TupleRest createTuple(@PathVariable String userId,
                                 @PathVariable String erdId,
                                 @PathVariable String entityId,
                                 @RequestBody TupleDetailsRequestModel tupleDetails) {

        if(tupleDetails.getConstraintType().isEmpty()
        || tupleDetails.getColumnName().isEmpty()
        || tupleDetails.getDataType().isEmpty())
            throw new TupleServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto user = userService.getUserByUserId(userId);

        if(user == null)
            throw new TupleServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());

        ErdDto erd = erdService.getErdByErdId(erdId);

        if(erd == null
        || !erd.getUserDetails().getUserId().equals(user.getUserId()))
            throw new TupleServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());

        EntityDto entity = entityService.getEntity(entityId);

        if(entity == null
        || !entity.getErdDetails().getErdId().equals(erd.getErdId()))
            throw new TupleServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());

        TupleDto newTuple = new TupleDto();
        newTuple.setConstraintType(tupleDetails.getConstraintType());
        newTuple.setColumnName(tupleDetails.getColumnName());
        newTuple.setDataType(tupleDetails.getDataType());

        newTuple.setEntity(entity);

        TupleDto savedTuple = tupleService.createTuple(newTuple);

        entity.getTuples().add(savedTuple);

        entityService.updateEntity(entityId, entity);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(savedTuple, TupleRest.class);
    }

    @PutMapping(path = "/{tupleId}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public TupleRest updateTuple(@PathVariable String tupleId,
                                 @RequestBody TupleDetailsRequestModel tupleDetails) {

        ModelMapper modelMapper = new ModelMapper();
        TupleDto tupleDto = modelMapper.map(tupleDetails, TupleDto.class);

        TupleDto updateTuple = tupleService.updateTuple(tupleId, tupleDto);

        return modelMapper.map(updateTuple, TupleRest.class);
    }

    @DeleteMapping(path = "/{tupleId}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public OperationStatusModel deleteTuple(@PathVariable String tupleId) {

        return null;
    }

}
