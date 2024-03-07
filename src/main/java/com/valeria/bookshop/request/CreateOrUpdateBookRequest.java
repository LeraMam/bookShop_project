package com.valeria.bookshop.request;

import java.util.List;

public record CreateOrUpdateBookRequest(String name, String image, double price,
                                        String publishYear, List<Long> authors,
                                        Long publisher, List<Long> categories) {
}
