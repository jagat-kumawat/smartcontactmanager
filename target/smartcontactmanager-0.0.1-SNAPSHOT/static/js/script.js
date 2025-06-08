console.log("this is script file");

function toggleSidebar() {

    const sidebar = document.getElementsByClassName("sidebar")[0];
    const content = document.getElementsByClassName("content")[0];

    if (window.getComputedStyle(sidebar).display === "none") {
        sidebar.style.display = "block";
        content.style.marginLeft = "20%";
    }
    else {
        sidebar.style.display = "none";
        content.style.marginLeft = "0%";
    }
};

const search = () => {
    let query = document.getElementById("search-input").value;

    if (query === "") {
        document.querySelector(".search-result").style.display = "none";
    } else {
        console.log(query);

        // Sending request to server
        let url = `http://localhost:9090/search/${query}`;

        fetch(url)
            .then((response) => response.json())
            .then((data) => {
                console.log(data);

                let text = `<div class='list-group'>`;

                data.forEach((contact) => {
                    text += `<a href='/user/${contact.cid}/contacts' class='list-group-item list-group-action'>${contact.name}</a>`;
                });

                text += `</div>`;

                // Update the innerHTML with the new content
                document.querySelector(".search-result").innerHTML = text;
                document.querySelector(".search-result").style.display = "block";
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }
}

// first request to server to create order
const paymentstart = () => {

    console.log("payment started");
    let amount = $("#payment_field").val();
    console.log(amount); 
    if (amount == "" || amount == null) {

        alert("amount is required !!");
        return;

    }
    // code.....
    // we will use ajax to send request to server to create order --jquery
  $.ajax(
		{
          url:"/user/create_order",
        data:JSON.stringify({amount:amount,info:"order_request"}),
        contentType: 'application/json',
        type: "POST",
        dataType: "json",
        success:function(response) {

            // invoked where success
                console.log(response);
        },
        error:function(error) {

            // invoked when error
                console.log(error);
                alert("something went wrong !!");
        },
    }
    );
};