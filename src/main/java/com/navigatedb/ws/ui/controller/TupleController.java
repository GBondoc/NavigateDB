package com.navigatedb.ws.ui.controller;

import com.navigatedb.ws.ui.model.request.TupleDetailsRequestModel;
import com.navigatedb.ws.ui.model.response.OperationStatusModel;
import com.navigatedb.ws.ui.model.response.TupleRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/{userId}/erds/{erdId}/entities/{entityId}/tuples")
public class TupleController {




    @GetMapping(path = "/{tupleId}",
            produces = { MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE}
    )
    public TupleRest getEntityTuple(@PathVariable String tupleId) {

        return null;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public TupleRest createTuple(@PathVariable String userId,
                                 @PathVariable String erdId,
                                 @PathVariable String entityId,
                                 @RequestBody TupleDetailsRequestModel tupleDetails) {

        return null;
    }

    @PutMapping(path = "/{tupleId}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public TupleRest updateTuple(@PathVariable String tupleId,
                                 @RequestBody TupleDetailsRequestModel tupleDetails) {

        return null;
    }

    @DeleteMapping(path = "/{tupleId}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public OperationStatusModel deleteTuple(@PathVariable String tupleId) {

        return null;
    }

}
