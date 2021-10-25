$(document).ready(function() {
	var userID = document.getElementById("userID").getAttribute("value");
	populatePosts(userID);
	
	$("#submitPost").submit(function(event) {

		event.preventDefault();

		var submittedContent = $("#submittedContent").val();
		alert("SUBMITTED COMMENT:"+submittedContent);
		console.log("SUBMITTED COMMENT:"+submittedContent);
		$.ajax({
			type: "POST",
			url: "postpost",
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			data: {
				postID: "",
				submittedContent: submittedContent,
			},
			success: function(response) {
				$('#PostsSection').html(response);
			}
		})
	});

	$("#deletePost").on("click", function() {
		var postID = document.getElementById("postID").value;
		$.ajax({
			type: "POST",
			url: "deletepost",
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			data: {
				postID: postID,
			},
			success: function(response) {
				$('#PostsSection').html(response);;
			}
		})
	});
})


function populatePosts(userID) {
	$.ajax({
		type: "GET",
		url: "getposts/"+userID,
		success: function(response) {
			$('#PostsSection').html(response);
		}
	})
}