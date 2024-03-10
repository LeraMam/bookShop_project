package com.valeria.bookshop.controller;

import com.valeria.bookshop.dto.GroupDTO;
import com.valeria.bookshop.request.CreateOrUpdateGroupRequest;
import com.valeria.bookshop.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/publisher")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public List<GroupDTO> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public GroupDTO addNewGroup(@RequestBody CreateOrUpdateGroupRequest request){
        return groupService.addNewGroup(request);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(path="{id}")
    public void deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(path= "{id}")
    public GroupDTO updateGroup(@PathVariable Long id, @RequestBody CreateOrUpdateGroupRequest request){
        return groupService.updateGroup(id, request);
    }
}
