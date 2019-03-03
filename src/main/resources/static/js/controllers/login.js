(function() {
	var app = angular.module('login', []);
	
	app.controller('AuthorizationController', ['$http', '$window', function($http, $window) {
		
		this.authorize = function() {
			
			$http({
				method: 'POST',
				url: '/login/authorize',
				headers: {
					   'Content-Type': 'text/plain'
					 },
					 data: 'login'
			}).then(function success(response) {
				if (response.data == "Logged in") {
					$window.location.href = '/index.html';
				}
			});
		}
		
		this.authorize();
	}]);
	
	app.controller('LoginController', [ '$http', '$window', function($http, $window) {
		var control = this;
		control.user = {};
		control.result = "";
		
		this.login = function() {
			$http.post('/login/login', this.user).then(function success(response) {
				if (response.data.indexOf("Logged in") !== -1) {
						$window.location.href = '/index.html#/1';
				} else {
					var elem = document.getElementById("LoginResult");
					elem.style.color = "Red";
					
					control.result = response.data;
				}
				
			}, function error(response) {
				var elem = document.getElementById("LoginResult");
				elem.style.color = "Red";
				control.result = "Unknown error ocurred.";
			});
		}
		
		
	}]);
	
})();