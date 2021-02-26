package com.example.study.repository;


import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        String account ="TEST03";
        String password ="TEST03";
        //String status = "Registered";
        String email = "Test03@naver.com";
        String phoneNumber = "010-0333-0000";
        LocalDateTime registeredAt = LocalDateTime.now();

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
       // user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);

        User newUser = userRepository.save(user);

        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read(){
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-0000-0000");
        user.getOrderGroupList().stream().forEach(orderGroup -> {
            System.out.println("------------주문 묶음--------------");
            System.out.println(orderGroup.getRevName());
            System.out.println(orderGroup.getRevAddress());
            System.out.println(orderGroup.getTotalPrice());
            System.out.println(orderGroup.getTotalQuantity());
            System.out.println("------------주문 상세--------------");
            orderGroup.getOrderDetailList().forEach(orderDetail -> {
                System.out.println(orderDetail.getItem().getPartner().getName());
                System.out.println(orderDetail.getItem().getPartner().getCategory().getTitle());
                System.out.println(orderDetail.getItem().getName());
                System.out.println(orderDetail.getItem().getPartner().getCallCenter());
                System.out.println(orderDetail.getStatus());
                System.out.println(orderDetail.getArrivalDate());
            });
        });
        Assertions.assertNotNull(user);
    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser ->{
            selectUser.setAccount("PPPP");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update Method()");
        });
    }

    @Test
    //@Transactional 실질적인 쿼리문을 동작시키지 않아, 테스트 할때 사용한다.
    public void delete(){
        Optional<User> user = userRepository.findById(3L);

        Assertions.assertTrue(user.isPresent()); // 반드시 값이 있어야 한다.

        user.ifPresent(selectUser ->{
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(3L);
        Assertions.assertFalse(deleteUser.isPresent()); // 반드시 값이 없어야 한다.
    }


}
