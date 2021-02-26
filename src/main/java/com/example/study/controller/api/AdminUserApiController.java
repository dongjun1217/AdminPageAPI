package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.AdminUserApiRequest;
import com.example.study.model.network.response.AdminUserApiResponse;
import com.example.study.service.AdminUserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adminUser")
@Slf4j
public class AdminUserApiController implements CrudInterface<AdminUserApiRequest, AdminUserApiResponse> {

    @Autowired
    AdminUserApiLogicService adminUserApiLogicService;

    @Override
    @PostMapping("")
    public Header<AdminUserApiResponse> create(@RequestBody Header<AdminUserApiRequest> request) {
        log.info("AdminUser, create : {}",request);
        return adminUserApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<AdminUserApiResponse> read(@PathVariable Long id) {
        log.info("AdminUser, read : {}",id);
        return adminUserApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<AdminUserApiResponse> update(@RequestBody Header<AdminUserApiRequest> request) {
        log.info("AdminUser, update : {}",request);
        return adminUserApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        log.info("AdminUser, delete : {}",id);
        return adminUserApiLogicService.delete(id);
    }
}
