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
import com.navigatedb.ws.ui.model.response.RequestOperationStatus;
import com.navigatedb.ws.ui.model.response.TupleRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users/{userId}/erds/{erdId}/entities/{entityId}/tuples")
@PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
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

        ErdDto erd = erdService.getErdByErdId(userId, erdId);

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

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        tupleService.deleteTuple(tupleId);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    /*
    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<TupleRest> getTuples(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "50") int limit) {

        List<TupleRest> returnValue = new ArrayList<>();

        List<TupleDto> tuples = tupleService.getTuples(page, limit);
        ModelMapper modelMapper = new ModelMapper();

        for(TupleDto tupleDto : tuples) {
            TupleRest tupleModel = modelMapper.map(tupleDto, TupleRest.class);
            returnValue.add(tupleModel);
        }

        return returnValue;
    }
     */

    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<TupleRest> getTuples(@PathVariable String userId,
                                     @PathVariable String erdId,
                                     @PathVariable String entityId,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "50") int limit) {

        List<TupleRest> returnValue = new ArrayList<>();

        List<TupleDto> tuples = tupleService.getTuplesForEntity(entityId, erdId, userId, page, limit);
        ModelMapper modelMapper = new ModelMapper();

        for(TupleDto tupleDto : tuples) {
            TupleRest tupleModel = modelMapper.map(tupleDto, TupleRest.class);
            returnValue.add(tupleModel);
        }

        return returnValue;
    }

}
