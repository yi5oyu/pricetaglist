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
    }
    card_hover()
})