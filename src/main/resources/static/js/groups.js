(function() {
	var app = angular.module('groupsJS', []);
	
	
	
	
	
	
	app.controller("GroupController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.groups = [];
		control.groupsPerPage = 25;
		control.gap = 10;
		control.currentPage = 0;
		control.display = [];
		control.filters = [
		      {id: '1', name: 'grname', label: 'Group Name'},
		      {id: '2', name: 'grgm', label: 'Group Master'}
		    ];
		control.flt_selected = {id: '1', name: 'grname', label: 'Group Name'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/groups/filterName',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.groups = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '2') {

				$http({
					method: 'POST',
					url: '/groups/filterMaster',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.groups = response.data;
						control.groupToPages();

				});
			}  
		}
		
		control.removeGroup = function(id,uname) {
			
			var groupKey = {};
			groupKey.id = id;
			groupKey.uname = uname;
			
			$http({
				method: 'POST',
				url: '/groups/removeGroup',
				headers: {
					'Content-Type': 'application/json'  
					 },
				data: groupKey
			}).then(function success(response) {
				control.loadGroups();
				toastr["success"]('Game group '+response.data.grname+' successfully deleted!', "Success!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please make sure the desired entry has no relation to other entries in the database.', "Error!");
			  });

		}
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.groups.length; i++) {
	            if (i % control.groupsPerPage === 0) {
	            	control.display[Math.floor(i / control.groupsPerPage)] = [ control.groups[i] ];
	            } else {
	            	control.display[Math.floor(i / control.groupsPerPage)].push(control.groups[i]);
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
			control.flt_selected = {id: '1', name: 'grname', label: 'Group Name'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/groups/getGroups',
			}).then(function success(response) {

					control.groups = response.data;
					control.groupToPages();
					control.currentPage = 0;

			});
		}
		
		
		control.loadGroups = function() {

			$http({
				method: 'GET',
				url: '/groups/getGroups',
			}).then(function success(response) {

					control.groups = response.data;
					control.groupToPages();


			}, function errorCallback(response) {
				console.log(response.data);

			  });
		}
		
		/*
		if(control.groups.length < 1){
			control.loadGroups();

		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="2"){
				control.flt_selected = {id: '1', name: 'grname', label: 'Group Name'};
				control.loadGroups();
				control.currentPage = 0;
				control.filter = "";
			}
			
		});
		
		
	}]);



app.controller('GroupAddController', ['$scope', '$modal', '$log', '$http', '$rootScope', function ($scope, $modal, $log, $http,$rootScope) {
		 var control = this;
		   
		  
		  $scope.groupPackage =[];
		  $scope.groupPackage[0]="";

		  
		  

		  $scope.open = function (size) {
			  

		    var modalInstance = $modal.open({
		      templateUrl: 'myGroupContent.html',
		      controller: ModalInstanceCtrl,
		      size: size,
		      resolve: {
		    	  groupPackage: function () {
		          return $scope.groupPackage;
		        }
		      }
		    });

		    modalInstance.result.then(function (selectedItem) {
		      $scope.selectedAdm = selectedItem;
		    }, function () {

		    });
		  };
	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, groupPackage) {
	

			groupPackage[0]="";
			$scope.groupPackage = groupPackage;

			
		  
		  
		
		  $scope.ok = function () {
			  
			 

			  var groupname = "";
			  groupname = groupPackage[0];

			  $scope.groupname = groupname;

			  if($scope.groupname =="")
				{
					  $scope.result='Please, fill in the group name field.';
					  var elem = document.getElementById("GroupAddResult");
					  elem.style.color = "Red";
				} else {
			
			  $http({
					method: 'POST',
					url: '/groups/addGroup',
					headers: {
						   'Content-Type': 'text/plain'
						 },
						 data: groupname
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("GroupAddResult");
					if($scope.result =='Group successfully added'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Group successfully added'){  
						$http({
							method: 'GET',
							url: '/groups/getGroupsByGM',
							
						}).then(function success(response) {
							if(response.data!=null){
								$rootScope.GMGroups=response.data;
							}
						
						});
						setTimeout(function(){$modalInstance.close($scope.groupname);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("GroupAddResult");
					elem.style.color = "Red";
					$scope.result= 'Unkown error occured. Please re-check all your inputs, reload the page and try again later';
					console.log(response);
				});
				}
				
			  
			  
		  };
		  
		  

		  $scope.cancel = function () {
		    $modalInstance.dismiss('cancel');
		  };
		};
	}]);




app.controller('GroupUpdateController', ['$scope', '$modal', '$log', '$http', '$rootScope', function ($scope, $modal, $log, $http,$rootScope) {
	 var control = this;
	   
	  
	  $scope.groupPackage =[];
	  $scope.groupPackage[0]="";
	  $scope.groupPackage[1]={};
	  $scope.groupPackage[2]={};

	  
	  

	  $scope.open = function (id, uname) {
		  
		  var grp = {};
		  grp.id = id;
		  grp.uname = uname;
		  
		  $http({
				method: 'POST',
				url: '/groups/getGroup',
				headers: {
					   'Content-Type': 'application/json'
					 },
					 data: grp
			}).then(function success(response) {
				$scope.groupPackage[0]=response.data.grname;
				$scope.groupPackage[1]=id;
				$scope.groupPackage[2]=uname;
				
				 var modalInstance = $modal.open({
				      templateUrl: 'updateGroupContent.html',
				      controller: ModalInstanceCtrl,
				      resolve: {
				    	  groupPackage: function () {
				          return $scope.groupPackage;
				        }
				      }
				    });

				    modalInstance.result.then(function (selectedItem) {
				      $scope.selectedAdm = selectedItem;
				    }, function () {
				    
				    });
			
			});
		  

	   
	  };


	

	var ModalInstanceCtrl = function ($scope, $modalInstance, $window, groupPackage) {


		$scope.groupPackage = groupPackage;

		
	  
	  
	
	  $scope.ok = function () {
		  
		  var group = {};
		  var groupname = "";
		  groupname = groupPackage[0];
		  group.grname=groupname;
		  group.uname = groupPackage[2];
		  group.id = groupPackage[1];

		  $scope.groupname = groupname;
		  $scope.group = group;

		
		  $http({
				method: 'POST',
				url: '/groups/update',
				headers: {
					   'Content-Type': 'application/json'
					 },
					 data: group
			}).then(function success(response) {
				$scope.result = response.data;
				var elem = document.getElementById("GroupUpdateResult");
				if($scope.result =='Group successfully updated'){
					elem.style.color = "Green";
				} else {
					elem.style.color = "Red";
				}
				if($scope.result =='Group successfully updated'){  
					$http({
						method: 'GET',
						url: '/groups/getGroupsByGM',
						
					}).then(function success(response) {
						if(response.data!=null){
							$rootScope.GMGroups=response.data;
						}
					
					});
					setTimeout(function(){$modalInstance.close($scope.group);$window.location.reload();},1000);
					
				}
			}, function error(response) {
				var elem = document.getElementById("GroupUpdateResult");
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
	


})();