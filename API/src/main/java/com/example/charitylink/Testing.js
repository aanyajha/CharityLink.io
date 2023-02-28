const username = "vjasti";
const password = "passwordtest";

const url = `http://localhost:8080/api/user/login/username?username=${username}&password=${password}`;

$.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function(data) {
        console.log(data); // handle the response data here
    },
    error: function(xhr, status, error) {
        console.error(error); // handle errors here
    }
});