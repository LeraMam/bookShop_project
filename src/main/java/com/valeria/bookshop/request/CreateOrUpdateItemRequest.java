package com.valeria.bookshop.request;

import java.util.List;

public record CreateOrUpdateItemRequest(String name, String image, double price,
                                        String publishYear, List<Long> brands,
                                        Long group, List<Long> categories) {
}
