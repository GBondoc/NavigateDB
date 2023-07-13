package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.exceptions.ErdServiceException;
import com.navigatedb.ws.io.entity.ErdEntity;
import com.navigatedb.ws.io.entity.UserEntity;
import com.navigatedb.ws.repository.ErdRepository;
import com.navigatedb.ws.repository.UserRepository;
import com.navigatedb.ws.service.ErdService;
import com.navigatedb.ws.shared.Utils;
import com.navigatedb.ws.shared.dto.ErdDto;
import com.navigatedb.ws.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
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
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public ErdDto createErd(ErdDto erd) {

        if(erdRepository.findByName(erd.getName()) != null) throw new ErdServiceException("Erd already exists");

        ModelMapper modelMapper = new ModelMapper();
        ErdEntity erdEntity = modelMapper.map(erd, ErdEntity.class);

        String publicErdId = utils.generateErdId(30);
        erdEntity.setErdId(publicErdId);

        ErdEntity storedErdDetails = erdRepository.save(erdEntity);

        return modelMapper.map(storedErdDetails, ErdDto.class);
    }

    @Override
    public ErdDto getErdByErdId(String erdId) {

        ErdEntity erdEntity = erdRepository.findByErdId(erdId);

        if(erdEntity == null) throw new ErdServiceException("ERD with ID: " + erdId + " not found");

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(erdEntity, ErdDto.class);
    }

    @Override
    public ErdDto updateErd(String erdId, ErdDto erd) {

        ErdEntity erdEntity = erdRepository.findByErdId(erdId);

        if(erdEntity == null) throw new ErdServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        erdEntity.setName(erd.getName());

        ErdEntity updatedErdDetails = erdRepository.save(erdEntity);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(updatedErdDetails, ErdDto.class);
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
        ModelMapper modelMapper = new ModelMapper();

        for(ErdEntity erdEntity : erds) {
            ErdDto erdDto = modelMapper.map(erdEntity, ErdDto.class);
            returnValue.add(erdDto);
        }

        return returnValue;
    }

    @Override
    public List<ErdDto> getErds(String userId) {
        List<ErdDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) return returnValue;

        Iterable<ErdEntity> erds = erdRepository.findAllByUserDetails(userEntity);

        for(ErdEntity erdEntity : erds) {
            returnValue.add( modelMapper.map(erdEntity, ErdDto.class) );
        }

        return  returnValue;
    }

    @Override
    public ErdDto getErd(String erdId) {
        ErdDto returnValue = null;

        ErdEntity erdEntity = erdRepository.findByErdId(erdId);

        if(erdEntity != null) {
            returnValue = new ModelMapper().map(erdEntity, ErdDto.class);
        }

        return returnValue;
    }

}
