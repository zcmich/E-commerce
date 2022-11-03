$(document).ready(function () {

    $("#logoutLink").on("click", function (e) {
        e.preventDefault();
        document.logoutForm.submit();
    });

    customizeDropdownMenu();


});


function customizeDropdownMenu(){
    $(".navbar .dropdown").hover(
        function (){
            $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
        }
        ,
        function (){
            $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp();
        }
    );

    $(".dropdown> a").click(function (){   //$("li > a").click(function (){
        location.href = this.href;
    });
}

function checkPasswordMatch(confirmPassword) {
    if (confirmPassword.value !== $('#password').val()) {
        confirmPassword.setCustomValidity('Cliff says Passwords do not match!');
    } else {
        confirmPassword.setCustomValidity('');

    }
}