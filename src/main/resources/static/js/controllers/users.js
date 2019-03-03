(function() {
	var app = angular.module('usersJS', []);
	
	
	
	app.controller("UserController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.users = [];
		control.gap = 10;
		control.currentPage = 0;
		control.itemsPerPage = 20;
		control.display = [];
		//$window.location.href = '/login.html';
		control.flt_selected = {id: '1', name: 'uname', label: 'Username'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {
				$http({
					method: 'POST',
					url: '/users/filterUsername',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.users = response.data;
						control.groupToPages();

				});
			}
		
		control.removeUser = function(username) {
			
			$http({
				method: 'POST',
				url: '/users/removeUser',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: username
			}).then(function success(response) {
				toastr["success"]('User ' + username + ' successfully deleted!', "Success!");
				control.loadUsers();
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please make sure the desired entry has no relation to other entries in the database.', "Error!");
			  });

		}
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.users.length; i++) {
	            if (i % control.itemsPerPage === 0) {
	            	control.display[Math.floor(i / control.itemsPerPage)] = [ control.users[i] ];
	            } else {
	            	control.display[Math.floor(i / control.itemsPerPage)].push(control.users[i]);
	            }
	        }
	    };
	    
	    
	    control.range = function(size, start, end) {
	    	var ret = [];

	    	
	    	if(size < end) {
	    		end = size;
	    		start = size - control.gap;
	    	}
	    	for(var i = start; i<end; i++) {
	    		ret.push(i);
	    	}

	    return ret;
	    };
	    
	    
	    control.prevPage = function () {
	        if (control.currentPage > 0) {
	        	control.currentPage--;
	        }
	    };
	    
	    control.nextPage = function () {
	        if (control.currentPage < control.display.length - 1) {
	        	control.currentPage++;
	        }
	    };
	    
	    control.setPage = function (test) {
	    	control.currentPage = test;
	    };
		
		control.asc = function() {
			control.flt_reverse = false;
			
			control.groupToPages();
		}
		
		control.desc = function() {
			control.flt_reverse = true;
			
			control.groupToPages();
		}
		
		control.reset = function() {
			control.flt_selected = {id: '1', name: 'uname', label: 'Username'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/users/getUsers',
			}).then(function success(response) {
					control.currentPage = 0;
					control.users = response.data;
					control.groupToPages();

			});
		}
		
		
		control.loadUsers = function() {
			$http({
				method: 'GET',
				url: '/users/getUsers',
			}).then(function success(response) {

					control.users = response.data;
					control.groupToPages();

			});
		}
		/*
		if(control.users.length < 1){
			control.loadUsers();
		} */
		
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="8"){
				control.flt_selected = {id: '1', name: 'tickt', label: 'Ticket Title'};
				control.loadUsers();
				control.filter = "";
			}
			
		});
		
		
	}]);
	
	
	
	
	app.controller('AdminAddController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		  
		  
		  
		  $scope.adminPackage =[];
		  $scope.adminPackage[0]="";
		  $scope.adminPackage[1]="";
		  $scope.adminPackage[2]="";
		  $scope.adminPackage[3]="";
		  $scope.adminPackage[4]="";

		  
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#adminImg').attr('src', e.target.result);
		
			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileAdmin").change(function(){
				readURL(this);
			});
		  
			$scope.removeImg = function() {
				adminPackage[4] = null;
	
			}
			
			$scope.removeImg = function() {
				$('#adminImg').attr('src', "");
				$('#adminImg').attr('value', "");
				$('#fileAdmin').attr('value', "");
				$('#fileAdmin').trigger("input");
				$('#adminImg').trigger("input");
				adminPackage[4]="";

				fileread="adminPackage[4]";
			}

		  $scope.open = function (size) {
			  

		    var modalInstance = $modal.open({
		      templateUrl: 'myAdminContent.html',
		      controller: ModalInstanceCtrl,
		      size: size,
		      resolve: {
		    	  adminPackage: function () {
		          return $scope.adminPackage;
		        }
		      }
		    });

		    modalInstance.result.then(function (selectedItem) {
		      $scope.selectedAdm = selectedItem;
		    }, function () {

		    });
		  };
	

		

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, adminPackage) {
			
			adminPackage[0]="";
			adminPackage[1]="";
			adminPackage[2]="";
			adminPackage[3]="";
			adminPackage[4]="";
			$scope.adminPackage = adminPackage;
			
			$scope.removeImg = function() {
				$('#adminImg').attr('src', "");
				$('#adminImg').attr('value', "");
				$('#fileAdmin').attr('value', "");
				$('#fileAdmin').trigger("input");
				$('#adminImg').trigger("input");
				adminPackage[4]="";

				fileread="adminPackage[4]";
			}
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#adminImg').attr('src', e.target.result);

			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileAdmin").change(function(){
				readURL(this);
			});

		  $scope.ok = function () {
			  
			 
			  
			  var admUser = {};
			  admUser.uname = adminPackage[0];
			  admUser.pword = adminPackage[1];
			  admUser.uemail = adminPackage[3];
			  admUser.uavat =  adminPackage[4];
			  $scope.admUser = admUser;

			
			  $http({
					method: 'POST',
					url: '/users/addmin',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: admUser
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("AdminAddResult");
					if($scope.result =='Administrator successfully added'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Administrator successfully added'){  
						setTimeout(function(){$modalInstance.close($scope.admUser);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("AdminAddResult");
					elem.style.color = "Red";
					$scope.result= 'Unkown error occured. Please re-check all your inputs, reload the page and try again later';
					console.log(response);
				});
				
			  
			 
		  };
		  
		  

		  $scope.cancel = function () {
		    $modalInstance.dismiss('cancel');
		  };
		};
	}]);
	
	
	app.controller('SendRequestController', ['$scope', '$modal', '$log', '$http', '$rootScope', function ($scope, $modal, $log, $http,$rootScope) {
		 var control = this;
		  
		 
		 control.sendRequest = function(username){
			 $http({
					method: 'POST',
					url: '/invreq/sendFRequest',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: username
				}).then(function success(response) {
					$scope.result = response.data;
					$rootScope.selectUser(username);
					toastr["success"]('Request sent to: ' + username, response.data);

				}, function error(response) {
					$scope.result= 'Error occured, please reload page and try again later.';
					toastr["error"]('Error occured, please reload page and try again later.', "Error!");
					console.log(response);
				});
		 }
		  
		  
		  $scope.ginvitePackage =[];
		  $scope.ginvitePackage[0]=[];
		  $scope.ginvitePackage[1]="";
		  $scope.ginvitePackage[2]="";

		  
		 

		  $scope.open = function (username) {
			  $scope.ginvitePackage[2]=username;
			  $http({
					method: 'POST',
					url: '/groups/getGroupsByGMAndMember',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: username
				}).then(function success(response) {
					if (response.data != null) {
						$scope.gameGroups = response.data;
						$scope.ginvitePackage[0]=$scope.gameGroups;
						$scope.gameSelectedGroup = response.data[0];
						$scope.ginvitePackage[1] = $scope.gameSelectedGroup;
					}
							    var modalInstance = $modal.open({
							      templateUrl: 'myGroupInviteContent.html',
							      controller: ModalInstanceCtrl,
							      resolve: {
							    	  ginvitePackage: function () {
							          return $scope.ginvitePackage;
							        }
							      }
							    });
				
						    modalInstance.result.then(function (selectedItem) {
						      $scope.selectedGI = selectedItem;
						    }, function () {
				
						    });
				});
		  };
	

		

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, ginvitePackage) {
			
			ginvitePackage[1]=ginvitePackage[0][0];
			$scope.ginvitePackage = ginvitePackage;
			$scope.check = true;
			
			$scope.$watch('ginvitePackage[1]', function() {
				var group = {};
				group.uname = ginvitePackage[2];
				group.grname = ginvitePackage[1].grgm;
				group.id = ginvitePackage[1].grid;
				$scope.group = group;
			
				$http({
					method: 'POST',
					url: '/invreq/alreadySent',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: group
				}).then(function success(response) {
					if(response.data=="true"){
						$scope.check = true;
					} else {
						$scope.check = false;
					}
				});
			});
			
			
			

		  $scope.ok = function () {
			  
			 
			  
			  var group = {};
			  group.uname = ginvitePackage[2];
			  group.grname = ginvitePackage[1].grgm;
			  group.id = ginvitePackage[1].grid;
			  $scope.group = group;

			
			  $http({
					method: 'POST',
					url: '/invreq/sendGInvite',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: group
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("GroupInviteResult");
					if($scope.result =='Group Invite sent!'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Group Invite sent!'){  
						setTimeout(function(){$modalInstance.close($scope.group);},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("GroupInviteResult");
					elem.style.color = "Red";
					$scope.result= 'Unkown error occured. Please reload the page and try again later';
					console.log(response);
				});
				
			  
			 
		  };
		  
		  

		  $scope.cancel = function () {
		    $modalInstance.dismiss('cancel');
		  };
		};
	}]);
	
	
	})();