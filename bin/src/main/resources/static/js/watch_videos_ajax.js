function openForm() {
	  document.getElementById("myHeartFormVideo").style.display = "block";
	}

function closeForm() {
	  document.getElementById("myHeartFormVideo").style.display = "none";
	}
	
function openFormMusic() {
	  document.getElementById("myHeartFormMusic").style.display = "block";
	}

function closeFormMusic() {
	  document.getElementById("myHeartFormMusic").style.display = "none";
	}

//Close save to playlist heart button if click outside box
$(document).mouseup(function(e) 
{
    if ($("#myHeartFormVideo").has(e.target).length === 0 && !$("#myHeartFormVideo").is(e.target)) 
    {
        closeForm();
        closeFormMusic();
    }
});	

$(document).ready(function(){
	$("#savePlaylistButtonVideo").on("click", function() {
		var userID = document.getElementById("userID").value;
		var playlistID = document.getElementById("playlistID").value;
		var mediaID = document.getElementById("mediaID").value;
		$.ajax({
			type: "POST",
			url: "/video/addToPlaylist",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			data: {
				userID :userID,
				playlistID :playlistID,
				mediaID :mediaID
			},
			success: function (response) {
				$("#heartButton").removeClass('userPageHeartIcon').addClass('userPageHeartIconLiked');
				$('#heartButton').attr("onclick", "closeForm()");
			}
		}) 
	});
	
	$('#heartButton').on("click", function() {
		if($("#heartButton").hasClass("userPageHeartIconLiked")) {
			//If already liked, then send Ajax request to unlike it
			var userID = document.getElementById("userID").value;
			var mediaID = document.getElementById("mediaID").value;
			$.ajax({
				type: "POST",
				url: "/video/removeFromPlaylist",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				data: {
					userID :userID,
					mediaID :mediaID
				},
				success: function (response) {
					$("#heartButton").removeClass('userPageHeartIconLiked').addClass('userPageHeartIcon');
					$('#heartButton').attr("onclick", "openForm()");
				}
			})
		}
	});

	
	$("#savePlaylistButtonMusic").on("click", function() {
		var userIDmusic = document.getElementById("userID").value;
		var playlistIDmusic = document.getElementById("playlistID").value;
		var mediaIDmusic = document.getElementById("mediaID").value;
		$.ajax({
			type: "POST",
			url: "/music/addToPlaylist",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			data: {
				userIDmusic :userIDmusic,
				playlistIDmusic :playlistIDmusic,
				mediaIDmusic :mediaIDmusic
			},
			success: function (response) {
				$("#heartButtonMusic").removeClass('userPageHeartIcon').addClass('userPageHeartIconLiked');
				$('#heartButtonMusic').attr("onclick", "closeFormMusic()");
			}
		}) 
	});
	
	$('#heartButtonMusic').on("click", function() {
		if($("#heartButtonMusic").hasClass("userPageHeartIconLiked")) {
			//If already liked, then send Ajax request to unlike it
			var userIDmusic = document.getElementById("userID").value;
			var mediaIDmusic = document.getElementById("mediaID").value;
			$.ajax({
				type: "POST",
				url: "/music/removeFromPlaylist",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				data: {
					userIDmusic :userIDmusic,
					mediaIDmusic :mediaIDmusic
				},
				success: function (response) {
					$("#heartButtonMusic").removeClass('userPageHeartIconLiked').addClass('userPageHeartIcon');
					$('#heartButtonMusic').attr("onclick", "openFormMusic()");
				}
			})
		}
	});
	
	
	$("#zqButton").on("click", function() {
		if ($("#zqButton").hasClass("zqClass") == false) {
			var artistId = document.getElementById("artistId").value;
			$.ajax({
				type: "POST",
				url: "/video/subscribe",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				data: {
					artistId :artistId
				},
				success: function (response) {
					
					$(".sbutton").text("UNSUBSCRIBE");
					$("#zqButton").addClass('zqClass');
				}
				
			}) 
		}
		
	});
	
	$("#zqButton").on("click", function() {
		if ($("#zqButton").hasClass("zqClass")) {
			var artistId = document.getElementById("artistId").value;
			$.ajax({
				type: "POST",
				url: "/video/unsubscribe",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				data: {
					artistId :artistId
				},
				success: function (response) {
					$(".sbutton").text("SUBSCRIBE");
					$("#zqButton").removeClass('zqClass');
				}
			}) 
		}
		
	});
	
	$("#zqButtonMusic").on("click", function() {
		if ($("#zqButtonMusic").hasClass("zqClassMusic") == false) {
			var artistIdMusic = document.getElementById("artistId").value;
			$.ajax({
				type: "POST",
				url: "/music/subscribe",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				data: {
					artistIdMusic :artistIdMusic
				},
				success: function (response) {
					
					$(".sbutton").text("UNSUBSCRIBE");
					$("#zqButtonMusic").addClass('zqClassMusic');
				}
				
			}) 
		}
		
	});
	
	$("#zqButtonMusic").on("click", function() {
		if ($("#zqButtonMusic").hasClass("zqClassMusic")) {
			var artistIdMusic = document.getElementById("artistId").value;
			$.ajax({
				type: "POST",
				url: "/music/unsubscribe",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				data: {
					artistIdMusic :artistIdMusic
				},
				success: function (response) {
					$(".sbutton").text("SUBSCRIBE");
					$("#zqButtonMusic").removeClass('zqClassMusic');
				}
			}) 
		}
		
	});
	
	$("#userCommentsSubmitBtnVideo").on("click", function() {
		
		if($('#commentsTxtArea').val().trim() == "") {
			alert("Please enter your comments");
		} 
		
		if($('#commentsTxtArea').val().trim() != "") {
		var submittedComment = $("#commentsTxtArea").val().trim();
		var commentUserId = document.getElementById("commentUserId").value;
		var commentDisplayName = document.getElementById("commentDisplayName").value;
		var commentMediaId = document.getElementById("commentMediaId").value;
		var commentDateTime = document.getElementById("commentDateTime").value;
		var ajaxChecker = 543501872;
		var ajaxChecker2 = 32163231;
		$.ajax({
			type: "POST",
			url: "/video/submitComments",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			data: {
				submittedComment :submittedComment,
				commentUserId :commentUserId,
				commentDisplayName :commentDisplayName,
				commentMediaId :commentMediaId,
				commentDateTime :commentDateTime
			},
			success: function (response) {
				$('#userCommentsSection').load("http://localhost:8080/video/aftersubmitcomment/" + commentMediaId + "/" + ajaxChecker + "/" + ajaxChecker2);
			}
		})
		
	}
		
	});
	
	$("#userCommentsSubmitBtnMusic").on("click", function() {
		
		if($('#commentsTxtAreaMusic').val().trim() == "") {
			alert("Please enter your comments");
		} 
		
		if($('#commentsTxtAreaMusic').val().trim() != "") {
		var submittedCommentMusic = $("#commentsTxtAreaMusic").val().trim();
		var commentUserIdMusic = document.getElementById("commentUserIdMusic").value;
		var commentDisplayNameMusic = document.getElementById("commentDisplayNameMusic").value;
		var commentMediaIdMusic = document.getElementById("commentMediaIdMusic").value;
		var commentDateTimeMusic = document.getElementById("commentDateTimeMusic").value;
		var ajaxCheckerMusic = 723472837;
		var ajaxChecker2Music = 340982904;
		$.ajax({
			type: "POST",
			url: "/music/submitComments",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			data: {
				submittedCommentMusic :submittedCommentMusic,
				commentUserIdMusic :commentUserIdMusic,
				commentDisplayNameMusic :commentDisplayNameMusic,
				commentMediaIdMusic :commentMediaIdMusic,
				commentDateTimeMusic :commentDateTimeMusic
			},
			success: function (response) {
				$('#userCommentsSectionMusic').load("http://localhost:8080/music/aftersubmitcomment/" + commentMediaIdMusic + "/" + ajaxCheckerMusic + "/" + ajaxChecker2Music);
			}
		})
		
	}
		
	});
	
	$("span[id='deleteCommentBtnVideo']").each(function(i){
    	$(this).attr('id', $(this).attr('id') + i);
	});
	
	$("input[id='commentIDvideo']").each(function(i){
    	$(this).attr('id', $(this).attr('id') + i);
	});
	
	$(".deleteCommentBtnVideo").on("click", function() {
		var commentIDvideo = document.getElementById($(this).next('input').attr('id')).value;
		var commentMediaId = document.getElementById("commentMediaId").value;
		var ajaxChecker = 543501872;
		var ajaxChecker2 = 32163231;
		$.ajax({
			type: "POST",
			url: "/video/deleteComment",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			data: {
				commentIDvideo :commentIDvideo,
			},
			success: function (response) {
				$('#userCommentsSection').load("http://localhost:8080/video/aftersubmitcomment/" + commentMediaId + "/" + ajaxChecker + "/" + ajaxChecker2);
			}
		}) 
	});
	
	
	$("span[id='deleteCommentBtnMusic']").each(function(i){
    	$(this).attr('id', $(this).attr('id') + i);
	});
	
	$("input[id='commentIDmusic']").each(function(i){
    	$(this).attr('id', $(this).attr('id') + i);
	});
	
	$(".deleteCommentBtnMusic").on("click", function() {
		var commentIDmusic = document.getElementById($(this).next('input').attr('id')).value;
		var commentMediaIdMusic = document.getElementById("commentMediaIdMusic").value;
		var ajaxCheckerMusic = 723472837;
		var ajaxChecker2Music = 340982904;
		$.ajax({
			type: "POST",
			url: "/music/deleteComment",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			data: {
				commentIDmusic :commentIDmusic,
			},
			success: function (response) {
				$('#userCommentsSectionMusic').load("http://localhost:8080/music/aftersubmitcomment/" + commentMediaIdMusic + "/" + ajaxCheckerMusic + "/" + ajaxChecker2Music);
			}
		}) 
	});
	
	
});