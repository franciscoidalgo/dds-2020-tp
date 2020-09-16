document.getElementById("logoutButton").addEventListener("click", logout);

function logout(){
    $.ajax({
        type: "POST",
        url: "/logout",
        success: function(){
            location.reload(true);
        }
    })
}

