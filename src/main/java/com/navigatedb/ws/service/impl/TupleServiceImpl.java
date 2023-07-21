package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.exceptions.TupleServiceException;
import com.navigatedb.ws.io.entity.TupleEntity;
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
    Utils utils;

    @Override
    public TupleDto createTuple(TupleDto tuple) throws TupleServiceException {

        if(tupleRepository.findByColumnName(tuple.getColumnName()) != null)
            throw new TupleServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        TupleEntity tupleEntity = modelMapper.map(tuple, TupleEntity.class);

        String publicTupleId = utils.generateTupleId(30);
        tupleEntity.setTupleId(publicTupleId);

        TupleEntity storedTupleDetails = tupleRepository.save(tupleEntity);

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
        return null;
    }

    @Override
    public void deleteTuple(String tupleId) {

    }
}
