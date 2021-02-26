package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderDetailApiRequest;
import com.example.study.model.network.response.OrderDetailApiResponse;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.service.OrderDetailApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderDetail")
@Slf4j
public class OrderDetailApiController implements CrudInterface<OrderDetailApiRequest, OrderDetailApiResponse> {

    @Autowired
    OrderDetailApiLogicService orderDetailApiLogicService;

    @GetMapping("")
    public Header<List<OrderDetailApiResponse>> search
            (@PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        log.info("{}",pageable);
        return orderDetailApiLogicService.search(pageable);
    }

    @Override
    @PostMapping("")
    public Header<OrderDetailApiResponse> create(@RequestBody Header<OrderDetailApiRequest> request) {
        log.info("OrderDetail, create : {}",request);
        return orderDetailApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<OrderDetailApiResponse> read(@PathVariable Long id) {
        log.info("OrderDetail, read : {}",id);
        return orderDetailApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<OrderDetailApiResponse> update(@RequestBody Header<OrderDetailApiRequest> request) {
        log.info("OrderDetail, update : {}",request);
        return orderDetailApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        log.info("OrderDetail, delete : {}",id);
        return orderDetailApiLogicService.delete(id);
    }
}
