package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.ErdDto;

public interface ErdService {
    ErdDto createErd(ErdDto erd);

    ErdDto getErdByErdId(String erdId);

    ErdDto updateErd(String erdId, ErdDto erd);

    void deleteErd(String erdId);
}
