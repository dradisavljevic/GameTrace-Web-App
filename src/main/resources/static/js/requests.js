(function() {
	var app = angular.module('requestsJS', []);
	
	
	
	
	app.controller("RequestController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.requests = [];
		control.gap = 10;
		control.currentPage = 0;
		control.itemsPerPage = 25;
		control.display = [];
		control.filters = [
		      {id: '1', name: 'reqgname', label: 'Game Name'},
		      {id: '2', name: 'reqgrd', label: 'Release Year'},
		      {id: '3', name: 'reqsub', label: 'Submitted By'},
		      {id: '4', name: 'reqstatus', label: 'Request Status'}
		    ];
		//$window.location.href = '/login.html';
		control.flt_selected = {id: '1', name: 'reqgname', label: 'Game Name'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/requests/filterName',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.requests = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '2') {
				if(isNaN(control.filter)){
					
				} else {
					$http({
						method: 'POST',
						url: '/requests/filterYear',
						headers: {
							'Content-Type': 'text/plain'   
							 },
						data: control.filter
					}).then(function success(response) {
							control.currentPage = 0;
							control.requests = response.data;
							control.groupToPages();
	
					});
				}
			} else if (control.flt_selected.id == '3') {

				$http({
					method: 'POST',
					url: '/requests/filterUser',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.requests = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '4') {

				$http({
					method: 'POST',
					url: '/requests/filterStatus',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.requests = response.data;
						control.groupToPages();

				});
			}
		}
		
		control.isPending = function(status) {
			if(status == "PENDING"){
				return true;
			} else {
				return false;
			}
			
		}
		
		control.isNotAccepted = function(status) {
			if(status != "ACCEPTED"){
				return true;
			} else {
				return false;
			}
			
		}
		
		control.removeRequest = function(id) {
			
			$http({
				method: 'POST',
				url: '/requests/removeRequest',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: id
			}).then(function success(response) {
				control.loadRequests();
				toastr["success"]('Game request successfully deleted!', "Success!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please make sure the desired entry has no relation to other entries in the database.', "Error!");
			  });

		}
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.requests.length; i++) {
	            if (i % control.itemsPerPage === 0) {
	            	control.display[Math.floor(i / control.itemsPerPage)] = [ control.requests[i] ];
	            } else {
	            	control.display[Math.floor(i / control.itemsPerPage)].push(control.requests[i]);
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
			control.flt_selected = {id: '1', name: 'reqgname', label: 'Game Name'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/requests/getRequests',
			}).then(function success(response) {

					control.requests = response.data;
					control.groupToPages();
					control.currentPage = 0;

			});
		}
		
		
		control.loadRequests = function() {

			$http({
				method: 'GET',
				url: '/requests/getRequests',
			}).then(function success(response) {

					control.requests = response.data;
					control.groupToPages();


			}, function errorCallback(response) {
				console.log(response.data);

			  });
		}
		/*
		if(control.requests.length < 1){
			control.loadRequests();

		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="7"){
				control.flt_selected = {id: '1', name: 'reqgname', label: 'Game Name'};
				control.loadRequests();
				control.currentPage = 0;
				control.filter = "";
			}
			
		});
		
		
	}]);
	
	
	
	
	
	
	app.controller('RequestAddController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 $scope.gameYear = [
		      {id: '1', name: '2017'},
		      {id: '2', name: '2016'},
		      {id: '3', name: '2015'},
		      {id: '4', name: '2014'},
		      {id: '5', name: '2013'},
		      {id: '6', name: '2012'},
		      {id: '7', name: '2011'},
		      {id: '8', name: '2010'},
		      {id: '9', name: '2009'},
		      {id: '10', name: '2008'},
		      {id: '11', name: '2007'},
		      {id: '12', name: '2006'},
		      {id: '13', name: '2005'},
		      {id: '14', name: '2004'},
		      {id: '15', name: '2003'},
		      {id: '16', name: '2002'},
		      {id: '17', name: '2001'},
		      {id: '18', name: '2000'},
		      {id: '19', name: '1999'},
		      {id: '20', name: '1998'},
		      {id: '21', name: '1997'},
		      {id: '22', name: '1996'},
		      {id: '23', name: '1995'},
		      {id: '24', name: '1994'},
		      {id: '25', name: '1993'},
		      {id: '26', name: '1992'},
		      {id: '27', name: '1991'},
		      {id: '28', name: '1990'},
		      {id: '29', name: '1989'},
		      {id: '30', name: '1988'},
		      {id: '31', name: '1987'},
		      {id: '32', name: '1986'},
		      {id: '33', name: '1985'},
		      {id: '34', name: '1984'},
		      {id: '35', name: '1983'},
		      {id: '36', name: '1982'},
		      {id: '37', name: '1981'},
		      {id: '38', name: '1980'}
		 ]
		 $scope.selectedGameYear = {id: '1', name: '2017'};
		  
		  
		  
		  $scope.requestPackage =[];
		  $scope.requestPackage[0]=$scope.gameYear;
		  $scope.requestPackage[1]=$scope.selectedGameYear;
		  $scope.gameImage = "";
		  $scope.gameName = "";
		  $scope.gameDescription = "";
		  $scope.gameDetection = "";
		  $scope.requestPackage[3] = $scope.gameImage;
		  $scope.requestPackage[4] = $scope.gameName;
		  $scope.requestPackage[5] = $scope.gameDescription;
		  $scope.requestPackage[6] = $scope.gameDetection;
		  $scope.requestPackage[8] = [];
		  
		  
		 
		  
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#requestGameImg').attr('src', e.target.result);
		
			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileRequest").change(function(){
				readURL(this);
			});
			
			$scope.removeImg = function() {
				$('#requestGameImg').attr('src', "");
				$('#requestGameImg').attr('value', "");
				$('#fileRequest').attr('value', "");
				$('#fileRequest').trigger("input");
				$('#requestGameImg').trigger("input");
				requestPackage[3]="";

				fileread="requestPackage[3]";
			}
		  
			
		  $scope.open = function (size) {
			  
			  $http({
					method: 'GET',
					url: '/games/getDevelopers'
				}).then(function success(response) {
					if (response.data != null) {
						$scope.gameDevelopers = response.data;
						$scope.requestPackage[2]=$scope.gameDevelopers;
						$scope.gameSelectedDeveloper = response.data[0];
						$scope.requestPackage[7] = $scope.gameSelectedDeveloper;
					}
					
					 var modalInstance = $modal.open({
					      templateUrl: 'myRequestContent.html',
					      controller: ModalInstanceCtrl,
					      size: size,
					      resolve: {
					    	  requestPackage: function () {
					          return $scope.requestPackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selected = selectedItem;
					    }, function () {
					
					    });
				});
			  

		   
		  };
	

		

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, requestPackage) {
			
			

		  $scope.gameYear = requestPackage[0];
		  $scope.selectedGameYear = requestPackage[1];
		  $scope.gameDevelopers=requestPackage[2];
		  $scope.gameImage=requestPackage[3];
		  $scope.gameName=requestPackage[4];
		  $scope.gameDescription=requestPackage[5];
		  $scope.gameDetection=requestPackage[6];
		  requestPackage[4]="";
		  requestPackage[1] = {id: '1', name: '2017'};
		  requestPackage[5]= "";
		  requestPackage[7]= requestPackage[2][0];
		  requestPackage[8]=[];
	      requestPackage[6]="";
	      requestPackage[3]="";
		  $scope.requestPackage = requestPackage;
		  
		  $scope.removeImg = function() {
				$('#requestGameImg').attr('src', "");
				$('#requestGameImg').attr('value', "");
				$('#fileRequest').attr('value', "");
				$('#fileRequest').trigger("input");
				$('#requestGameImg').trigger("input");
				requestPackage[3]="";

				fileread="requestPackage[3]";
			}
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#requestGameImg').attr('src', e.target.result);

			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileRequest").change(function(){
				readURL(this);
			});
			
			$scope.contains = function(needle) {
			    // Per spec, the way to identify NaN is that it is not equal to itself
			    var findNaN = needle !== needle;
			    var indexOf;

			    if(!findNaN && typeof Array.prototype.indexOf === 'function') {
			        indexOf = Array.prototype.indexOf;
			    } else {
			        indexOf = function(needle) {
			            var i = -1, index = -1;

			            for(i = 0; i < this.length; i++) {
			                var item = this[i];

			                if((findNaN && item !== item) || item === needle) {
			                    index = i;
			                    break;
			                }
			            }

			            return index;
			        };
			    }

			    return indexOf.call(this, needle) > -1;
			};
			
			
		$scope.add = function() {
			if ($scope.contains.call(requestPackage[8], requestPackage[7].gdname))
			{
				
			} else {
				requestPackage[8].push(requestPackage[7].gdname);
			}
	
		}
		
		$scope.removeDev = function() {
			if ($scope.contains.call(requestPackage[8], requestPackage[7].gdname))
			{
				var index = requestPackage[8].indexOf(requestPackage[7].gdname);
				if (index > -1) {
					requestPackage[8].splice(index, 1);
				}
				
			} else {
				
			}
		
		}
		
		

		  $scope.ok = function () {
			  
			 
			  
			  var reqGame = {};
			  reqGame.gamename = requestPackage[4];
			  reqGame.gamedesc = requestPackage[5];
			  reqGame.gamepc = 0;
			  reqGame.gamepsec = 0;
			  reqGame.gamepmin = 0;
			  reqGame.gamephour = 0;
			  reqGame.gamepday = 0;
			  reqGame.gamepn = 0;
			  reqGame.gimg = requestPackage[3];
			  reqGame.gameDevelopers = [];
			  if (requestPackage[8].length != 0) {
				  for (var i = 0; i < requestPackage[8].length; i++) {
					  reqGame.gameDevelopers.push(requestPackage[8][i]);
					}
				} else {
					reqGame.gameDevelopers.push(requestPackage[7].gdname);
				}
			  reqGame.gamery = requestPackage[1].name;
			  reqGame.gamedr = requestPackage[6];
			  $scope.reqGame = reqGame;

			
			  $http({
					method: 'POST',
					url: '/requests/save',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: reqGame
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("RequestAddResult");
					if($scope.result =='Request successfully added'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Request successfully added'){  
						setTimeout(function(){$modalInstance.close($scope.reqGame);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("RequestAddResult");
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
	
	
	
	app.controller('RequestUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 $scope.gameYear = [
		      {id: '1', name: '2017'},
		      {id: '2', name: '2016'},
		      {id: '3', name: '2015'},
		      {id: '4', name: '2014'},
		      {id: '5', name: '2013'},
		      {id: '6', name: '2012'},
		      {id: '7', name: '2011'},
		      {id: '8', name: '2010'},
		      {id: '9', name: '2009'},
		      {id: '10', name: '2008'},
		      {id: '11', name: '2007'},
		      {id: '12', name: '2006'},
		      {id: '13', name: '2005'},
		      {id: '14', name: '2004'},
		      {id: '15', name: '2003'},
		      {id: '16', name: '2002'},
		      {id: '17', name: '2001'},
		      {id: '18', name: '2000'},
		      {id: '19', name: '1999'},
		      {id: '20', name: '1998'},
		      {id: '21', name: '1997'},
		      {id: '22', name: '1996'},
		      {id: '23', name: '1995'},
		      {id: '24', name: '1994'},
		      {id: '25', name: '1993'},
		      {id: '26', name: '1992'},
		      {id: '27', name: '1991'},
		      {id: '28', name: '1990'},
		      {id: '29', name: '1989'},
		      {id: '30', name: '1988'},
		      {id: '31', name: '1987'},
		      {id: '32', name: '1986'},
		      {id: '33', name: '1985'},
		      {id: '34', name: '1984'},
		      {id: '35', name: '1983'},
		      {id: '36', name: '1982'},
		      {id: '37', name: '1981'},
		      {id: '38', name: '1980'}
		 ]
		 $scope.selectedGameYear = {id: '1', name: '2017'};
		  
		  
		  
		  $scope.requestPackage =[];
		  $scope.requestPackage[0]=$scope.gameYear;
		  $scope.requestPackage[1]=$scope.selectedGameYear;
		  $scope.gameImage = "";
		  $scope.gameName = "";
		  $scope.gameDescription = "";
		  $scope.gameDetection = "";
		  $scope.requestPackage[3] = $scope.gameImage;
		  $scope.requestPackage[4] = $scope.gameName;
		  $scope.requestPackage[5] = $scope.gameDescription;
		  $scope.requestPackage[6] = $scope.gameDetection;
		  $scope.requestPackage[8] = [];
		  $scope.requestPackage[9]={};
		  $scope.requestPackage[10]="";
		  
		 
		  
		  $scope.requestPackage[11]=[
		      {id: '1', name: 'PENDING'},
		      {id: '2', name: 'DECLINED'},
		      {id: '3', name: 'ACCEPTED'},
		  ];
		  
		  $scope.requestPackage[12]=$scope.requestPackage[11][0];
		  
		 
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#requestUpdateGameImg').attr('src', e.target.result);
		
			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateRequest").change(function(){
				readURL(this);
			});
			
			$scope.removeImg = function() {
				$scope.requestPackage[3] = $scope.requestPackage[10];
				$('#fileUpdateRequest').trigger("input");
				$('#requestUpdateGameImg').trigger("input");
				fileread="requestPackage[3]";
	
			}
		  
			
		  $scope.open = function (id) {
			  
			  $http({
					method: 'GET',
					url: '/games/getDevelopers'
				}).then(function success(response) {
					if (response.data != null) {
						$scope.gameDevelopers = response.data;
						$scope.requestPackage[2]=$scope.gameDevelopers;
						$scope.gameSelectedDeveloper = response.data[0];
						$scope.requestPackage[7] = $scope.gameSelectedDeveloper;
					}
					
					
					$http({
						method: 'POST',
						url: '/requests/getRequest',
						headers: {
							   'Content-Type': 'text/plain'
							 },
							 data: id
					}).then(function success(response) {
						$scope.requestPackage[4]=response.data.reqgname;
						for (var j=0; j<$scope.requestPackage[0].length; j++){
							if($scope.requestPackage[0][j].name==response.data.reqgrd){
								$scope.requestPackage[1]=$scope.requestPackage[0][j];
							}
						}
						$scope.requestPackage[5]=response.data.reqdesc;
						$scope.requestPackage[6]=response.data.reqdr;
						$scope.requestPackage[3]=response.data.reqimg;
						$scope.requestPackage[10]=response.data.reqimg;
						for(var k=0; k<response.data.gameDevelopers.length; k++){
							if($scope.requestPackage[8].indexOf(response.data.gameDevelopers[k].gdname)==-1)
								$scope.requestPackage[8].push(response.data.gameDevelopers[k].gdname);
						}
						$scope.requestPackage[9] = id;
						for (var c=0; c<$scope.requestPackage[11].length; c++){
							if($scope.requestPackage[11][c].name==response.data.reqstatus){
								$scope.requestPackage[12]=$scope.requestPackage[11][c];
							}
						}
						
						var modalInstance = $modal.open({
						      templateUrl: 'updateRequestContent.html',
						      controller: ModalInstanceCtrl,
						      resolve: {
						    	  requestPackage: function () {
						          return $scope.requestPackage;
						        }
						      }
						    });

						    modalInstance.result.then(function (selectedItem) {
						      $scope.selected = selectedItem;
						    }, function () {
						  
						    });
						
					});
					
				});
			  
			  
			  

		    
		  };
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, requestPackage) {
			
			

		  $scope.gameYear = requestPackage[0];
		  $scope.selectedGameYear = requestPackage[1];
		  $scope.gameDevelopers=requestPackage[2];
		  $scope.gameImage=requestPackage[3];
		  $scope.gameName=requestPackage[4];
		  $scope.gameDescription=requestPackage[5];
		  $scope.gameDetection=requestPackage[6];
		 
		  $scope.requestPackage = requestPackage;
		  
		  
		  $scope.removeImg = function() {
				$scope.requestPackage[3] = $scope.requestPackage[10];
				$('#fileUpdateRequest').trigger("input");
				$('#requestUpdateGameImg').trigger("input");
				fileread="requestPackage[3]";
	
			}
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#requestUpdateGameImg').attr('src', e.target.result);

			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateRequest").change(function(){
				readURL(this);
			});
			
			$scope.contains = function(needle) {
			    // Per spec, the way to identify NaN is that it is not equal to itself
			    var findNaN = needle !== needle;
			    var indexOf;

			    if(!findNaN && typeof Array.prototype.indexOf === 'function') {
			        indexOf = Array.prototype.indexOf;
			    } else {
			        indexOf = function(needle) {
			            var i = -1, index = -1;

			            for(i = 0; i < this.length; i++) {
			                var item = this[i];

			                if((findNaN && item !== item) || item === needle) {
			                    index = i;
			                    break;
			                }
			            }

			            return index;
			        };
			    }

			    return indexOf.call(this, needle) > -1;
			};
			
			
		$scope.add = function() {
			if ($scope.contains.call(requestPackage[8], requestPackage[7].gdname))
			{
				
			} else {
				requestPackage[8].push(requestPackage[7].gdname);
			}
	
		}
		
		$scope.removeDev = function() {
			if ($scope.contains.call(requestPackage[8], requestPackage[7].gdname))
			{
				var index = requestPackage[8].indexOf(requestPackage[7].gdname);
				if (index > -1) {
					requestPackage[8].splice(index, 1);
				}
				
			} else {
				
			}
	
		}
		
		

		  $scope.ok = function () {
			  
			 
			  
			  var reqGame = {};
			  reqGame.gamename = requestPackage[4];
			  reqGame.gamedesc = requestPackage[5];
			  reqGame.gamepc = 0;
			  reqGame.gamepsec = 0;
			  reqGame.gamepmin = 0;
			  reqGame.gamephour = 0;
			  reqGame.gamepday = 0;
			  reqGame.gamepn = 0;
			  reqGame.gimg = requestPackage[3];
			  reqGame.gameDevelopers = [];
			  reqGame.reqid = requestPackage[9];
			  reqGame.reqstatus = requestPackage[12].name;
			  if (requestPackage[8].length != 0) {
				  for (var i = 0; i < requestPackage[8].length; i++) {
					  reqGame.gameDevelopers.push(requestPackage[8][i]);
					}
				} else {
					reqGame.gameDevelopers.push(requestPackage[7].gdname);
				}
			  reqGame.gamery = requestPackage[1].name;
			  reqGame.gamedr = requestPackage[6];
			  $scope.reqGame = reqGame;


			
			  $http({
					method: 'POST',
					url: '/requests/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: reqGame
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("RequestUpdateResult");
					if($scope.result =='Request successfully accepted' || $scope.result =='Request successfully declined' || $scope.result =='Request successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Request successfully accepted' || $scope.result =='Request successfully declined' || $scope.result =='Request successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.reqGame);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("RequestUpdateResult");
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
	
	
	
	app.controller('SpecificRequestUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 $scope.gameYear = [
		      {id: '1', name: '2017'},
		      {id: '2', name: '2016'},
		      {id: '3', name: '2015'},
		      {id: '4', name: '2014'},
		      {id: '5', name: '2013'},
		      {id: '6', name: '2012'},
		      {id: '7', name: '2011'},
		      {id: '8', name: '2010'},
		      {id: '9', name: '2009'},
		      {id: '10', name: '2008'},
		      {id: '11', name: '2007'},
		      {id: '12', name: '2006'},
		      {id: '13', name: '2005'},
		      {id: '14', name: '2004'},
		      {id: '15', name: '2003'},
		      {id: '16', name: '2002'},
		      {id: '17', name: '2001'},
		      {id: '18', name: '2000'},
		      {id: '19', name: '1999'},
		      {id: '20', name: '1998'},
		      {id: '21', name: '1997'},
		      {id: '22', name: '1996'},
		      {id: '23', name: '1995'},
		      {id: '24', name: '1994'},
		      {id: '25', name: '1993'},
		      {id: '26', name: '1992'},
		      {id: '27', name: '1991'},
		      {id: '28', name: '1990'},
		      {id: '29', name: '1989'},
		      {id: '30', name: '1988'},
		      {id: '31', name: '1987'},
		      {id: '32', name: '1986'},
		      {id: '33', name: '1985'},
		      {id: '34', name: '1984'},
		      {id: '35', name: '1983'},
		      {id: '36', name: '1982'},
		      {id: '37', name: '1981'},
		      {id: '38', name: '1980'}
		 ]
		 $scope.selectedGameYear = {id: '1', name: '2017'};
		  
		  
		  
		  $scope.requestPackage =[];
		  $scope.requestPackage[0]=$scope.gameYear;
		  $scope.requestPackage[1]=$scope.selectedGameYear;
		  $scope.gameImage = "";
		  $scope.gameName = "";
		  $scope.gameDescription = "";
		  $scope.gameDetection = "";
		  $scope.requestPackage[3] = $scope.gameImage;
		  $scope.requestPackage[4] = $scope.gameName;
		  $scope.requestPackage[5] = $scope.gameDescription;
		  $scope.requestPackage[6] = $scope.gameDetection;
		  $scope.requestPackage[8] = [];
		  $scope.requestPackage[9]={};
		  $scope.requestPackage[10]="";
		  
		 
		  
		  $scope.requestPackage[11]=[
		      {id: '1', name: 'PENDING'},
		      {id: '2', name: 'DECLINED'},
		      {id: '3', name: 'ACCEPTED'},
		  ];
		  
		  $scope.requestPackage[12]=$scope.requestPackage[11][0];
		  
		  $scope.isAccepted = function(status) {
				if(status == "ACCEPTED"){
					return true;
				} else {
					return false;
				}
				
			}
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#requestUpdateGameImg').attr('src', e.target.result);
		
			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateRequest").change(function(){
				readURL(this);
			});
			
			$scope.removeImg = function() {
				$scope.requestPackage[3] = $scope.requestPackage[10];
				$('#fileUpdateRequest').trigger("input");
				$('#requestUpdateGameImg').trigger("input");
				fileread="requestPackage[3]";
	
			}
		  
			
		  $scope.open = function (id) {
			  
			  $http({
					method: 'GET',
					url: '/games/getDevelopers'
				}).then(function success(response) {
					if (response.data != null) {
						$scope.gameDevelopers = response.data;
						$scope.requestPackage[2]=$scope.gameDevelopers;
						$scope.gameSelectedDeveloper = response.data[0];
						$scope.requestPackage[7] = $scope.gameSelectedDeveloper;
					}
					
					
					$http({
						method: 'POST',
						url: '/requests/getRequest',
						headers: {
							   'Content-Type': 'text/plain'
							 },
							 data: id
					}).then(function success(response) {
						$scope.requestPackage[4]=response.data.reqgname;
						for (var j=0; j<$scope.requestPackage[0].length; j++){
							if($scope.requestPackage[0][j].name==response.data.reqgrd){
								$scope.requestPackage[1]=$scope.requestPackage[0][j];
							}
						}
						$scope.requestPackage[5]=response.data.reqdesc;
						$scope.requestPackage[6]=response.data.reqdr;
						$scope.requestPackage[3]=response.data.reqimg;
						$scope.requestPackage[10]=response.data.reqimg;
						for(var k=0; k<response.data.gameDevelopers.length; k++){
							if($scope.requestPackage[8].indexOf(response.data.gameDevelopers[k].gdname)==-1)
								$scope.requestPackage[8].push(response.data.gameDevelopers[k].gdname);
						}
						$scope.requestPackage[9] = id;
						for (var c=0; c<$scope.requestPackage[11].length; c++){
							if($scope.requestPackage[11][c].name==response.data.reqstatus){
								$scope.requestPackage[12]=$scope.requestPackage[11][c];
							}
						}
						
						var modalInstance = $modal.open({
						      templateUrl: 'updateRequestContent.html',
						      controller: ModalInstanceCtrl,
						      resolve: {
						    	  requestPackage: function () {
						          return $scope.requestPackage;
						        }
						      }
						    });

						    modalInstance.result.then(function (selectedItem) {
						      $scope.selected = selectedItem;
						    }, function () {
						  
						    });
						
					});
					
				});
			  
			  
			  

		    
		  };
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, requestPackage) {
			
			

		  $scope.gameYear = requestPackage[0];
		  $scope.selectedGameYear = requestPackage[1];
		  $scope.gameDevelopers=requestPackage[2];
		  $scope.gameImage=requestPackage[3];
		  $scope.gameName=requestPackage[4];
		  $scope.gameDescription=requestPackage[5];
		  $scope.gameDetection=requestPackage[6];
		 
		  $scope.requestPackage = requestPackage;
		  
		  
		  $scope.removeImg = function() {
				$scope.requestPackage[3] = $scope.requestPackage[10];
				$('#fileUpdateRequest').trigger("input");
				$('#requestUpdateGameImg').trigger("input");
				fileread="requestPackage[3]";
	
			}
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#requestUpdateGameImg').attr('src', e.target.result);

			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateRequest").change(function(){
				readURL(this);
			});
			
			$scope.contains = function(needle) {
			    // Per spec, the way to identify NaN is that it is not equal to itself
			    var findNaN = needle !== needle;
			    var indexOf;

			    if(!findNaN && typeof Array.prototype.indexOf === 'function') {
			        indexOf = Array.prototype.indexOf;
			    } else {
			        indexOf = function(needle) {
			            var i = -1, index = -1;

			            for(i = 0; i < this.length; i++) {
			                var item = this[i];

			                if((findNaN && item !== item) || item === needle) {
			                    index = i;
			                    break;
			                }
			            }

			            return index;
			        };
			    }

			    return indexOf.call(this, needle) > -1;
			};
			
			
		$scope.add = function() {
			if ($scope.contains.call(requestPackage[8], requestPackage[7].gdname))
			{
				
			} else {
				requestPackage[8].push(requestPackage[7].gdname);
			}
	
		}
		
		$scope.removeDev = function() {
			if ($scope.contains.call(requestPackage[8], requestPackage[7].gdname))
			{
				var index = requestPackage[8].indexOf(requestPackage[7].gdname);
				if (index > -1) {
					requestPackage[8].splice(index, 1);
				}
				
			} else {
				
			}
	
		}
		
		

		  $scope.ok = function () {
			  
			 
			  
			  var reqGame = {};
			  reqGame.gamename = requestPackage[4];
			  reqGame.gamedesc = requestPackage[5];
			  reqGame.gamepc = 0;
			  reqGame.gamepsec = 0;
			  reqGame.gamepmin = 0;
			  reqGame.gamephour = 0;
			  reqGame.gamepday = 0;
			  reqGame.gamepn = 0;
			  reqGame.gimg = requestPackage[3];
			  reqGame.gameDevelopers = [];
			  reqGame.reqid = requestPackage[9];
			  reqGame.reqstatus = requestPackage[12].name;
			  if (requestPackage[8].length != 0) {
				  for (var i = 0; i < requestPackage[8].length; i++) {
					  reqGame.gameDevelopers.push(requestPackage[8][i]);
					}
				} else {
					reqGame.gameDevelopers.push(requestPackage[7].gdname);
				}
			  reqGame.gamery = requestPackage[1].name;
			  reqGame.gamedr = requestPackage[6];
			  $scope.reqGame = reqGame;


			
			  $http({
					method: 'POST',
					url: '/requests/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: reqGame
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("RequestUpdateResult");
					if($scope.result =='Request successfully accepted' || $scope.result =='Request successfully declined' || $scope.result =='Request successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Request successfully accepted' || $scope.result =='Request successfully declined' || $scope.result =='Request successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.reqGame);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("RequestUpdateResult");
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