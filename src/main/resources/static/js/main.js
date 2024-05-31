$(document).ready(function() {
    function card_hover(){
        $('.card').hover(
            function() {
                $(this).addClass('card-hover');
                $(this).find('.card-front').css("display", "none");
                $(this).addClass('card-tag');
            },
            function() {
                $(this).removeClass('card-hover');
                $(this).removeClass('card-tag');
                $(this).find('.card-front').css("display", "block");
            }
        )

        $('.card').each(function() {
            let $card = $(this);
            let hammer = new Hammer(this);

            hammer.on('tap', function(event) {
                if ($card.hasClass('card-hover')) {
                    console.log("탭 1")
                    $card.removeClass('card-hover');
                    $card.removeClass('card-tag');
                    $card.find('.card-front').css("display", "block");
                } else {
                    console.log("탭 2")
                    $card.addClass('card-hover');
                    $card.find('.card-front').css("display", "none");
                    $card.addClass('card-tag');
                }
            });

            hammer.on('doubletap', function(event) {
                console.log("더블탭")
                window.location.href = $('.card-box-a').attr('href')
            });
        })
    }
    card_hover()
})