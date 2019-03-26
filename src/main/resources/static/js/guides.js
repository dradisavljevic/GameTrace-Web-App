(function() {
	var app = angular.module('guidesJS', []);
	
	
	
	app.controller("GuideController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.guides = [];
		control.guidesPerPage = 25;
		control.gap = 10;
		control.currentPage = 0;
		control.display = [];
		control.filters = [
		      {id: '1', name: 'guname', label: 'Guide Title'},
		      {id: '2', name: 'gameFullName', label: 'Game Name'},
		      {id: '3', name: 'gameUserUname', label: 'Author Name'}
		    ];
		control.flt_selected = {id: '1', name: 'guname', label: 'Guide Name'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/guides/filterTitle',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.guides = response.data;
						for(var i=0;i<response.data.length;i++) { 
							control.guides[i].gameFullName=control.guides[i].game.gamename+" ("+control.guides[i].game.gamery+")";
						}
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '2') {

				$http({
					method: 'POST',
					url: '/guides/filterGame',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
					control.currentPage = 0;
						control.guides = response.data;
						for(var i=0;i<response.data.length;i++) { 
							control.guides[i].gameFullName=control.guides[i].game.gamename+" ("+control.guides[i].game.gamery+")";
						}
						control.groupToPages();

				});
			}  else if (control.flt_selected.id == '3') {

				$http({
					method: 'POST',
					url: '/guides/filterUser',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.guides = response.data;
						for(var i=0;i<response.data.length;i++) { 
							control.guides[i].gameFullName=control.guides[i].game.gamename+" ("+control.guides[i].game.gamery+")";
						}
						control.groupToPages();

				});
			}
			 
		}
		
		
		control.removeGuide = function(id, gameId, uname) {
			
			var guideKey = {};
			guideKey.id = id;
			guideKey.gameId = gameId;
			guideKey.uname = uname;
			
			
			$http({
				method: 'POST',
				url: '/guides/removeGuide',
				headers: {
					'Content-Type': 'application/json'  
					 },
				data: guideKey
			}).then(function success(response) {
				control.loadGuides();
				toastr["success"]('Game guide '+ response.data.guname+' successfully deleted!', "Success!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please make sure the desired entry has no relation to other entries in the database.', "Error!");
			  });
		}
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.guides.length; i++) {
	            if (i % control.guidesPerPage === 0) {
	            	control.display[Math.floor(i / control.guidesPerPage)] = [ control.guides[i] ];
	            } else {
	            	control.display[Math.floor(i / control.guidesPerPage)].push(control.guides[i]);
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
			control.flt_selected = {id: '1', name: 'guname', label: 'Guide Name'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/guides/getGuides',
			}).then(function success(response) {

					control.guides = response.data;
					for(var i=0;i<response.data.length;i++) { 
						control.guides[i].gameFullName=control.guides[i].game.gamename+" ("+control.guides[i].game.gamery+")";
					}
					control.groupToPages();
					control.currentPage = 0;

			});
		}
		
		
		control.loadGuides = function() {

			$http({
				method: 'GET',
				url: '/guides/getGuides',
			}).then(function success(response) {

					control.guides = response.data;
					for(var i=0;i<response.data.length;i++) { 
						control.guides[i].gameFullName=control.guides[i].game.gamename+" ("+control.guides[i].game.gamery+")";
					}
					control.groupToPages();


			}, function errorCallback(response) {
				console.log(response.data);

			  });
		}
		
		/*
		if(control.guides.length < 1){
			control.loadGuides();

		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="12"){
				control.flt_selected = {id: '1', name: 'guname', label: 'Guide Name'};
				control.loadGuides();
				control.currentPage = 0;
				control.filter = "";
			}
			
		});
		
		
	}]);
	
	
	app.controller('GuideAddController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 
		 $scope.guideGames = [];
		 
		 $scope.guidePackage = [];
		 $scope.guidePackage[0]="";
		 $scope.guidePackage[1]={};
		 $scope.guidePackage[2]="";
		 $scope.guidePackage[3]= {};
		 $scope.guideArray=[];
		  
		  
		  
		  
		  

		  $scope.open = function (size) {
			  
			  
			  $http({
					method: 'GET',
					url: '/games/getGamesCompact'
				}).then(function success(response) {
					if (response.data != null) {
						$scope.guideGames = response.data;
						$scope.selectedGuideGame = $scope.guideGames[0];
						for(var i=0;i<response.data.length;i++) { 
							$scope.guideGames[i].gamename=$scope.guideGames[i].gamename+" ("+$scope.guideGames[i].gamery+")";

						}
						$scope.guidePackage[3]=$scope.guideGames;
						$scope.guidePackage[1]=$scope.guideGames[0];
					}
					
					var modalInstance = $modal.open({
					      templateUrl: 'myGuideContent.html',
					      controller: ModalInstanceCtrl,
					      size: size,
					      resolve: {
					    	  guidePackage: function () {
					          return $scope.guidePackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedGuide = selectedItem;
					    }, function () {
					
					    });
					
				});
			  

		    
		  };
	

	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, guidePackage) {
	

			guidePackage[0]="";
			guidePackage[2]="";
			
			guidePackage[1]=guidePackage[3][0];
			$scope.guidePackage = guidePackage;

		  

		  $scope.ok = function () {
			  
			 

			  var guide = {};
			  guide.guname = guidePackage[0];
			  guide.gucont = guidePackage[2];
			  guide.gameGameid = guidePackage[1].gameid;

			  $scope.guide = guide;

			
			  $http({
					method: 'POST',
					url: '/guides/addGuide',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: guide
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("GuideAddResult");
					if($scope.result =='Guide successfully added'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Guide successfully added'){  
						setTimeout(function(){$modalInstance.close($scope.guide);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("GuideAddResult");
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
	
	
	
	
	
	app.controller('GuideUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 
		 $scope.guideGames = [];
		 
		 $scope.guidePackage = [];
		 $scope.guidePackage[0]="";
		 $scope.guidePackage[1]="";
		 $scope.guideArray=[];
		 $scope.guidePackage[2] = {};
		 $scope.guidePackage[3] = {};
		 $scope.guidePackage[4] = {};
		  
		  
		  
		  

		  $scope.open = function (id,gameId, uname) {

			  $scope.guidePackage[2]=id;
			  $scope.guidePackage[3]=gameId;
			  $scope.guidePackage[4]=uname;
			  var guideKey = {};
			  guideKey.id = id;
			  guideKey.gameId = gameId;
			  guideKey.uname = uname;
			  $http({
					method: 'POST',
					url: '/guides/getGuide',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: guideKey
				}).then(function success(response) {
					$scope.guidePackage[0]=response.data.guname;
					$scope.guidePackage[1]=response.data.gucont;
					
					var modalInstance = $modal.open({
					      templateUrl: 'updateGuideContent.html',
					      controller: ModalInstanceCtrl,
					      resolve: {
					    	  guidePackage: function () {
					          return $scope.guidePackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedGuide = selectedItem;
					    }, function () {
					
					    });
				
				});
			  

		    
		  };
	

	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window,  guidePackage) {
	

		
			
			$scope.guidePackage = guidePackage;

		  

		  $scope.ok = function () {
			  
			 

			  var guide = {};
			  guide.guname = guidePackage[0];
			  guide.gucont = guidePackage[1];
			  guide.gameGameid = guidePackage[3];
			  guide.guid = guidePackage[2];
			  guide.gameUserUname = guidePackage[4];

			  $scope.guide = guide;

			
			  $http({
					method: 'POST',
					url: '/guides/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: guide
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("GuideUpdateResult");
					if($scope.result =='Guide successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Guide successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.guide);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("GuideUpdateResult");
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
	
	
	
	app.controller('SpecificGuideAddController', ['$scope', '$modal', '$log', '$http', '$location', function ($scope, $modal, $log, $http, $location) {
		 var control = this;
		 
		 $scope.guidePackage = [];
		 $scope.guidePackage[0]="";
		 $scope.guidePackage[1]={};
		 $scope.guidePackage[2]="";


		  $scope.open = function (size) {
			  

		    var modalInstance = $modal.open({
		      templateUrl: 'mySpecificGuideContent.html',
		      controller: ModalInstanceCtrl,
		      size: size,
		      resolve: {
		    	  guidePackage: function () {
		          return $scope.guidePackage;
		        }
		      }
		    });

		    modalInstance.result.then(function (selectedItem) {
		      $scope.selectedGuide = selectedItem;
		    }, function () {
	
		    });
		  };
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, guidePackage) {
	
			
			guidePackage[0]="";
			guidePackage[2]="";
			var putanja = $location.path();

			var deljeno = putanja.split('/');
			guidePackage[1]=deljeno[2];
			$scope.guidePackage = guidePackage;
			

			

			$scope.ok = function () {
			  
			 

			  var guide = {};
			  guide.guname = guidePackage[0];
			  guide.gucont = guidePackage[2];
			  guide.gameGameid = guidePackage[1];

			  $scope.guide = guide;

			
			  $http({
					method: 'POST',
					url: '/guides/addGuide',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: guide
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("SpecificGuideAddResult");
					if($scope.result =='Guide successfully added'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Guide successfully added'){  
						setTimeout(function(){$modalInstance.close($scope.guide);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("SpecificGuideAddResult");
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
	
	
	
	
	
	
	app.controller('SpecificGuideUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 
		 $scope.guideGames = [];
		 
		 $scope.guidePackage = [];
		 $scope.guidePackage[0]="";
		 $scope.guidePackage[1]="";
		 $scope.guideArray=[];
		 $scope.guidePackage[2] = {};
		 $scope.guidePackage[3] = {};
		 $scope.guidePackage[4] = {};
		  
		  
		  
		  

		  $scope.open = function (id,gameId, uname) {

			  $scope.guidePackage[2]=id;
			  $scope.guidePackage[3]=gameId;
			  $scope.guidePackage[4]=uname;
			  var guideKey = {};
			  guideKey.id = id;
			  guideKey.gameId = gameId;
			  guideKey.uname = uname;
			  $http({
					method: 'POST',
					url: '/guides/getGuide',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: guideKey
				}).then(function success(response) {
					$scope.guidePackage[0]=response.data.guname;
					$scope.guidePackage[1]=response.data.gucont;
					
					
					 var modalInstance = $modal.open({
					      templateUrl: 'updateGuideContent.html',
					      controller: ModalInstanceCtrl,
					      resolve: {
					    	  guidePackage: function () {
					          return $scope.guidePackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedGuide = selectedItem;
					    }, function () {
					
					    });
				
				});
			  

		   
		  };
	

	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window,  guidePackage) {
	

		
			
			$scope.guidePackage = guidePackage;

		  

		  $scope.ok = function () {
			  
			 

			  var guide = {};
			  guide.guname = guidePackage[0];
			  guide.gucont = guidePackage[1];
			  guide.gameGameid = guidePackage[3];
			  guide.guid = guidePackage[2];
			  guide.gameUserUname = guidePackage[4];

			  $scope.guide = guide;

			
			  $http({
					method: 'POST',
					url: '/guides/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: guide
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("GuideUpdateResult");
					if($scope.result =='Guide successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Guide successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.guide);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("GuideUpdateResult");
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