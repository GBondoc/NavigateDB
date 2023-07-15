package com.navigatedb.ws.ui.controller;

import com.navigatedb.ws.exceptions.ErdServiceException;
import com.navigatedb.ws.exceptions.UserServiceException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    @GetMapping(path = "{id}", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ErdRest getErd(@PathVariable String id) {

        ErdDto erdDto = erdService.getErdByErdId(id);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(erdDto, ErdRest.class);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> createErd(@PathVariable String userId, @RequestBody ErdDetailsRequestModel erdDetails) throws ErdServiceException {

        try {
            if (erdDetails.getName().isEmpty())
                throw new ErdServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

            UserDto user = userService.getUserByUserId(userId);

            ErdDto newErd = new ErdDto();
            newErd.setName(erdDetails.getName());

            newErd.setUserDetails(user);

            ErdDto savedErd = erdService.createErd(newErd);

            user.getErds().add(savedErd);

            userService.updateUser(userId, user);

            return ResponseEntity.ok().build();
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured: " + e.getMessage());
        }
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ErdRest updateErd(@PathVariable String id, @RequestBody ErdDetailsRequestModel erdDetails) throws ErdServiceException {

        if(erdDetails.getName().isEmpty()) throw new ErdServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        ErdDto erdDto = modelMapper.map(erdDetails, ErdDto.class);

        ErdDto updateErd = erdService.updateErd(id, erdDto);

        return modelMapper.map(updateErd, ErdRest.class);
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
