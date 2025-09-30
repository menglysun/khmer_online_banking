package org.springclass.onlinebankingsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclass.onlinebankingsystem.controller.request.UpdateUserRequest;
import org.springclass.onlinebankingsystem.controller.request.UserRoleRequest;
import org.springclass.onlinebankingsystem.controller.response.UserResponse;
import org.springclass.onlinebankingsystem.service.UserService;
import org.springclass.onlinebankingsystem.shared.object.ResponseObjectMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final ResponseObjectMap responseObjectMap;

    @GetMapping("/list")
    public Map<String, Object> getList(
            @RequestParam(required = false) String query,
            @RequestParam int page,
            @RequestParam int size
    ) {
        var item = service.findAll(query, page, size);
        return responseObjectMap.responseObject(item.getContent(), item.getTotalElements(), page, size);
    }

    @GetMapping("{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        return responseObjectMap.responseObject(new UserResponse(service.find(id)));
    }

    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo() {
        return responseObjectMap.responseObject(new UserResponse(service.getUserInfo()));
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody UpdateUserRequest request) {
        return responseObjectMap.responseObject(new UserResponse(service.update(request.UserRequest())));
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteUserById(@PathVariable Long id) {
        service.delete(id);
        return responseObjectMap.responseCodeWithMessage(200, "Delete successfully");
    }

    @PostMapping("/add-role")
    public Map<String, Object> assignRole(@RequestBody UserRoleRequest request) {
        return responseObjectMap.responseObject(new UserResponse(service.assignRole(request)));
    }
}
