package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.exceptions.EntityRelationServiceException;
import com.navigatedb.ws.exceptions.EntityServiceException;
import com.navigatedb.ws.io.entity.EntityEntity;
import com.navigatedb.ws.io.entity.EntityRelationEntity;
import com.navigatedb.ws.io.entity.RelationEntity;
import com.navigatedb.ws.repository.EntityRelationRepository;
import com.navigatedb.ws.repository.EntityRepository;
import com.navigatedb.ws.repository.RelationRepository;
import com.navigatedb.ws.service.EntityRelationService;
import com.navigatedb.ws.shared.Utils;
import com.navigatedb.ws.shared.dto.EntityDto;
import com.navigatedb.ws.shared.dto.EntityRelationDto;
import com.navigatedb.ws.ui.model.request.EntityRelationTargetsDetailsRequestModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityRelationServiceImpl implements EntityRelationService {

    @Autowired
    Utils utils;

    @Autowired
    EntityRelationRepository entityRelationRepository;

    @Autowired
    EntityRepository entityRepository;

    @Autowired
    RelationRepository relationRepository;

    @Override
    public List<EntityDto> getEntities(String entityName) {

        List<EntityDto> returnValue = new ArrayList<>();

        EntityEntity entityEntity = entityRepository.findByName(entityName);

        if(entityEntity == null)
            throw new EntityServiceException("Entity with name: " + entityName + " not found");

        Iterable<EntityRelationEntity> entityRelations = entityRelationRepository.findAllByEntityDetails(entityEntity);
        ModelMapper modelMapper = new ModelMapper();

        for(EntityRelationEntity entityRelation :entityRelations) {
            String channel = entityRelation.getChannel();
            Iterable<EntityRelationDto> correlations = getEntitiesByChannel(channel);
            for(EntityRelationDto correlation : correlations) {
                EntityEntity correlatedEntity = modelMapper.map(correlation.getEntityDetails(), EntityEntity.class);
                if(correlatedEntity != entityEntity) {
                    EntityDto correlatedEntityDto = modelMapper.map(correlatedEntity, EntityDto.class);
                    returnValue.add(correlatedEntityDto);
                }
            }
        }

        return returnValue;

    }

    @Override
    public List<EntityRelationDto> getEntitiesByChannel(String channel) {

        List<EntityRelationDto> returnValue = new ArrayList<>();

        Iterable<EntityRelationEntity> entityRelationEntity = entityRelationRepository.findAllByChannel(channel);
        ModelMapper modelMapper = new ModelMapper();

        for(EntityRelationEntity entityRelation : entityRelationEntity) {
            returnValue.add(modelMapper.map(entityRelation, EntityRelationDto.class));
        }

        return returnValue;
    }

    @Override
    public void createEntityRelation(EntityRelationTargetsDetailsRequestModel relation) {

        EntityEntity sender = entityRepository.findByName(relation.getEntitySenderName());
        EntityEntity receiver = entityRepository.findByName(relation.getEntityReceiverName());
        RelationEntity type = relationRepository.findByRelationId(relation.getRelationId());

        if(sender == null)
            throw new EntityRelationServiceException("Entity with name: " + sender.getName() + " not found");

        if(receiver == null)
            throw new EntityRelationServiceException("Entity with name: " + receiver.getName() + " not found");

        if(type == null)
            throw new EntityRelationServiceException("Relation with name: " + type.getRelationType() + " not found");

        RelationEntity relationReceiver;
        if(type.getRelationType().equals("OneToMany")) {
            relationReceiver = relationRepository.findByRelationTypeAndNullable("ManyToOne", type.getNullable());
        } else if(type.getRelationType().equals("ManyToOne")) {
            relationReceiver = relationRepository.findByRelationTypeAndNullable("OneToMany", type.getNullable());
        } else {
            relationReceiver = type;
        }

        ModelMapper modelMapper = new ModelMapper();

        String channel = utils.generateEntityRelationChannel(30);

        EntityRelationEntity entityRelationEntitySender = new EntityRelationEntity();

        entityRelationEntitySender.setEntityRelationId(utils.generateEntityRelationId(30));
        entityRelationEntitySender.setChannel(channel);
        entityRelationEntitySender.setEntityDetails(sender);
        entityRelationEntitySender.setRelationDetails(type);

        EntityRelationEntity entityRelationEntityReceiver = new EntityRelationEntity();

        entityRelationEntityReceiver.setEntityRelationId(utils.generateEntityRelationId(30));
        entityRelationEntityReceiver.setChannel(channel);
        entityRelationEntityReceiver.setEntityDetails(receiver);
        entityRelationEntityReceiver.setRelationDetails(relationReceiver);

        entityRelationRepository.save(entityRelationEntitySender);
        entityRelationRepository.save(entityRelationEntityReceiver);

    }


}
