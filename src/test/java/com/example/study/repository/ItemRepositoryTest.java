package com.example.study.repository;


import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create(){
        String status="Unregistered";
        String name="삼성노트북";
        String title="삼성 노트북 A100";
        String content="2021년형 노트북입니다.";
        BigDecimal price= BigDecimal.valueOf(900000);
        String brandName="삼성";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy="AdminServer";
        Long partnerId = 1l;

        Item item = new Item();
        item.setStatus(status);
        item.setName(name);
        item.setTitle(title);
        item.setContent(content);
        item.setPrice(price);
        item.setBrandName(brandName);
        item.setRegisteredAt(registeredAt);
        item.setCreatedAt(createdAt);
        item.setCreatedBy(createdBy);
        //item.setPartnerId(partnerId);

        Item newItem = itemRepository.save(item);
        Assertions.assertNotNull(newItem);
    }
    @Test
    public void read(){
        Long id = 1L;

        Optional<Item> item = itemRepository.findById(id);
        Assertions.assertTrue(item.isPresent());
    }
}
