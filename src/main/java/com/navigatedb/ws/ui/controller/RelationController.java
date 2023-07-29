package com.navigatedb.ws.ui.controller;

import com.navigatedb.ws.exceptions.RelationServiceException;
import com.navigatedb.ws.service.RelationService;
import com.navigatedb.ws.shared.dto.RelationDto;
import com.navigatedb.ws.ui.model.request.RelationDetailsRequestModel;
import com.navigatedb.ws.ui.model.response.ErrorMessages;
import com.navigatedb.ws.ui.model.response.OperationStatusModel;
import com.navigatedb.ws.ui.model.response.RelationRest;
import com.navigatedb.ws.ui.model.response.RequestOperationStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("relations")
public class RelationController {

    @Autowired
    RelationService relationService;


    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public RelationRest getRelation(@PathVariable String id) {

        RelationDto relationDto = relationService.getRelationByRelationId(id);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(relationDto, RelationRest.class);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public RelationRest createRelation(@RequestBody RelationDetailsRequestModel relationDetails) {

        if(relationDetails.getRelationType().isEmpty() || relationDetails.getNullable().isEmpty())
            throw new RelationServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        RelationDto relationDto = modelMapper.map(relationDetails, RelationDto.class);

        RelationDto createdRelation = relationService.createRelation(relationDto);

        return modelMapper.map(createdRelation, RelationRest.class);
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public RelationRest updateRelation(@PathVariable String id,
                                       @RequestBody RelationDetailsRequestModel relationDetails) {

        if(relationDetails.getRelationType().isEmpty()
        || relationDetails.getNullable().isEmpty())
            throw new RelationServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        RelationDto relationDto = modelMapper.map(relationDetails, RelationDto.class);

        RelationDto updatedRelation = relationService.updateRelation(id, relationDto);

        return modelMapper.map(updatedRelation, RelationRest.class);
    }

    @DeleteMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public OperationStatusModel deleteRelation(@PathVariable String id) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        relationService.deleteRelation(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;

    }

}



















