package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.CategoryApiRequest;
import com.example.study.model.network.response.CategoryApiResponse;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.service.CategoryApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryApiController implements CrudInterface<CategoryApiRequest, CategoryApiResponse> {

    @Autowired
    CategoryApiLogicService categoryApiLogicService;

    @GetMapping("")
    public Header<List<CategoryApiResponse>> search
            (@PageableDefault(sort = "id",direction = Sort.Direction.ASC) Pageable pageable){
        log.info("{}",pageable);
        return categoryApiLogicService.search(pageable);
    }

    @Override
    @PostMapping("")
    public Header<CategoryApiResponse> create(@RequestBody Header<CategoryApiRequest> request) {
        log.info("Category, create : {}",request);
        return categoryApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<CategoryApiResponse> read(@PathVariable Long id) {
        log.info("Category, read : {}",id);
        return categoryApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<CategoryApiResponse> update(@RequestBody Header<CategoryApiRequest> request) {
        log.info("Category, update : {}",request);
        return categoryApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        log.info("Category, delete : {}",id);
        return categoryApiLogicService.delete(id);
    }
}
