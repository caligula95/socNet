/**
 * 
 */

function getFriends(){
	
	var pathname = window.location.pathname;
	console.log(pathname);
	var index = pathname.lastIndexOf("/")
	var friend = pathname.substring(index, pathname.length)
        if(friend == "/friends")
            friend = "";

	var url = "http://localhost:8080/socNet/api/friends" + friend;
	console.log(url);
	$.get(url, function(data){
		console.log(JSON.stringify(data));
		
		for(user of data){
		var friendUrl = "http://localhost:8080/socNet/home/" + user.username;
		console.log(friendUrl);
        var div = '<li class="list-group-item">' +
                        '<div class="col-xs-12 col-sm-3">' +
                            '<img src="http://api.randomuser.me/portraits/men/49.jpg" ' +
                        'alt="' + user.firstName + ' ' + user.lastName + 
                        '" class= "img-responsive img-circle" />' +
                        '</div>' +
        				'<div class="col-xs-12 col-sm-9">' +
                             '<div class="profile-usertitle-name">' +
                        '<a href = "' + friendUrl + ' ">' + user.firstName + 
                        ' ' + user.lastName + ' </a>' +
                    	'</div>' +
                    	 '<a>Write message</a>'+
                        '</div>' +
                        '<div class="clearfix"></div>' + 
                    '</li>';
        $("#contact-list").append(div);
		}
	}).error(function(jqXHR, textStatus, errorThrown) {
		  if (textStatus == 'timeout')
			    console.log('The server is not responding');

			  if (textStatus == 'error')
			    console.log(errorThrown);

			  // Etc
			});
}

window.onload = getFriends;