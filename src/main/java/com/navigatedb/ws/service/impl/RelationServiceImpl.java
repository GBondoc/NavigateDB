package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.exceptions.RelationServiceException;
import com.navigatedb.ws.io.entity.RelationEntity;
import com.navigatedb.ws.repository.RelationRepository;
import com.navigatedb.ws.service.RelationService;
import com.navigatedb.ws.shared.Utils;
import com.navigatedb.ws.shared.dto.RelationDto;
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
public class RelationServiceImpl implements RelationService {

    @Autowired
    RelationRepository relationRepository;

    @Autowired
    Utils utils;

    @Override
    public RelationDto createRelation(RelationDto relation) {

        if((relationRepository.findByRelationTypeAndNullable(relation.getRelationType(), relation.getNullable()) != null)
        && (relationRepository.findByNullable(relation.getNullable()) != null))
            throw new RelationServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        RelationEntity relationEntity = modelMapper.map(relation, RelationEntity.class);

        String publicRelationId = utils.generateRelationId(30);
        relationEntity.setRelationId(publicRelationId);

        RelationEntity storedRelationDetails = relationRepository.save(relationEntity);

        return modelMapper.map(storedRelationDetails, RelationDto.class);
    }

    @Override
    public RelationDto getRelationByRelationId(String relationId) {

        RelationEntity relationEntity = relationRepository.findByRelationId(relationId);

        if(relationEntity == null) throw new RelationServiceException("Relation with ID: " + relationId + " not found");

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(relationEntity, RelationDto.class);
    }

    @Override
    public RelationDto updateRelation(String relationId, RelationDto relation) {
        RelationEntity relationEntity = relationRepository.findByRelationId(relationId);

        if(relationEntity == null)
            throw new RelationServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        relationEntity.setRelationType(relation.getRelationType());
        relationEntity.setNullable(relation.getNullable());

        RelationEntity updatedRelationDetails = relationRepository.save(relationEntity);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(updatedRelationDetails, RelationDto.class);
    }

    @Override
    public void deleteRelation(String relationId) {
        RelationEntity relationEntity = relationRepository.findByRelationId(relationId);

        if(relationEntity == null)
            throw new RelationServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        relationRepository.delete(relationEntity);
    }

    @Override
    public List<RelationDto> getRelations(int page, int limit) {
        List<RelationDto> returnValue = new ArrayList<>();

        if(page > 0) page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<RelationEntity> relationsPage = relationRepository.findAll(pageableRequest);
        List<RelationEntity> relations = relationsPage.getContent();
        ModelMapper modelMapper = new ModelMapper();

        for(RelationEntity relationEntity : relations) {
            RelationDto relationDto = modelMapper.map(relationEntity, RelationDto.class);
            returnValue.add(relationDto);
        }

        return returnValue;
    }

    @Override
    public RelationDto getRelationByType(String relationName, String nullable) {

        RelationEntity relationEntity = relationRepository.findByRelationTypeAndNullable(relationName, nullable);

        if(relationEntity == null) throw new RelationServiceException("Relation with name: " + relationName + " not found");

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(relationEntity, RelationDto.class);

    }
}
