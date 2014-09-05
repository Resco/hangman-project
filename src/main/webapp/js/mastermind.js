		<script src='/js/lib/jquery-1.10.2.min.js'></script>
		<script src='/js/lib/mustache.js'></script>
		<script src='/js/game.js'></script>
    		<script src='/js/game_moves_view.js'></script>

		<script>
			var game;
			var came_id;
			var session_id;
			
			function render_template_navigate(name, avg, games) {
        		var template = $('#navigate-template').html();
        		var rendered = Mustache.render(template, {
          			description: name,
          			average : avg,
          			games : games
        		});
        		$("#target_general").html(rendered);
      		}
			
			$(document).ready(function() {
				 
        		$("#register").submit(on_register);
				$("#login").submit(on_login);
				$("#move").submit(on_move);
				$("#newgame").click(on_new_game);
				$("#rank_button").click(on_g_rank);
				$("#logout").click(on_logout);
				$("#log").show();
				
				
				$("#buttons").hide();
				$("#new_g").hide();
				$("#rank").hide();
				check_log();
      		});
      		
      		<!--Logout-->
      		
      		function on_logout(){
      			document.cookie = "session_id='" + session_id + "'; expires=Thu, 01 Jan 1970 00:00:00 UTC"; 
      			$.ajax({
          			url: '/logout',
          			method: 'post',
          			data: {
            			session_id: session_id,
          			},
          			success: on_logout_success,
        		});
      		}
      		
      		function on_logout_success(data){
				$("#display").text("");
				$("#register")[0].reset();
				$("#login")[0].reset();
				
				$("#buttons").hide();
				$(".inside").hide();
				$("#log").show();
				$("#reg").show();
      		}
      		
      		<!--Navigation Buttons Functions-->
      		
      		function on_g_rank(){
      			$("#new_g").hide();
      			$("#rank").show();
      			on_g_rank_builder();
      		}
      		
      		function on_game(){
      			$("#new_g").show();
      			$("#rank").hide();
      		}
      		
      		<!--Global Rank Builder-->
      		
      		function on_g_rank_builder(){
      			$.ajax({
          			url: '/g_rank',
          			method: 'get',
          			success: on_g_rank_builder_success,
        		});	
      		}
      		
      		function on_g_rank_builder_success (data){
      			$("#ranking").text(JSON.stringify(data));
      			var template = $('#global_template').html();
				var rendered = Mustache.render(template, data);
				$("#ranking").html(rendered);
      		}
      		
     		<!--Player History-->
      		
      		function on_player_history(id){
      			$.ajax({
          			url: '/p_hist',
          			method: 'post',
          			data: {
            			nickname: id,
          			},
          			success: on_player_history_success,
        		});	
      		}
      		
      		function on_player_history_success (data){
      			$("#ranking").text(JSON.stringify(data));
      			var template = $('#history_template').html();
				var rendered = Mustache.render(template, data);
				$("#ranking").html(rendered);
      		}
      		
      		<!--New Game-->
      		
      		function on_new_game() {
      			$("#move").show();
      			//creazione della partita
      			$.ajax({
          			url: '/newgame',
          			method: 'get',
          			success: on_new_game_success,
        		});	
      		}
      		
      		function on_new_game_success (data){
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
      			$.ajax({
          			url: '/move',
          			method: 'post',
          			success: on_move_success,
          			data: {
            			game_id: game_id,
            			sequence: $("#seq").val(),
          			},
        		});
        		return false;
      		}
      		
      		function on_move_success (data){
      			
      			game.on_move(data.move_id, data.move, data.result);
      			if(data.result=="++++"){
      				$("#move").hide();
      				$("#general").text("Well done! YOU WIN!");
      				render_template_navigate(data.description, data.average, data.games);
      			}
      			$("#move")[0].reset();
      		}
			
			<!--Check Log-->
			
			function check_log() {
        		$.ajax({
          			url: '/logged',
          			method: 'get',
          			success: on_login_success,
        		})
      		}
			
			<!--Register-->
			
			function on_register() {
				$(".register_input").attr("style", "border-color: default");
				$(".login_input").attr("style", "border-color: default");
        		$.ajax({
          			url: '/register',
          			method: 'post',
          			success: on_register_success,
          			error: on_register_error,
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
        		$.ajax({
          			url: '/authenticate',
          			method: 'post',
          			success: on_login_success,
          			error: on_login_error,
          			data: {
            			nickname: $("#nick_login").val(),
            			password: $("#password_login").val(),
          			},
        		});
        	return false;
      		}
			
			function on_login_success(data) {
				if(data.description=="pw"){
					$("#display").text("Try another password");
					$("#password_login").attr("style", "border-color:red");
				}
				else if(data.description=="nick_exist"){
					$("#display").text("Try another nickname");
					$("#nick_login").attr("style", "border-color:red");
				}
				else{
				$("#login")[0].reset();
				$("#log").hide();
				$("#reg").hide();
				$("#display").text("");
				$("#register")[0].reset();
				$("#login")[0].reset();
				session_id = data.session_id;
				
				$("#buttons").show();
				
				render_template_navigate(data.description, data.average, data.games);
        		
      			}
      			
      		}

      		function on_login_error(data) {
				$("#display").text(JSON.stringify(data.responseJSON));
      		}
      		
</script>