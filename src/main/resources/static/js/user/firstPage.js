
////////////////////////////////ПОПЫТКА СДЕЛАТЬ ТО,ЧТО НА ГЛАВНОЙ

$(document).ready(() => { 

    // for (let filterConfigKey in filterItemsConfig) {
    //     const conf = filterItemsConfig[filterConfigKey];
    //     updateFilterItemsList(conf)
    // }

    const reloadBooks = ()=> {
        var bookCount = 0;
        ajaxGET('/api/item', books => {
            console.log(books);

            $('#books').empty();
            $('#books2').empty();
            for (var i = 0; i < 3; i++) {
                    book_id = books[i].id
                    // console.log(bookId)
                    const img = $('<img src="' + books[i].image + '" alt="">')
                    const price = $('<h2>' + books[i].price + ' р</h2>')
                    const name = $('<p>' + books[i].name + '</p>')
                    // console.log(books[i]);
                    // console.log(bookCount);
    
                    const div2 = $('<div class="col-sm-4"></div>')
                    const div3 = $('<div class="product-image-wrapper"></div>')
                    const div4 = $('<div class="single-products"></div>')
    
                    const ul1 = $('<a class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>В корзину</a>')
                    ul1.click(() => {
                        /*console.log('ID:', book_id);
                        ajaxPOSTWithoutResponse('/api/bucket/action', {bookId: book_id, action: 'APPEND_ITEM'},
                            () => showMessage("Книга добавлена в корзину", 1000))*/
                    })
    
                    const div5 = $('<div class="productinfo text-center"></div>')
                    div2.append(div3)
                    div3.append(div4)
                    div4.append(div5)
                    div5.append(img, price, name, ul1)
                    $('#books').append(div2)

            }
            for (var i = 3; i < 6; i++) {
                // console.log(books[i]);
                bookId = books[i].id
                const img = $('<img src="' + books[i].image + '" alt="">')
                const price = $('<h2>' + books[i].price + ' р</h2>')
                const name = $('<p>' + books[i].name + '</p>')
                // console.log(books[i]);
                // console.log(bookCount);

                const div2 = $('<div class="col-sm-4"></div>')
                const div3 = $('<div class="product-image-wrapper"></div>')
                const div4 = $('<div class="single-products"></div>')
                const ul1 = $('<a class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>В корзину</a>')
                ul1.click(() => {
                ajaxPOSTWithoutResponse('/api/order', {bookId: bookId, action: 'CREATE_BOOK'},
                    () => showMessage("Книга добавлена в корзину", 1000))
                })

                const div5 = $('<div class="productinfo text-center"></div>')
                div2.append(div3)
                div3.append(div4)
                div4.append(div5)
                div5.append(img, price, name, ul1)
                $('#books2').append(div2)

            }
        })
    }

    $(document).ready(() => {
        reloadBooks();
    })
})

