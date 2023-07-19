package com.navigatedb.ws.ui.controller;

import com.navigatedb.ws.exceptions.ErdServiceException;
import com.navigatedb.ws.service.ErdService;
import com.navigatedb.ws.service.UserService;
import com.navigatedb.ws.shared.dto.ErdDto;
import com.navigatedb.ws.shared.dto.UserDto;
import com.navigatedb.ws.ui.model.request.ErdDetailsRequestModel;
import com.navigatedb.ws.ui.model.response.ErdRest;
import com.navigatedb.ws.ui.model.response.ErrorMessages;
import com.navigatedb.ws.ui.model.response.OperationStatusModel;
import com.navigatedb.ws.ui.model.response.RequestOperationStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users/{userId}/erds")
public class ErdController {

    @Autowired
    ErdService erdService;

    @Autowired
    UserService userService;


    @GetMapping(path = "/{erdId}", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}
    )
    public ErdRest getUserErd(@PathVariable String erdId) {

        ErdDto erdDto = erdService.getErd(erdId);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(erdDto, ErdRest.class);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ErdRest createErd(@PathVariable String userId, @RequestBody ErdDetailsRequestModel erdDetails) throws ErdServiceException {

        if (erdDetails.getName().isEmpty())
            throw new ErdServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto user = userService.getUserByUserId(userId);

        if(user == null)
            throw new ErdServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());

        ErdDto newErd = new ErdDto();
        newErd.setName(erdDetails.getName());

        newErd.setUserDetails(user);

        ErdDto savedErd = erdService.createErd(newErd);

        user.getErds().add(savedErd);

        userService.updateUser(userId, user);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(savedErd, ErdRest.class);
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ErdRest updateErd(@PathVariable String id, @RequestBody ErdDetailsRequestModel erdDetails) throws ErdServiceException {

        if(erdDetails.getName().isEmpty()) throw new ErdServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        ErdDto erdDto = modelMapper.map(erdDetails, ErdDto.class);

        ErdDto updatedErd = erdService.updateErd(id, erdDto);

        return modelMapper.map(updatedErd, ErdRest.class);
    }

    @DeleteMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public OperationStatusModel deleteErd(@PathVariable String id) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        erdService.deleteErd(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<ErdRest> getErds(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "limit", defaultValue = "50") int limit) {
        List<ErdRest> returnValue = new ArrayList<>();

        List<ErdDto> erds = erdService.getErds(page, limit);
        ModelMapper modelMapper = new ModelMapper();

        for(ErdDto erdDto : erds) {
            ErdRest erdModel = modelMapper.map(erdDto, ErdRest.class);
            returnValue.add(erdModel);
        }

        return returnValue;
    }

}
