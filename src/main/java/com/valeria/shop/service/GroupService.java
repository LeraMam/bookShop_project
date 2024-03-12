package com.valeria.shop.service;

import com.valeria.shop.db.entity.GroupEntity;
import com.valeria.shop.db.repository.GroupRepository;
import com.valeria.shop.dto.GroupDTO;
import com.valeria.shop.exception.BadRequestException;
import com.valeria.shop.exception.NotFoundException;
import com.valeria.shop.mapper.GroupMapper;
import com.valeria.shop.request.CreateOrUpdateGroupRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    public List<GroupDTO> getAllGroups() {
        return groupMapper.mapEntitiesToDTOs(groupRepository.findAll());
    }

    public GroupDTO addNewGroup(CreateOrUpdateGroupRequest request) {
        groupRepository.findGroupByName(request.name()).ifPresent(GroupEntity -> {
            throw new BadRequestException("Группа " + request.name() + " уже существует");
        });
        GroupEntity group = groupMapper.mapToEntity(request);
        return groupMapper.mapEntityToDto(groupRepository.save(group));
    }

    public GroupEntity findEntityById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Группа не найдена"));
    }

    public void deleteGroup(Long id) {
        boolean exists = groupRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Группа не найдена");
        }
        groupRepository.deleteById(id);
    }

    @Transactional
    public GroupDTO updateGroup(Long id, CreateOrUpdateGroupRequest request) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Группа не найдена")
        );
        if (!Objects.equals(groupEntity.getName(), request.name())) {
            groupRepository.findGroupByName(request.name()).ifPresent(entity -> {
                throw new BadRequestException("Название группы " + request.name() + " уже используется, измените название");
            });
        }
        groupEntity.setName(request.name());
        return groupMapper.mapEntityToDto(groupEntity);
    }
}
