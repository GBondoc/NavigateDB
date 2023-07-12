package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.ErdDto;

import java.util.List;

public interface ErdService {
    ErdDto createErd(ErdDto erd);

    ErdDto getErdByErdId(String erdId);

    ErdDto updateErd(String erdId, ErdDto erd);

    void deleteErd(String erdId);

    List<ErdDto> getErds(int page, int limit);
}
