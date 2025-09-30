package org.springclass.onlinebankingsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclass.onlinebankingsystem.repository.entity.Role;
import org.springclass.onlinebankingsystem.service.RoleService;
import org.springclass.onlinebankingsystem.shared.object.ResponseObjectMap;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Slf4j
@RestController
@RequestMapping("api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final ResponseObjectMap responseObjectMap;

    @GetMapping("{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        return responseObjectMap.responseObject(roleService.findById(id));
    }

    @PostMapping
    public Map<String, Object> createRole(@RequestBody Role request) {
        return responseObjectMap.responseObject(roleService.add(request));
    }

    @PutMapping("update")
    public Map<String, Object> updateRole(@RequestBody Role request) {
        return responseObjectMap.responseObject(roleService.update(request));
    }

    @GetMapping("/all")
    public Map<String, Object> findAllRole() {
        return responseObjectMap.responseObject(roleService.findAll());
    }

    @GetMapping("/list")
    public Map<String, Object> getList(
            @RequestParam(required = false) Optional<String> query,
            @RequestParam int page,
            @RequestParam int size
    ) {
        var item = roleService.findAll(query, page, size);
        return responseObjectMap.responseObject(item.getContent(), item.getTotalElements(), page, size);
    }
}
