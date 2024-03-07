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
const updatePriceRangeValues = (min, max) => {
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
const applyFilterConfig = (conf) => {
    updateFilterItemsList(conf)

    if (!conf.createRequest) {
        return;
    }
    const createItemModalSetting = {
        modalId: conf.modalForm.id,
        submit: {
            btnId: conf.modalForm.submitBtnId,
            action: () => {
                const body = {};
                for (let inputsKey in conf.modalForm.inputs) {
                    body[conf.modalForm.inputs[inputsKey].objectField] = $('#' + inputsKey).val().trim()
                }
                ajaxPOST(conf.baseUrl, body, (data) => {
                        showMessage(conf.createRequest.successMessage, 1500);
                        updateFilterItemsList(conf)
                    }
                )
            }
        },
        updateFields: {}
    }

    createItemModalSetting.updateFields[conf.modalForm.modalHeaderId] = {
        value: conf.createRequest.modalHeader
    }
    for (let inputsKey in conf.modalForm.inputs) {
        createItemModalSetting.updateFields[inputsKey] = {}
        createItemModalSetting.updateFields[inputsKey].value = ''
        createItemModalSetting.updateFields[inputsKey].placeholder = conf.modalForm.inputs[inputsKey].placeholder;
    }

    $('#' + conf.createBtnId).click(() => openModal(createItemModalSetting))
}

const filterItemsConfig = { //создаем характеристики элементов слева
    categories: {
        queryName: 'category',
        createBtnId: 'createCategoryBtn',
        baseUrl: '/api/category',
        listHolderId: 'categories',
        modalForm: {
            id: 'filterItemModal',
            modalHeaderId: 'filterItemModalHeader',
            submitBtnId: 'filterItemModalSubmit',
            inputs: {
                //id инпута и филд обьекта, который мы будем отправлять на бек
                filterItemModalCategoryName: {
                    placeholder: "Название категории",
                    objectField: 'name'
                }
            }
        },
        createRequest: {
            modalHeader: 'Создание категории',
            successMessage: 'Категория создана'
        },
        updateRequest: {
            modalHeader: 'Редактирование категории',
            successMessage: 'Категория обновлена'
        },
        deleteRequest: {
            successMessage: 'Категория удалена'
        },
        toViewItemModelMapper: (category) => {
            //TODO count
            return {id: category.id, name: category.name}
        }
    }, authors: {
        queryName: 'author',
        createBtnId: 'createAuthorBtn',
        baseUrl: '/api/author',
        listHolderId: 'authors',
        modalForm: {
            id: 'filterItemModal',
            modalHeaderId: 'filterItemModalHeader',
            submitBtnId: 'filterItemModalSubmit',
            inputs: {
                //id инпута и филд обьекта, который мы будем отправлять на бек
                filterItemModalCategoryName: {
                    placeholder: "Название бренда",
                    objectField: 'name'
                }
            }
        },
        createRequest: {
            modalHeader: 'Добавление бренда',
            successMessage: 'Бренд добавлен'
        },
        updateRequest: {
            modalHeader: 'Редактирование бренда',
            successMessage: 'Бренд обновлен'
        },
        deleteRequest: {
            successMessage: 'Бренд удален'
        },
        toViewItemModelMapper: (author) => {
            //TODO count
            return {id: author.id, name: author.name}
        }
    }, publishers: {
        queryName: 'publisher',
        createBtnId: 'createPublisherBtn',
        baseUrl: '/api/publisher',
        listHolderId: 'publishers',
        modalForm: {
            id: 'filterItemModal',
            modalHeaderId: 'filterItemModalHeader',
            submitBtnId: 'filterItemModalSubmit',
            inputs: {
                //id инпута и филд обьекта, который мы будем отправлять на бек
                filterItemModalCategoryName: {
                    placeholder: "Группа",
                    objectField: 'name'
                }
            }
        },
        createRequest: {
            modalHeader: 'Добавление группы',
            successMessage: 'Группа добавлена'
        },
        updateRequest: {
            modalHeader: 'Редактирование группы',
            successMessage: 'Группа обновлена'
        },
        deleteRequest: {
            successMessage: 'Группа удалена'
        },
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

const updateFilterItemsList = (conf) => { //берем данные категорий и вызываем функцию создания 
    ajaxGET(conf.baseUrl, (dataList) => {
        const htmlListElement = $('#' + conf.listHolderId);
        htmlListElement.empty();//очищаю список

        if (dataList.length === 0) { //если список данных пустой
            htmlListElement.append('<p style="text-align: center">Пусто<p>');
        }

        dataList.forEach(item => {
            if (!conf.modalForm) {
                htmlListElement.append(createFilterItem(conf, conf.toViewItemModelMapper(item)));
                return;
            }
            const modalSetting = {
                modalId: conf.modalForm.id,
                submit: {
                    btnId: conf.modalForm.submitBtnId,
                    action: () => {
                        const body = {};
                        for (let inputsKey in conf.modalForm.inputs) {
                            body[conf.modalForm.inputs[inputsKey].objectField] = $('#' + inputsKey).val().trim()
                        }
                        ajaxPUT(conf.baseUrl + '/' + item.id, body, (data) => {
                                showMessage(conf.updateRequest.successMessage, 1500);
                                updateFilterItemsList(conf)
                            }
                        )
                    }
                },
                updateFields: {}
            }
            modalSetting.updateFields[conf.modalForm.modalHeaderId] = {
                value: conf.updateRequest.modalHeader
            }
            for (let inputsKey in conf.modalForm.inputs) {
                modalSetting.updateFields[inputsKey] = {}
                modalSetting.updateFields[inputsKey].value = item[conf.modalForm.inputs[inputsKey].objectField]
                modalSetting.updateFields[inputsKey].placeholder = conf.modalForm.inputs[inputsKey].placeholder;
            }

            const editAction = () => openModal(modalSetting)

            htmlListElement.append(createFilterItem(conf, conf.toViewItemModelMapper(item), editAction));
        })
    })
}

const openModal = (settings) => { //всплывающее для редакта категорий окно
    const modalSubmitBtn = $('#' + settings.submit.btnId);
    modalSubmitBtn.unbind('click');//очищаю клик listeners, тк модалку переиспользую для создания и редактирования
    modalSubmitBtn.click(settings.submit.action)

    for (let updateFieldsKey in settings.updateFields) {
        const modalField = $('#' + updateFieldsKey);
        modalField.val(settings.updateFields[updateFieldsKey].value)//если филд инпут
        modalField.text(settings.updateFields[updateFieldsKey].value)//если филд это элемент с текстом
        modalField.attr('placeholder', settings.updateFields[updateFieldsKey].placeholder)
    }
    $('#' + settings.modalId).modal('show')
}


const searchParams = {//создаем элемент поиска
    searchByName: undefined,
    category: new Set(),
    author: new Set(),
    publisher: new Set(),
    year: new Set(),
    priceMin: undefined,
    priceMax: undefined
}

const resetSearch = () => { //очищаем элемент поиска
    searchParams.searchByName = undefined;
    searchParams.category = new Set();
    searchParams.author = new Set();
    searchParams.publisher = new Set();
    searchParams.year = new Set();
    searchParams.priceMin = undefined;
    searchParams.priceMax = undefined;
    showFilterResults() //выводим все книги
}

//вешаю на иконки удаления и редактирования нужные листнеры
const createFilterItem = (conf, viewItem, editAction = () => {
}) => {
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

    if (conf.deleteRequest) {
        let iRemove = $('<i class="fa fa-trash-o" style="color: #800000"></i>')

        iRemove.click(() => ajaxDELETE(conf.baseUrl + '/' + viewItem.id, () => {
            showMessage(conf.deleteRequest.successMessage, 1500);
            updateFilterItemsList(conf)
            searchParams[conf.queryName].delete(viewItem.id)
            showFilterResults()
        }))
        a.append(iRemove)
    }

    if (conf.updateRequest) {
        let iEdit = $('<i class="fa fa-pencil-square" style="color: darkblue"></i>')
        iEdit.click(editAction)
        a.append(iEdit)
    }

    a.append(viewItem.name)
    //span.text(viewItem.count)

    li.append(a)

    return li;
}

const showFilterResults = () => { //отправляем данные и получаем выборку,если в переменной ничего, то выводится все
    const urlParams = new URLSearchParams();
    console.log(searchParams)
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

const openBookModal = (book = null, submitAction = (book) => {
}) => {
    console.log('С сервера приходит это: ');
    console.log(book);

    if (!book) {
        $('#bookModalBookImage').prop('required', true)
        $('#bookModalLabel').text('Добавить товар')
    } else {
        $('#bookModalBookImage').prop('required', false)
        $('#bookModalLabel').text('Изменить товар: ' + book.name)
    }
    book = book ? book : {};

    ajaxGET('/api/meta', data => {
        $('#imagePreview').attr('src', book.image);
        $('#bookModalBookName').val(book.name);

        $('#bookModalAuthorsSelect').empty();
        data.authors.forEach(author => {
            if (book && book.authors && book.authors.filter(a => author.id === a.id).length===1) {
                $('#bookModalAuthorsSelect').append('<option value="' + author.id + '" selected>' + author.name + '</option>');
            } else {
                $('#bookModalAuthorsSelect').append('<option value="' + author.id + '">' + author.name + '</option>');
            }
        })
        //$('#bookModalAuthorsSelect').val(book.authors);
        $('#bookModalAuthorsSelect').selectpicker("refresh");

        $('#bookModalCategorySelect').empty();
        data.categories.forEach(category => {
            if (book && book.categories && book.categories.filter(c => category.id === c.id).length===1) {
                $('#bookModalCategorySelect').append('<option value="' + category.id + '" selected>' + category.name + '</option>');
            } else {
                $('#bookModalCategorySelect').append('<option value="' + category.id + '">' + category.name + '</option>');
            }
        })
        //$('#bookModalCategorySelect').val(book.categories);
        $('#bookModalCategorySelect').selectpicker("refresh");

        $('#bookModalPublisherSelect').empty();
        data.publishers.forEach(publisher => {

            $('#bookModalPublisherSelect').append('<option ' + (book.publisher && publisher.id === book.publisher.id ? 'selected' : '') + ' value="' + publisher.id + '">' + publisher.name + '</option>');
        })
        $('#bookModalPublisherSelect').selectpicker("refresh");

        $('#bookModalBookYear').val(book.publishYear);
        $('#bookModalBookPriceRub').val(book.price);

        $('#bookModal').modal('show')
    })

    $('#bookForm').submit((event) => {
        event.preventDefault();
        readImage($('#bookModalBookImage')).done(base64Data => {
            if (base64Data) {
                $('#imagePreview').attr('src', base64Data)
                book.image = base64Data;
            }
            book.name = $('#bookModalBookName').val();
            book.price = Number.parseFloat($('#bookModalBookPriceRub').val());
            book.authors = extractSelectedItems('bookModalAuthorsSelect'); //читаем выбранные элементы
            book.categories = extractSelectedItems('bookModalCategorySelect');
            book.publisher = extractSingleSelectedItem('bookModalPublisherSelect');//читаем выбранный элемент
            //book.publishYear = Number.parseInt($('#bookModalBookYear').val());
            book.publishYear = $('#bookModalBookYear').val();

            submitAction(book)
            console.log(book);
            $('#bookModal').modal('hide');
        });
    })
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
                // $('#searchForm').unbind( "submit" )
                const img = $('<img src="' + book.image + '" alt="">')
                const price = $('<h2>' + book.price + ' р</h2>')
                const name = $('<p>' + book.name + '</p>')

                const div1 = $('<div class="col-sm-4"></div>')
                const div2 = $('<div class="product-image-wrapper"></div>')
                const div3_1 = $('<div class="single-products">')

                const div3_2 = $('<div class="choose"></div>')
                const ul1 = $('<ul class="nav nav-pills nav-justified"><li><a><i class="fa fa-question"></i>Подробнее</a></li></ul>')
                ul1.click(() => {
                    window.location.href = '/details?book=' + book.id;
                })
                const ul3 = $('<ul class="nav nav-pills nav-justified"><li><a><i class="fa ' +
                    'fa-trash-o"></i>Удалить товар</a></li></ul>')
                ul3.click(() => {
                    ajaxDELETE('/api/book/' + book.id, () => {
                        showMessage('Товар удален', 1500)
                        reloadBooks()
                    });
                })
                const ul2 = $('<ul class="nav nav-pills nav-justified"><li><a><i class="fa ' +
                    'fa-wrench"></i>Изменить товар</a></li></ul>')
                ul2.click(() => {
                    openBookModal(book, (updatedBook) => {
                        ajaxPUT('/api/book/' + updatedBook.id, updatedBook, () => {
                            showMessage('Товар изменен', 1000, () => {
                                reloadBooks();
                            })
                        })
                    })
                })

                div3_2.append(ul1, ul2, ul3)
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

const extractSelectedItems = (selectId) => { //извлечение данных из селектов
    const selected = $('#' + selectId).val()
    return selected ? selected : [];
}

const extractSingleSelectedItem = (selectId) => {//извлечение данных из одного селекта
    const selected = $('#' + selectId).val()
    //возвращает пустую строку если ничего не выбрано, поэтому ставим null а не пустую строку
    return selected ? selected : null;
}


$(document).ready(() => {
    for (let filterConfigKey in filterItemsConfig) {
        const conf = filterItemsConfig[filterConfigKey];//вызываем filterItemsConfig для каждого элемента
        applyFilterConfig(conf)//создание категорий
    }

    $('#createBookBtn').click(() => {
        openBookModal(null, (book) => {
            ajaxPOST('/api/book', book, () => {
                showMessage("Товар создан", 1000, () => {
                    reloadBooks();
                })
            })
        });
    })

    reloadBooks();

    $('#headerBottomRow').append(
        '                 <div class="col-sm-3">\n' +
        '                    <div class="search_box pull-right">\n' +
        '                        <form id="search_form">\n' +
        '                            <input type="text" id="search_search" placeholder="Поиск" autocomplete="off">\n' +
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
    $('#search_search').val(searchParams.searchByName)// и парсим в форму имя при перезагрузке если оно есть, чтоб не терялось

    $("#bookModal").on('hide.bs.modal', () => $('#bookForm').unbind('submit'));
})