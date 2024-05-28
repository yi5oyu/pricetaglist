function chartPosition(num) {
    let item_query = '.item-'+num;
    let q = '.graph-'+num;
    let itemRect = document.querySelector(item_query).getBoundingClientRect();
    let center = itemRect.left + (itemRect.width / 2);
}

function chart(num, dates){
    let query = '.chart-'+num
    let ctx = $(query)

    let labels = dates.map(date => {
        let parts = date.priceDate.split('-')
        return parts[1] +"/"+ parts[2]
    })
    let data = dates.map(date => date.dailyPrice);

    chartPosition(num)

    let myLineChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: '가격',
                data: data,
                fill: false,
                borderColor: 'rgb(13 110 253)',
                tension: 0.1,
                pointRadius: 1,
                borderWidth: 2
            }]
        },
        options: {
            plugins: {
                tooltip: {

                },
                legend: {
                    //display: false,
                    labels: {
                        font: {
                            size: 9
                        }
                    }
                }
            },
            scales: {
                y: {
                    ticks: {
                        stepSize: 2000,
                        font: {
                            size: 10
                        }
                    },
                    grid: {
                        display: false
                    }
                },
                x: {
                    ticks: {
                        font: {
                            //style: 'Courier New',
                            size: 10
                        }
                    }
                }
            }
        }
    })
}

function items_hover(num, w) {
    let query = '.graph-'+num
    if(w == 0){
        $(query).css('display', 'block');
    } else
        $(query).css('display', 'none');
}

function ajax(query, selectedOption){
    $.ajax({
        url: '/search',
        type: 'GET',
        data: {
            query: query,
            options: selectedOption
        } ,
        success: function() {
            window.location.href = "search?query="+query+"&options="+selectedOption;
        }

    })
}

$(window).resize(function() {
    let len = $('.items').length
    console.log("resize "+ len)
    for(let i = 0 ; i<len ;i++)
        chartPosition(i)
})

$(document).ready(function() {

    $(document).on('mouseenter', '.items', function() {
        let index = $('.items').index(this)
        let w = 0
        items_hover(index, w)
    }).on('mouseleave', '.items', function() {
        let index = $('.items').index(this)
        let w = 1
        items_hover(index, w)
    })

    $('#search-btn').click(function() {
        let query = $('#query').val()
        let selectedOption = $('input[name="options"]:checked').val()
        if(query === ''){
<!--                $('#query').val() = ''-->
            alert("검색어를 입력해주세요")
        } else{
            ajax(query, selectedOption)
        }
    })

    let options = document.querySelectorAll('input[name="options"]')
    for(let i = 0 ; i<options.length ;i++){
        options[i].addEventListener('click', function() {
            let query = $('#search-result').text()
            let selectedOption = $('input[name="options"]:checked').val()
            ajax(query, selectedOption)
        })
    }

    $(window).scroll(function() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            loadMoreProducts()
        }
    })
})

let searchParams = new URLSearchParams(window.location.search)
let option = document.querySelectorAll('input[name = "options"]')
if(option[searchParams.get('options')] === undefined){
    option[0].checked = true
} else{
    option[searchParams.get('options')].checked = true
}

let pageNumber = 0;
let busy = false;

let array_num = []
let array_json = []

function loadMoreProducts() {
    $(".spinner-container").css("display", "flex");
    let num = 0;

    if (busy) return;
    busy = true;

    pageNumber++;
    $.ajax({
        url: '/searching',
        type: 'GET',
        data: {
            query: $('#search-result').text(),
            options: $('input[name="options"]:checked').val(),
            page: pageNumber,
            size: 36
        },
        dataType: 'json',
        success: function(data) {
            if (data.content.length > 0) {
                data.content.forEach(function(product) {
                    let hiddenClass = product.dailyPrice != 0 && product.itemQuantity != '품절' ? '' : 'hidden';
                    let items = $('.items').length
                    let productHtml = `
                        <div class="items mx-1 my-2 z-index-0 position-relative ${hiddenClass}">
                            <a href="${product.address}" class="card-box-a item-`+ (items+num) +`">
                                <div class="card" style="width: 13rem;">
                                    <img src="${product.itemImg}" class="card-img-top" style="height: 10rem;" alt="img">
                                    <div class="card-body">
                                        ${product.discountRate === 0 ?
                                            '<span class="badge text-bg-secondary">-</span>' :
                                            `<span class="badge text-bg-primary discount"> - ${product.discountRate}%</span>`}
                                        ${product.fixedPrice !== 0 ?
                                            `<span class="badge text-decoration-line-through text-secondary">${product.fixedPrice.toLocaleString()}원</span>` : ''}
                                        ${product.deliveryType === '로켓배송' ?
                                            `<span class="badge text-info">${product.deliveryType}</span>` : ''}
                                            <div>
                                        <h6 class="card-title text-danger my-2 fw-bold">${product.dailyPrice.toLocaleString()} 원</h6>
                                        ${product.itemQuantity !== '' ?
                                            '<span class="badge text-bg-danger">품절 임박</span>' : ''}
                                            </div>
                                        <p class="text-dark text-truncate word-box fs-7" title="${product.pname}">${product.pname}</p>
                                        <p class="text-secondary text-truncate info word-box fs-7" title="${product.detailInfo}">${product.detailInfo}</p>
                                    </div>
                                </div>
                            </a>
                            <div class="position-absolute p-1 graph graph-`+ (items+num) +`">
                                <canvas class="chart-`+ (items+num) +`"></canvas>
                            </div>

                        </div>`
                        $("#product-body").append(productHtml);
                        array_num.push((items+num))
                        array_json.push(product.productInfoByDates)
                        $(".spinner-container").css("display", "none");
                    })
                busy = false
            }

            for(let i = 0 ; i<array_num.length ;i++)
                chart(array_num[i], array_json[i])
            array_num = []
            array_json = []
        },
        error: function(xhr, status, error) {
            console.log('Error fetching data:', error);
            busy = false;
        }
    })
}

spin()

function spin(){
    const opts = {
        lines: 12, // 선 개수
        length: 80, // 각 선의 길이
        width: 9, // 선 굵기
        radius: 30, // 안쪽 원의 반지름
        scale: 0.4, // 스피너의 전체 크기
        corners: 1, // 코너 라운드(0..1)
        speed: 1.2, // Rounds per second
        rotate: 37, // 회전 각도
        animation: 'spinner-line-fade-quick', // 라인의 CSS 애니메이션 이름
        direction: 1, // 1: 시계 방향, -1: 시계 반대 방향
        color: '#000000', // CSS 색상
        fadeColor: 'transparent', // 페이드 색상
        top: '50%', // 상단 위치
        left: '50%', // 좌측 위치
        shadow: '0 0 1px transparent', // 그림자
        zIndex: 2000000000, // z-index
        className: 'spinner', // 스피너에 할당할 CSS 클래스
        position: 'absolute', // 위치 설정
    };

    const target = document.getElementById('spinner');
    const spinner = new Spinner(opts).spin(target);
}