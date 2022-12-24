function clearFilter(){
    window.location = moduleURL;
}

function showDeleteConfirmModal(link, entityName){
    let entityId = link.attr("entityId");

    $("#confirmText").text("Are you sure you want to delete this "+ entityName +
    " with ID "+ entityId + "?")

    $("#yesButton").attr("href", link.attr("href"));

    $("#confirmModal").modal();


}