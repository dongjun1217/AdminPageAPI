package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.PartnerApiResponse;
import com.example.study.service.PartnerApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partner")
@Slf4j
public class PartnerApiController implements CrudInterface<PartnerApiRequest, PartnerApiResponse> {

    @Autowired
    private PartnerApiLogicService partnerApiLogicService;

    @GetMapping("")
    public Header<List<PartnerApiResponse>> search
            (@PageableDefault(sort = "id",direction = Sort.Direction.DESC)Pageable pageable){
        log.info("{}",pageable);
        return partnerApiLogicService.search(pageable);
    }

    @Override
    @PostMapping("")
    public Header<PartnerApiResponse> create(@RequestBody Header<PartnerApiRequest> request) {
        log.info("Partner, create : {}",request);
        return partnerApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<PartnerApiResponse> read(@PathVariable Long id) {
        log.info("Partner, read : {}",id);
        return partnerApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<PartnerApiResponse> update(@RequestBody Header<PartnerApiRequest> request) {
        log.info("Partner, update : {}",request);
        return partnerApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        log.info("Partner, delete : {}",id);
        return partnerApiLogicService.delete(id);
    }
}
