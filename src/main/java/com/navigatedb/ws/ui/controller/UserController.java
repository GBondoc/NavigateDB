package com.navigatedb.ws.ui.controller;

import com.navigatedb.ws.exceptions.UserServiceException;
import com.navigatedb.ws.service.ErdService;
import com.navigatedb.ws.service.UserService;
import com.navigatedb.ws.shared.dto.ErdDto;
import com.navigatedb.ws.shared.dto.UserDto;
import com.navigatedb.ws.ui.model.request.UserDetailsRequestModel;
import com.navigatedb.ws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ErdService erdService;

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

        if(userDetails.getUsername().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);

        return modelMapper.map(createdUser, UserRest.class);
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest updateUser (@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        if(userDetails.getUsername().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updatedUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public OperationStatusModel deleteUser(@PathVariable String id) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "50") int limit) {
        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        for(UserDto userDto : users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }

        return returnValue;
    }

    @GetMapping(path = "/{id}/erds", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}
    )
    public List<ErdRest> getUserErds(@PathVariable String id) {
        List<ErdRest> returnValue = new ArrayList<>();

        List<ErdDto> erdsDto = erdService.getErds(id);

        if(erdsDto != null && !erdsDto.isEmpty()) {
            java.lang.reflect.Type listType = new TypeToken<List<ErdRest>>() {}.getType();
            returnValue = new ModelMapper().map(erdsDto, listType);
        }

        return returnValue;
    }

    @GetMapping(path = "/{userId}/erds/{erdId}", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}
    )
    public ErdRest getUserErd(@PathVariable String erdId) {

        ErdDto erdDto = erdService.getErd(erdId);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(erdDto, ErdRest.class);

    }

}
