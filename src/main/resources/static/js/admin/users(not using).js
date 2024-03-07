/////////////////////////////////////////////////////////////////////////////////////////USERS

$(document).ready(() => {

    $('#createUserBtn').click(() => {
        $('#bookModal').modal('show')
    })

    $('#UserForm').submit((event) => {
        event.preventDefault();
        const body = {
            email: $('#UserLogin').val(),
            password: $('#UserPassword').val(),
            role: 'ROLE_USER'
        }
        // console.log(body)

        ajaxPOST('/api/users/registration', body, () => {
            showMessage("Пользователь добавлен", 1000, ()=> {
                reloadUsers();
            })
        })
    })

    const reloadUsers = ()=> {
        ajaxGET('/api/users', users => {
            console.log(users);

            $('#users').empty();

            users.forEach(user => {
                // const img = $('<img src="../images/users/user.jpg" alt="">')
                const img = ''
                const price = $('<h2>' + user.email + ' </h2>')
                const name = $('<p>' + user.role + '</p>')
                if (user.role === 'ROLE_USER'){
                    const div1 = $('<div class="col-sm-4"></div>')
                    const div2 = $('<div class="product-image-wrapper"></div>')
                    const div3_1 = $('<div class="single-products">')

                    const div3_2 = $('<div class="choose"></div>')
                const ul1 = $('<ul class="nav nav-pills nav-justified"><li><a><i class="fa ' +
                    'fa-trash-o"></i>Удалить пользователя</a></li></ul>')
                ul1.click(() => {
                    ajaxDELETE('/api/users/' + user.id, ()=> {
                        showMessage('Пользователь удален', 1500)
                        reloadUsers()
                    });
                })
                div3_2.append(ul1)

                const div4 = $('<div class="productinfo text-center"></div>')

                div1.append(div2)
                div2.append(div3_1)
                div2.append(div3_2)
                div3_1.append(div4)
                div4.append(img, price, name)

                $('#users').append(div1)
                }
            })
        })
    }

    $(document).ready(() => {
        reloadUsers();
    })
})



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Books

// const extractSelectedItems = (selectId) => {
//     const selected = $('#' + selectId).val()
//     return selected ? selected : [];
// }

// const extractSingleSelectedItem = (selectId) => {
//     const selected = $('#' + selectId).val()
//     //возвращает пустую строку если ничего не выбрано, поэтому ставим null а не пустую строку
//     return selected ? selected : null;
// }



