package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.exceptions.TupleServiceException;
import com.navigatedb.ws.io.entity.EntityEntity;
import com.navigatedb.ws.io.entity.TupleEntity;
import com.navigatedb.ws.repository.EntityRepository;
import com.navigatedb.ws.repository.TupleRepository;
import com.navigatedb.ws.service.TupleService;
import com.navigatedb.ws.shared.Utils;
import com.navigatedb.ws.shared.dto.TupleDto;
import com.navigatedb.ws.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TupleServiceImpl implements TupleService {

    @Autowired
    TupleRepository tupleRepository;

    @Autowired
    EntityRepository entityRepository;

    @Autowired
    Utils utils;

    @Override
    public TupleDto createTuple(TupleDto tuple) throws TupleServiceException {
        // Check if the tuple with the same columnName already exists
        if (tupleRepository.findByColumnName(tuple.getColumnName()) != null) {
            throw new TupleServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        // Fetch the corresponding EntityEntity from the database
        EntityEntity entityEntity = entityRepository.findById(tuple.getEntity().getId()).orElse(null);

        // Check if the EntityEntity exists
        if (entityEntity == null) {
            throw new TupleServiceException("EntityEntity with id " + tuple.getEntity().getId() + " not found.");
        }

        // Map the TupleDto to TupleEntity
        ModelMapper modelMapper = new ModelMapper();
        TupleEntity tupleEntity = modelMapper.map(tuple, TupleEntity.class);

        // Set the associated EntityEntity
        tupleEntity.setEntityDetails(entityEntity);

        // Generate the tupleId and set it
        String publicTupleId = utils.generateTupleId(30);
        tupleEntity.setTupleId(publicTupleId);

        // Save the TupleEntity and get the stored TupleEntity
        TupleEntity storedTupleDetails = tupleRepository.save(tupleEntity);

        // Map the stored TupleEntity back to TupleDto and return it
        return modelMapper.map(storedTupleDetails, TupleDto.class);
    }

    @Override
    public TupleDto getTuple(String tupleId) {

        TupleEntity tupleEntity = tupleRepository.findByTupleId(tupleId);

        if(tupleEntity == null)
            throw new TupleServiceException("Tuple with ID: " + tupleId + "not found");

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(tupleEntity, TupleDto.class);
    }

    @Override
    public TupleDto updateTuple(String tupleId, TupleDto tuple) {

        TupleEntity tupleEntity = tupleRepository.findByTupleId(tupleId);

        if(tupleEntity == null)
            throw new TupleServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        if(tuple.getConstraintType() != null)
            tupleEntity.setConstraintType(tuple.getConstraintType());

        if(tuple.getColumnName() != null)
            tupleEntity.setColumnName(tuple.getColumnName());

        if(tuple.getDataType() != null)
            tupleEntity.setDataType(tuple.getDataType());

        TupleEntity updatedTupleDetails = tupleRepository.save(tupleEntity);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(updatedTupleDetails, TupleDto.class);
    }

    @Override
    public void deleteTuple(String tupleId) {

        TupleEntity tupleEntity = tupleRepository.findByTupleId(tupleId);

        if(tupleEntity == null)
            throw new TupleServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        tupleRepository.delete(tupleEntity);
    }
}
