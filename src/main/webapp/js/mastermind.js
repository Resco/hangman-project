var game;
var came_id;
var session_id;

<!--Document Ready-->

$(document).ready(function() {
	 
	$("#register").submit(on_register);
	$("#login").submit(on_login);
	$("#move").submit(on_move);
	$("#newgame").click(on_new_game);
	

	$("#buttons").hide();
	$("#target_general").hide();
	$(".inside").hide();;
	check_log();
});

<!--Register-->

function on_register() {
	$(".register_input").attr("style", "border-color: default");
	$(".login_input").attr("style", "border-color: default");
	$("#reg_submit").hide();
	$("#waiting_reg").show();
	
	$.ajax({
		url: '/register',
		method: 'post',
		success: on_register_success,
		error: on_error,
		data: {
			nickname: $("#nick_reg").val(),
			password: $("#password_reg").val(),
			re_password: $("#re_password_reg").val(),
			mail: $("#mail_reg").val(),
		},
	});
return false;
}

function on_register_success(data) {
	$("#display").attr("style", "color:red");
	$("#reg_submit").show();
	$("#waiting_reg").hide();
	if(data.description=="pw_leng"){
		$("#display").text("Password too short (8 characters at least)");
		$("#password_reg").attr("style", "border-color:red");
		
	}
	else if(data.description=="pw_equal"){
		$("#display").text("Please digit the same password twice");
		$("#re_password_reg").attr("style", "border-color:red");
	}
	else if(data.description=="mail"){
		$("#display").text("Your password must contain @");
		$("#mail_reg").attr("style", "border-color:red");
	}
	else if(data.description=="nick_exists"){
		$("#display").text("Please digit another nickname");
		$("#nick_reg").attr("style", "border-color:red");
	}
	else{
		$("#display").attr("style", "black");
		$("#display").text(data.description);
		
		$("#register")[0].reset();
		$("#login")[0].reset();
	}
	
	
}

function on_register_error(data) {
	$("#display").text(JSON.stringify(data.responseJSON));
}

<!--Login-->

function on_login() {
	$(".register_input").attr("style", "border-color: default");
	$(".login_input").attr("style", "border-color: default");
	$("#log_submit").hide();
	$("#waiting_log").show();
	
	$.ajax({
		url: '/authenticate',
		method: 'post',
		success: on_login_success,
		error: on_error,
		data: {
			nickname: $("#nick_login").val(),
			password: $("#password_login").val(),
		},
	});
return false;
}

function on_login_success(data) {
	$("#log_submit").show();
	$("#waiting_log").hide();
	$("#display").attr("style", "color:red");
	if(data.description=="pw"){
		$("#display").text("Try another password");
		$("#password_login").attr("style", "border-color:red");
	}
	else if(data.description=="nick_exist"){
		$("#display").text("Try another nickname");
		$("#nick_login").attr("style", "border-color:red");
	}
	else{
	$("#display").attr("style", "color:black");
	$("#display").text("");
	$(".outside").hide();
	$("#new_g").show();
		
	$("#register")[0].reset();
	$("#login")[0].reset();
	
	session_id = data.session_id;
	
	$("#buttons").show();
	$("#target_general").show();
	$("#welcome_text").hide();
	
	$("#send_submit").attr("disabled","disabled");
	
	render_template_navigate(data.description, data.average, data.games);
	
	}
	
}

function render_template_navigate(name, avg, games) {
	var template = $('#navigate-template').html();
	var rendered = Mustache.render(template, {
		description: name,
		average : avg,
		games : games
	});
	$("#target_general").html(rendered);
}

<!--Check Log-->

function check_log() {
	$.ajax({
		url: '/logged',
		method: 'get',
		success: on_login_success,
	})
}

<!--New Game-->

function on_new_game() {
	$("#move").hide();
	$("#info_game").hide();
	$("#waiting_new_game").show();      			
	//creazione della partita
	$.ajax({
		url: '/newgame',
		method: 'get',
		success: on_new_game_success,
		error: on_error,
	});	
}

function on_new_game_success (data){
	$("#move").show();
	$("#info_game").show();
	$("#waiting_new_game").hide();
	$("#send_submit").removeAttr("disabled");
	$("#answer").text("");
	$("#code").text(data.code);
	$("#general").text("Try to guess, if you can... ");
	$("#game_code").text(data.game_id);
	game_id = data.game_id
	game = new Game();
	var moves_view = new GameMovesView("#moves-template", "#answer");
	game.add_observer(moves_view);
}

<!--Move-->

function on_move (){
	$("#send_submit").hide();
	$("#waiting_send").show();
	$.ajax({
		url: '/move',
		method: 'post',
		success: on_move_success,
		error: on_error,
		data: {
			game_id: game_id,
			sequence: $("#seq").val(),
		},
	});
	return false;
}

function on_move_success (data){
	$("#send_submit").show();
	$("#waiting_send").hide();
	if(data.description=="short"){
		$("#display").attr("style", "color:red");
		$("#display").text("Insert a 4 digits sequence.");
	}
	else{
	game.on_move(data.move_id, data.move, data.result);
	
	if(data.result=="++++"){
		$("#move").hide();
		$("#general").text("Well done! YOU WIN!");
		render_template_navigate(data.description, data.average, data.games);
	}
	}
	$("#move")[0].reset();
}

<!--Global Rank Builder-->

function on_g_rank_builder(){
	$("#ranking").hide();
	$("#waiting_rank").show();
	$.ajax({
		url: '/g_rank',
		method: 'get',
		success: on_g_rank_builder_success,
		error: on_error,
	});	
}

function on_g_rank_builder_success (data){
	$("#ranking").show();
	$("#waiting_rank").hide();
	var template = $('#global_template').html();
	var rendered = Mustache.render(template, data);
	$("#ranking").html(rendered);
}

<!--Player History-->

function on_player_history(id){
	$("#ranking").hide();
	$("#waiting_rank").show();
	$.ajax({
		url: '/p_hist',
		method: 'post',
		data: {
			nickname: id,
		},
		success: on_player_history_success,
		error: on_error,
	});	
}

function on_player_history_success (data){
	$("#ranking_title").text("Player History")
	$("#ranking").show();
	$("#waiting_rank").hide();
	var template = $('#history_template').html();
	var rendered = Mustache.render(template, data);
	$("#ranking").html(rendered);
}


<!--Logout-->

function on_logout(){
	document.cookie = "session_id='" + session_id + "'; expires=Thu, 01 Jan 1970 00:00:00 UTC"; 
	$("#logout").hide();
	$("#waiting_logout").show();
	$.ajax({
		url: '/logout',
		method: 'post',
		data: {
			session_id: session_id,
		},
		success: on_logout_success,
		error: on_error,
	});
}

function on_logout_success(data){
	$("#logout").show();
	$("#waiting_logout").hide();
	
	$("#display").html("&nbsp;");
	
	$("#buttons").hide();
	$("#target_general").hide();
	$("#welcome_text").show();
	
	$("#general").text("Let's play a new game");
	$("#answer").html("&nbsp;");
	$("#send_submit").attr("disabled", "disabled");
	
	$(".inside").hide();
	$(".outside").show();
}

<!--Navigation Buttons Functions-->

function on_g_rank(){
	$("#ranking_title").text("General Ranking")
	$("#new_g").hide();
	$("#rank").show();
	on_g_rank_builder();
}

function on_game(){
	$("#new_g").show();
	$("#rank").hide();
}

<!--Error-->

function on_error(data) {
	window.alert(JSON.stringify(data.responseJSON));
}
      		