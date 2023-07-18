package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.repository.EntityRepository;
import com.navigatedb.ws.service.EntityService;
import com.navigatedb.ws.shared.dto.EntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityServiceImpl implements EntityService {

    @Autowired
    EntityRepository entityRepository;

    public EntityDto createEntity(EntityDto entity) {
        return null;
    }

    @Override
    public EntityDto getEntity(String entityId) {
        return null;
    }


}
