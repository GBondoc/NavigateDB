package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.exceptions.ErdServiceException;
import com.navigatedb.ws.io.entity.ErdEntity;
import com.navigatedb.ws.repository.ErdRepository;
import com.navigatedb.ws.service.ErdService;
import com.navigatedb.ws.shared.Utils;
import com.navigatedb.ws.shared.dto.ErdDto;
import com.navigatedb.ws.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErdServiceImpl implements ErdService {

    @Autowired
    ErdRepository erdRepository;

    @Autowired
    Utils utils;

    @Override
    public ErdDto createErd(ErdDto erd) {

        if(erdRepository.findByName(erd.getName()) != null) throw new ErdServiceException("Erd already exists");

        ErdEntity erdEntity = new ErdEntity();
        BeanUtils.copyProperties(erd, erdEntity);

        String publicErdId = utils.generateErdId(30);
        erdEntity.setErdId(publicErdId);

        ErdEntity storedErdDetails = erdRepository.save(erdEntity);

        ErdDto returnValue = new ErdDto();
        BeanUtils.copyProperties(storedErdDetails, returnValue);

        return returnValue;
    }

    @Override
    public ErdDto getErdByErdId(String erdId) {
        ErdDto returnValue = new ErdDto();
        ErdEntity erdEntity = erdRepository.findByErdId(erdId);

        if(erdEntity == null) throw new ErdServiceException("ERD with ID: " + erdId + " not found");

        BeanUtils.copyProperties(erdEntity, returnValue);

        return returnValue;
    }

    @Override
    public ErdDto updateErd(String erdId, ErdDto erd) {
        ErdDto returnValue = new ErdDto();
        ErdEntity erdEntity = erdRepository.findByErdId(erdId);

        if(erdEntity == null) throw new ErdServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        erdEntity.setName(erd.getName());

        ErdEntity updatedErdDetails = erdRepository.save(erdEntity);

        BeanUtils.copyProperties(updatedErdDetails, returnValue);

        return returnValue;
    }

    @Override
    public void deleteErd(String erdId) {
        ErdEntity erdEntity = erdRepository.findByErdId(erdId);

        if(erdEntity == null) throw new ErdServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        erdRepository.delete(erdEntity);
    }

    @Override
    public List<ErdDto> getErds(int page, int limit) {
        List<ErdDto> returnValue = new ArrayList<>();

        if(page > 0) page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<ErdEntity> erdsPage = erdRepository.findAll(pageableRequest);
        List<ErdEntity> erds = erdsPage.getContent();

        for(ErdEntity erdEntity : erds) {
            ErdDto erdDto = new ErdDto();
            BeanUtils.copyProperties(erdEntity, erdDto);
            returnValue.add(erdDto);
        }

        return returnValue;
    }

}
