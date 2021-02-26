package com.example.study.controller.api;


import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.service.ItemApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@Slf4j
public class ItemApiController implements CrudInterface<ItemApiRequest, ItemApiResponse> {

    @Autowired
    private ItemApiLogicService itemApiLogicService;

    @GetMapping("")
    public Header<List<ItemApiResponse>> search
            (@PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        log.info("{}",pageable);
        return itemApiLogicService.search(pageable);
    }

    @Override
    @PostMapping("")  // /api/item
    public Header<ItemApiResponse> create(@RequestBody Header<ItemApiRequest> request) {
        log.info("Item, create : {}",request);
        return itemApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") // /api/item/1 ~ 100...
    public Header<ItemApiResponse> read(@PathVariable Long id) {
        log.info("Item, read : {}",id);
        return itemApiLogicService.read(id);
    }

    @Override
    @PutMapping("") // /api/item
    public Header<ItemApiResponse> update(@RequestBody Header<ItemApiRequest> request) {
        log.info("Item, update : {}",request);
        return itemApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}") // /api/item/1 ~ 100...
    public Header delete(@PathVariable Long id) {
        log.info("Item, delete : {}",id);
        return itemApiLogicService.delete(id);
    }
}
