package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.OrderDetailApiRequest;
import com.example.study.model.network.response.OrderDetailApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderDetailRepository;
import com.example.study.repository.OrderGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailApiLogicService implements CrudInterface<OrderDetailApiRequest, OrderDetailApiResponse> {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest body = request.getData();

        OrderDetail orderDetail = OrderDetail.builder()
                .status(body.getStatus())
                .arrivalDate(LocalDateTime.now().plusDays(2))
                .quantity(body.getQuantity())
                .totalPrice(body.getTotalPrice())
                .orderGroup(orderGroupRepository.getOne(2l))
                .item(itemRepository.getOne(2l))
                .build();
        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
        return Header.OK(response(newOrderDetail));
    }

    @Override
    public Header<OrderDetailApiResponse> read(Long id) {
        Optional<OrderDetail> optional = orderDetailRepository.findById(id);
        System.out.println(optional);
        return orderDetailRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest body = request.getData();
        return orderDetailRepository.findById(body.getId()).map(update -> {
            update
                    .setStatus(body.getStatus())
                    .setArrivalDate(body.getArrivalDate())
                    .setQuantity(body.getQuantity())
                    .setTotalPrice(body.getTotalPrice());
            return update;
        })
                .map(newOrderDetail -> orderDetailRepository.save(newOrderDetail))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<OrderDetail> optional = orderDetailRepository.findById(id);
        return optional.map(orderDetail -> {
            orderDetailRepository.delete(orderDetail);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private OrderDetailApiResponse response(OrderDetail orderDetail){
        OrderDetailApiResponse orderDetailApiResponse = OrderDetailApiResponse.builder()
                .id(orderDetail.getId())
                .status(orderDetail.getStatus())
                .arrivalDate(orderDetail.getArrivalDate())
                .quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice())
                .totalPrice(orderDetail.getTotalPrice())
                .orderGroupId(orderDetail.getOrderGroup().getId())
                .itemId(orderDetail.getItem().getId())
                .build();

        return orderDetailApiResponse;
    }

    public Header<List<OrderDetailApiResponse>> search(Pageable pageable){
        Page<OrderDetail> orderDetails = orderDetailRepository.findAll(pageable);

        List<OrderDetailApiResponse> orderDetailApiResponseList = orderDetails.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(orderDetails.getTotalPages())
                .totalElements(orderDetails.getTotalElements())
                .currentPage(orderDetails.getNumber())
                .currentElements(orderDetails.getNumberOfElements())
                .build();
        return Header.OK(orderDetailApiResponseList,pagination);
    }
}
