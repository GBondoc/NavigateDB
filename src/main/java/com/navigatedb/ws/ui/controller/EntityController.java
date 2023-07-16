package com.navigatedb.ws.ui.controller;


import com.navigatedb.ws.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/{userId}/erds/{erdId}/entities")
public class EntityController {

    @Autowired
    EntityService entityService;

}
