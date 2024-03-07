///////////////////////////////////////////////////////////////////////////PRICE RANGE
const getPriceRangeValues = () => {
    let slide1 = parseFloat($('#rangeInput1').val());
    let slide2 = parseFloat($('#rangeInput2').val());
    if (slide1 > slide2) {
        let tmp = slide2;
        slide2 = slide1;
        slide1 = tmp;
    }
    return [slide1, slide2]
}
const updatePriceRangeValues = (min, max) => { //подпись
    $('.rangeValues').text(min + " BYN – " + max + " BYN");
}

const updatePriceRange = () => {
    ajaxGET('/api/meta/price', (prices) => {
        // console.log(prices)

        $('#rangeInput1').attr('max', prices.max)
        $('#rangeInput1').attr('min', prices.min)
        $('#rangeInput1').attr('value', searchParams.priceMax ? searchParams.priceMax : prices.max)

        $('#rangeInput2').attr('max', prices.max)
        $('#rangeInput2').attr('min', prices.min)
        $('#rangeInput2').attr('value', searchParams.priceMin ? searchParams.priceMin : prices.min)

        const values = getPriceRangeValues();
        updatePriceRangeValues(values[0], values[1]);
    })
}

$(document).ready(() => {
    $('#rangeInput1').on('input', () => {
        const values = getPriceRangeValues();
        updatePriceRangeValues(values[0], values[1]);
        searchParams.priceMin = values[0];
        searchParams.priceMax = values[1];
    })
    $('#rangeInput2').on('input', () => {
        const values = getPriceRangeValues();
        updatePriceRangeValues(values[0], values[1]);
        searchParams.priceMin = values[0];
        searchParams.priceMax = values[1];
    })
    //$('#rangeInput1').trigger('input')
    //$('#rangeInput2').trigger('input')
})

////////////////////////////////////////////////////////////////////////////// FILTER ITEMS

const filterItemsConfig = { //создаем характеристики элементов слева
    categories: {
        queryName: 'category',
        baseUrl: '/api/category',
        listHolderId: 'categories',
        toViewItemModelMapper: (category) => {
            return {id: category.id, name: category.name}
        }
    }, authors: {
        queryName: 'author',
        baseUrl: '/api/author',
        listHolderId: 'authors',
        toViewItemModelMapper: (author) => {
            return {id: author.id, name: author.name}
        }
    }, publishers: {
        queryName: 'publisher',
        baseUrl: '/api/publisher',
        listHolderId: 'publishers',
        toViewItemModelMapper: (publisher) => {
            return {id: publisher.id, name: publisher.name}
        }
    }, years: {
        queryName: 'year',
        baseUrl: '/api/meta/years',
        listHolderId: 'years',
        toViewItemModelMapper: (year) => {
            year = '' + year;
            return {id: year, name: year}
        }
    },
}


const updateFilterItemsList = (conf) => { //берем данные категорий и создаем  каждый элемент
    ajaxGET(conf.baseUrl, (dataList) => {
        const htmlListElement = $('#' + conf.listHolderId);
        htmlListElement.empty();//очищаю список
        dataList.forEach(item => {
            htmlListElement.append(createFilterItem(conf, conf.toViewItemModelMapper(item)));
        })
    })
}

const searchParams = { //для фильтрации и поиска
    searchByName: undefined,
    category: new Set(),
    author: new Set(),
    publisher: new Set(),
    year: new Set(),
    priceMin: undefined,
    priceMax: undefined
}

const resetSearch = () => { //очистка поиска и фильтрации на фронте
    searchParams.searchByName = undefined;
    searchParams.category = new Set();
    searchParams.author = new Set();
    searchParams.publisher = new Set();
    searchParams.year = new Set();
    searchParams.priceMin = undefined;
    searchParams.priceMax = undefined;

    showFilterResults()
}

const createFilterItem = (conf, viewItem) => { //создаем кнопки
    const li = $('<li></li>')
    const a = $('<h5></h5>')
    const span = $('<span class="pull-right"></span>')
    const input = $('<input type="checkbox" id="scales" name="scales">')
    input.prop('checked', searchParams[conf.queryName].has(viewItem.id))

    input.click(() => {
        if (input.is(':checked')) {
            searchParams[conf.queryName].add(viewItem.id)
        } else {
            searchParams[conf.queryName].delete(viewItem.id)
        }
    })
    span.append(input)
    a.append(span)

    a.append(viewItem.name)

    li.append(a)

    return li;
}

const showFilterResults = () => {  //отправляем данные и получаем выборку,если в переменной ничего, то выводится все
    const urlParams = new URLSearchParams();

    if (searchParams.searchByName) {
        urlParams.append('search', searchParams.searchByName);
    }
    delete searchParams.searchByName;

    if (searchParams.priceMin) {
        urlParams.append('priceMin', searchParams.priceMin);
    }
    delete searchParams.priceMin;
    if (searchParams.priceMax) {
        urlParams.append('priceMax', searchParams.priceMax);
    }
    delete searchParams.priceMax;

    for (let searchParamsKey in searchParams) {
        searchParams[searchParamsKey].forEach(searchParam => {
            urlParams.append(searchParamsKey, searchParam);
        })
    }
    window.location.search = urlParams;
}

const fillSearchParams = () => { //заполняем данные для фильтрации
    const urlParams = new URLSearchParams(window.location.search);
    searchParams.searchByName = urlParams.get('search');
    searchParams.category = new Set(urlParams.getAll('category').map(v=>parseInt(v)))
    searchParams.author = new Set(urlParams.getAll('author').map(v=>parseInt(v)))
    searchParams.publisher = new Set(urlParams.getAll('publisher').map(v=>parseInt(v)))
    searchParams.year = new Set(urlParams.getAll('year'))
    searchParams.priceMin = parseFloat(urlParams.get('priceMin'))
    searchParams.priceMax = parseFloat(urlParams.get('priceMax'))

    searchParams.priceMin = isNaN(searchParams.priceMin) ? undefined : searchParams.priceMin;
    searchParams.priceMax = isNaN(searchParams.priceMax) ? undefined : searchParams.priceMax;

    //window.location.search = urlParams;
    console.log(searchParams)
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Books


function readImage(inputElement) {
    let deferred = $.Deferred();

    let files = inputElement.get(0).files;
    if (files && files[0]) {
        let fr = new FileReader();
        fr.onload = function (e) {
            deferred.resolve(e.target.result);
        };
        fr.readAsDataURL(files[0]);
    } else {
        deferred.resolve(undefined);
    }

    return deferred.promise();
}

const reloadBooks = () => {
    ajaxGET('/api/book?' + new URLSearchParams(window.location.search), books => {
        $('#books').empty();
        if (books.length == 0) {
            const message = $('<p id="message" class="text-center">Ничего не найдено</p>')
            const nothing_found = $('<img src="../../images/sad_cat.jpg">')
            $('#books').append(message)
            $('#books').append(nothing_found)
        }
        else{
            books.forEach(book => {
                const img = $('<img src="' + book.image + '" alt="">')
                const price = $('<h2>' + book.price + ' р</h2>')
                const name = $('<p>' + book.name + '</p>')

                const div1 = $('<div class="col-sm-4"></div>')
                const div2 = $('<div class="product-image-wrapper"></div>')

                const div3_1 = $('<div id="product-in-catalog" class="single-products">')
                const div3_2 = $('<div class="choose"></div>')
                const ul1 = $('<ul class="nav nav-pills nav-justified"><li><a><i class="fa fa-question"></i>Подробнее</a></li></ul>')
                ul1.click(() => {
                    window.location.href = '/details?book=' + book.id;
                })
                const ul2 = $('<ul class="nav nav-pills nav-justified"><li><a><i class="fa ' +
                    'fa-shopping-cart"></i>В корзину</a></li></ul>')
                ul2.click(() => {
                    console.log('ID:', book.id);
                    ajaxPOSTWithoutResponse('/api/bucket/action', {bookId: book.id, action: 'APPEND_BOOK'},
                        () => showMessage("Товар добавлен в корзину", 1000))
                })
                div3_2.append(ul1, ul2)

                const div4 = $('<div class="productinfo text-center"></div>')

                div1.append(div2)
                div2.append(div3_1)
                div2.append(div3_2)
                div3_1.append(div4)
                div4.append(img, price, name)

                $('#books').append(div1)
            })
        }
    })
    updateFilterItemsList(filterItemsConfig.years)
    updatePriceRange()
}


$(document).ready(() => {
    for (let filterConfigKey in filterItemsConfig) {
        const conf = filterItemsConfig[filterConfigKey]; //вызываем filterItemsConfig для каждого элемента
        updateFilterItemsList(conf)//создаем категории
    }

    reloadBooks();

    $('#headerBottomRow').append(
        '                 <div class="col-sm-3">\n' +
        '                    <div class="search_box pull-right">\n' +
        '                        <form id="search_form">\n' +
        '                            <input type="text" id="search_search" placeholder="Поиск">\n' +
        '                            <button id="" type="submit" class="btn success">.</button>\n' +
        '                        </form>\n' +
        '                    </div>\n' +
        '                </div>')


    $('#search_form').submit((event) => {
        event.preventDefault(); //отменить действие браузера по умолчанию
        searchParams.searchByName = $('#search_search').val()
        showFilterResults()//выполняем поиск по имени
    })
    fillSearchParams() //каждый раз заполняем при перезагрузке страницы
    $('#search_search').val(searchParams.searchByName) // и парсим в форму имя при перезагрузке если оно есть, чтоб не терялось
})