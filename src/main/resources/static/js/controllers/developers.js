(function() {
	var app = angular.module('developersJS', []);
	
	
	
	
	
	app.controller("DeveloperController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.developers = [];
		control.developersPerPage = 25;
		control.gap = 10;
		control.currentPage = 0;
		control.display = [];
		control.filters = [
		      {id: '1', name: 'gdname', label: 'Developer Name'},
		      {id: '2', name: 'gdcount', label: 'Minimal Game Count'}
		    ];
		control.flt_selected = {id: '1', name: 'gdname', label: 'Developer Name'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/developers/filterName',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;

						control.developers = response.data;
						
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '2') {
				
				if(isNaN(control.filter)){
					
				} else {
					$http({
						method: 'POST',
						url: '/developers/filterCount',
						headers: {
							'Content-Type': 'text/plain'   
							 },
						data: control.filter
					}).then(function success(response) {
							control.currentPage = 0;
							control.developers = response.data;
							
							control.groupToPages();
	
					});
				}
			}  
			 
		}
		
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="14"){
				control.flt_selected = {id: '1', name: 'gdname', label: 'Developer Name'};
				control.loadDevelopers();
				control.currentPage = 0;
				control.filter = "";
			}
			
		});
		
		control.removeDeveloper = function(id) {
			
			
			$http({
				method: 'POST',
				url: '/developers/removeDeveloper',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: id
			}).then(function success(response) {
				control.loadDevelopers();
				toastr["success"]('Game Developer ' + response.data.gdname + ' successfully deleted!', "Success!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please make sure the developer has no games he has developed on our site.', "Error!");
			  });
		}
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.developers.length; i++) {
	            if (i % control.developersPerPage === 0) {
	            	control.display[Math.floor(i / control.developersPerPage)] = [ control.developers[i] ];
	            } else {
	            	control.display[Math.floor(i / control.developersPerPage)].push(control.developers[i]);
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
			control.flt_selected = {id: '1', name: 'gdname', label: 'Developer Name'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/developers/getDevelopersZeroless',
			}).then(function success(response) {

					control.developers = response.data;
					control.currentPage = 0;
					control.groupToPages();

			});
		}
		
		
		control.loadDevelopers = function() {

			$http({
				method: 'GET',
				url: '/developers/getDevelopersZeroless',
			}).then(function success(response) {

					control.developers = response.data;
					
					control.groupToPages();


			}, function errorCallback(response) {
				console.log(response.data);

			  });
		}
		/*
		if(control.developers.length < 1){
			control.loadDevelopers();

		}
		*/
		
	}]);
	
	
	
	
	
	app.controller('DeveloperAddController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		   
		  
		  $scope.developerPackage =[];
		  $scope.developerPackage[0]="";

		  
		  

		  $scope.open = function (size) {
			  

		    var modalInstance = $modal.open({
		      templateUrl: 'myDeveloperContent.html',
		      controller: ModalInstanceCtrl,
		      size: size,
		      resolve: {
		    	  developerPackage: function () {
		          return $scope.developerPackage;
		        }
		      }
		    });

		    modalInstance.result.then(function (selectedItem) {
		      $scope.selectedDeveloper = selectedItem;
		    }, function () {

		    });
		  };
	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, developerPackage) {
	

			developerPackage[0]="";
			$scope.developerPackage = developerPackage;

			
		  
		  
		
		  $scope.ok = function () {
			  
			 

			  var devname = "";
			  devname = developerPackage[0];

			  $scope.devname = devname;

			  if($scope.devname =="")
			{
				  var elem = document.getElementById("DeveloperAddResult");
				  elem.style.color = "Red";
				  $scope.result='Please, fill in the developer name field.';
			} else {
			
			  $http({
					method: 'POST',
					url: '/developers/save',
					headers: {
						   'Content-Type': 'text/plain'
						 },
						 data: devname
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("DeveloperAddResult");
					if($scope.result =='Game developer successfully added'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Game developer successfully added'){  
						setTimeout(function(){$modalInstance.close($scope.devname);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("DeveloperAddResult");
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
	
	
	
	app.controller('DeveloperUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 
	
		 
		 $scope.developerPackage = [];
		 $scope.developerPackage[0]="";
		 $scope.developerPackage[1]={};
		
		  
		  
		  
		  

		  $scope.open = function (id) {

			  $scope.developerPackage[1]=id;
			
			  $http({
					method: 'POST',
					url: '/developers/getDeveloper',
					headers: {
						   'Content-Type': 'text/plain'
						 },
						 data: id
				}).then(function success(response) {
					$scope.developerPackage[0]=response.data.gdname;
				
					var modalInstance = $modal.open({
					      templateUrl: 'updateDeveloperContent.html',
					      controller: ModalInstanceCtrl,
					      resolve: {
					    	  developerPackage: function () {
					          return $scope.developerPackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedDeveloper = selectedItem;
					    }, function () {
					
					    });
					
				
				});
			  

		    
		  };
	

	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window,  developerPackage) {
	

		
			
			$scope.developerPackage = developerPackage;

		  

		  $scope.ok = function () {
			  
			 

			  var developer = {};
			  developer.name = developerPackage[0];
			  developer.id = developerPackage[1];
			 
			  $scope.developer = developer;

			
			  $http({
					method: 'POST',
					url: '/developers/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: developer
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("DeveloperUpdateResult");
					if($scope.result =='Game developer successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Game developer successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.developer);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("DeveloperUpdateResult");
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