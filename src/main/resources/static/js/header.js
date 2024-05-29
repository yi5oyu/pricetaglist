$(document).ready(function() {
    $('form').on('submit', function(e) {
        let input = $('#query').val().trim()
        if (input === '') {
            alert('검색어를 입력해주세요')
            e.preventDefault()
        }
    })
    $('.tag').hover(
        function() {
            $(this).addClass('hover-bg');
        },
        function() {
            $(this).removeClass('hover-bg');
        }
    )
})