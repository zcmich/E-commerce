$(document).ready(function () {

    $("#logoutLink").on("click", function (e) {
        e.preventDefault();
        document.logoutForm.submit();
    });

    customizeDropdownMenu();


});


function customizeDropdownMenu(){
    $(".dropdown> a").click(function (){   //$("li > a").click(function (){
        location.href = this.href;
    });
}