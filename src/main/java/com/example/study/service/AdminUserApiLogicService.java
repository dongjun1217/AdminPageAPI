package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.AdminUser;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.AdminUserApiRequest;
import com.example.study.model.network.response.AdminUserApiResponse;
import com.example.study.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdminUserApiLogicService implements CrudInterface<AdminUserApiRequest, AdminUserApiResponse> {

    @Autowired
    AdminUserRepository adminUserRepository;


    @Override
    public Header<AdminUserApiResponse> create(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest body = request.getData();

        AdminUser adminUser = AdminUser.builder()
                .account(body.getAccount())
                .password(body.getPassword())
                .status(body.getStatus())
                .role(body.getRole())
                .registeredAt(LocalDateTime.now())
                .build();
        AdminUser newAdminUser = adminUserRepository.save(adminUser);

        return response(newAdminUser);
    }

    @Override
    public Header<AdminUserApiResponse> read(Long id) {
        return adminUserRepository.findById(id)
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest body = request.getData();

        return adminUserRepository.findById(body.getId()).map(updateAdminUser -> {
            updateAdminUser
                    .setAccount(body.getAccount())
                    .setPassword(body.getPassword())
                    .setStatus(body.getStatus())
                    .setRole(body.getRole())
                    .setLastLoginAt(body.getLastLoginAt())
                    .setPasswordUpdatedAt(body.getPasswordUpdatedAt())
                    .setLoginFailCount(body.getLoginFailCount())
                    .setRegisteredAt(body.getRegisteredAt())
                    .setUnregisteredAt(body.getUnregisteredAt())
                    ;
            return updateAdminUser;
        })
                .map(newUpdateAdminUser -> adminUserRepository.save(newUpdateAdminUser))
                .map(this::response)
                .orElseGet(()-> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<AdminUser> optional = adminUserRepository.findById(id);

        return optional.map(adminUser -> {
            adminUserRepository.delete(adminUser);

            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<AdminUserApiResponse> response(AdminUser adminUser){
        AdminUserApiResponse adminUserApiResponse = AdminUserApiResponse.builder()
                .id(adminUser.getId())
                .account(adminUser.getAccount())
                .password(adminUser.getPassword())
                .status(adminUser.getStatus())
                .role(adminUser.getRole())
                .lastLoginAt(adminUser.getLastLoginAt())
                .passwordUpdatedAt(adminUser.getPasswordUpdatedAt())
                .registeredAt(adminUser.getRegisteredAt())
                .unregisteredAt(adminUser.getUnregisteredAt())
                .build();

        return Header.OK(adminUserApiResponse);
    }
}
