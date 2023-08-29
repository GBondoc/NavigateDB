package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.ErdDto;

import java.util.List;

public interface ErdService {
    ErdDto createErd(ErdDto erd);

    ErdDto getErdByErdId(String userId, String erdId);

    ErdDto updateErd(String erdId, ErdDto erd);

    void deleteErd(String erdId);

    List<ErdDto> getErds(int page, int limit);

    List<ErdDto> getErds(String userId);

    ErdDto getErd(String erdId);

    List<ErdDto> getErdsForUser(String userId, int page, int limit);

    ErdDto getErdByName(String userId, String erdName);
}
