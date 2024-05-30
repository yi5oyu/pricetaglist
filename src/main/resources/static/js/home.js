let swiper = new Swiper('.swiper', {
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
    slidesPerView : 2,
    spaceBetween : 5,
    breakpoints: {
        688: {
            slidesPerView: 3,
            spaceBetween: 5,
        },
        1024: {
            slidesPerView: 4,
            spaceBetween: 5,
        },
        1200: {
            slidesPerView: 5,
            spaceBetween: 5,
        },
        1400: {
            slidesPerView: 6,
            spaceBetween: 5,
        },
    },
})

function chart(num, dates){
    let query = '.chart-'+num
    let ctx = $(query)

    let labels = dates.map(date => {
        let parts = date.priceDate.split('-')
        return parts[1] +"/"+ parts[2]
    })
    let data = dates.map(date => date.dailyPrice);

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
                pointRadius: 0,
                borderWidth: 1
            }]
        },
        options: {
            plugins: {
                tooltip: {
                    mode: 'index',
                    intersect: false,
                    callbacks: {
                      label: function(context) {
                        let label = context.dataset.label || '';
                        if (label) {
                          label += ': ';
                        }
                        if (context.parsed.y !== null) {
                          label += context.parsed.y;
                        }
                        return label;
                      }
                    }
                },
                legend: {
                    display: false,
                    labels: {
                        font: {
                            size: 9
                        }
                    }
                }
            },
            scales: {
                y: {
                    display: false,
                    ticks: {
                        stepSize: 2000,
                        font: {
                            size: 10
                        }
                    },
                    grid: {
//                        display: false
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