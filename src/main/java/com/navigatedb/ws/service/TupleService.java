package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.TupleDto;

public interface TupleService {
    TupleDto createTuple(TupleDto tuple);
    TupleDto getTuple(String tupleId);
    TupleDto updateTuple(String tupleId, TupleDto tuple);
    void deleteTuple(String tupleId);
}
