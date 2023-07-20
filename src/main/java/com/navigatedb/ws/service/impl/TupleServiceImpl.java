package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.repository.TupleRepository;
import com.navigatedb.ws.service.TupleService;
import com.navigatedb.ws.shared.dto.TupleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TupleServiceImpl implements TupleService {

    @Autowired
    TupleRepository tupleRepository;

    @Override
    public TupleDto createTuple(TupleDto tuple) {
        return null;
    }

    @Override
    public TupleDto getTuple(String tupleId) {
        return null;
    }

    @Override
    public TupleDto updateTuple(String tupleId, TupleDto tuple) {
        return null;
    }

    @Override
    public void deleteTuple(String tupleId) {

    }
}
