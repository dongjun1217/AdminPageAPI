package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Category;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.CategoryApiRequest;
import com.example.study.model.network.response.CategoryApiResponse;
import com.example.study.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryApiLogicService implements CrudInterface<CategoryApiRequest, CategoryApiResponse> {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {
        CategoryApiRequest body = request.getData();
        Category category = Category.builder()
                .type(body.getType())
                .title(body.getTitle())
                .build();
        Category newCategory = categoryRepository.save(category);

        return Header.OK(response(newCategory));
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return categoryRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {
        CategoryApiRequest body = request.getData();
        return categoryRepository.findById(body.getId())
                .map(updateCategory->{
                    updateCategory
                        .setType(body.getType())
                        .setTitle(body.getTitle());
                    return updateCategory;
        })
                .map(newUpdateCategory -> categoryRepository.save(newUpdateCategory))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()-> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Category> optional = categoryRepository.findById(id);

        return optional.map(category -> {
            categoryRepository.delete(category);

            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private CategoryApiResponse response(Category category){

        CategoryApiResponse body = CategoryApiResponse.builder()
                .id(category.getId())
                .type(category.getType())
                .title(category.getTitle())
                .build();
        return body;

    }

    public Header<List<CategoryApiResponse>> search(Pageable pageable){
        Page<Category> categories = categoryRepository.findAll(pageable);

        List<CategoryApiResponse> categoryApiResponseList = categories.stream()
                .map(this::response)
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(categories.getTotalPages())
                .totalElements(categories.getTotalElements())
                .currentPage(categories.getNumber())
                .currentElements(categories.getNumberOfElements())
                .build();

        return Header.OK(categoryApiResponseList,pagination);
    }
}
