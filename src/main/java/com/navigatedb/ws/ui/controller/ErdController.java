package com.navigatedb.ws.ui.controller;

import com.navigatedb.ws.exceptions.ErdServiceException;
import com.navigatedb.ws.service.ErdService;
import com.navigatedb.ws.shared.dto.ErdDto;
import com.navigatedb.ws.ui.model.request.ErdDetailsRequestModel;
import com.navigatedb.ws.ui.model.response.ErdRest;
import com.navigatedb.ws.ui.model.response.ErrorMessages;
import com.navigatedb.ws.ui.model.response.OperationStatusModel;
import com.navigatedb.ws.ui.model.response.RequestOperationStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("erds")
public class ErdController {

    @Autowired
    ErdService erdService;


    @GetMapping(path = "{id}", produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ErdRest getErd(@PathVariable String id) {
        ErdRest returnValue = new ErdRest();

        ErdDto erdDto = erdService.getErdByErdId(id);
        BeanUtils.copyProperties(erdDto, returnValue);

        return returnValue;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ErdRest createErd(@RequestBody ErdDetailsRequestModel erdDetails) throws Exception {
        ErdRest returnValue = new ErdRest();

        if(erdDetails.getName().isEmpty()) throw new ErdServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ErdDto erdDto = new ErdDto();
        BeanUtils.copyProperties(erdDetails, erdDto);

        ErdDto createdErd = erdService.createErd(erdDto);
        BeanUtils.copyProperties(createdErd, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ErdRest updateErd(@PathVariable String id, @RequestBody ErdDetailsRequestModel erdDetails) {
        ErdRest returnValue = new ErdRest();

        if(erdDetails.getName().isEmpty()) throw new ErdServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ErdDto erdDto = new ErdDto();
        BeanUtils.copyProperties(erdDetails, erdDto);

        ErdDto updateErd = erdService.updateErd(id, erdDto);
        BeanUtils.copyProperties(updateErd, returnValue);

        return returnValue;
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

}
