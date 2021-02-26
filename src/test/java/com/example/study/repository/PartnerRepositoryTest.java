package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Partner;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class PartnerRepositoryTest extends StudyApplicationTests {

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    public void create(){
        String name ="Partner01";
        String status="Registered";
        String address="수원시 장안구";
        String callCenter="070-1111-1111";
        String partnerNumber="010-0000-5555";
        String businessNumber="1234567890123";
        String ceoName="신동준";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt =LocalDateTime.now();
        String createdBy ="AdminServer";
        Long categoryId =1l;

        Partner partner = new Partner();
        partner.setName(name);
        partner.setStatus(status);
        partner.setAddress(address);
        partner.setCallCenter(callCenter);
        partner.setPartnerNumber(partnerNumber);
        partner.setBusinessNumber(businessNumber);
        partner.setCeoName(ceoName);
        partner.setRegisteredAt(registeredAt);
        partner.setCreatedAt(createdAt);
        partner.setCreatedBy(createdBy);
        //partner.setCategoryId(categoryId);

        Partner newPartner = partnerRepository.save(partner);
        Assertions.assertNotNull(partner);
        Assertions.assertEquals(partner.getName(),name);

    }

    @Test
    public void read(){

    }
}
