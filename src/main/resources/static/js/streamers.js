(function() {
	var app = angular.module('streamersJS', []);
	
	
	
	
	
	app.controller("StreamerController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.streamers = [];
		control.streamersPerPage = 25;
		control.gap = 10;
		control.currentPage = 0;
		control.display = [];
		control.filters = [
		      {id: '1', name: 'strname', label: 'Streamer Name'}
		    ];
		control.flt_selected = {id: '1', name: 'strname', label: 'Streamer Name'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {

				$http({
					method: 'POST',
					url: '/streamers/filterName',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.streamers = response.data;
						control.groupToPages();

				});
			 
		}
		
		control.removeStreamer = function(id) {
			
			
			$http({
				method: 'POST',
				url: '/streamers/removeStreamer',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: id
			}).then(function success(response) {
				control.loadStreamers();
				toastr["success"]('Streamer '+response.data.strname+' successfully deleted!', "Success!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please make sure the desired entry has no relation to other entries in the database.', "Error!");
			  });
		}
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.streamers.length; i++) {
	            if (i % control.streamersPerPage === 0) {
	            	control.display[Math.floor(i / control.streamersPerPage)] = [ control.streamers[i] ];
	            } else {
	            	control.display[Math.floor(i / control.streamersPerPage)].push(control.streamers[i]);
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
			control.flt_selected = {id: '1', name: 'strname', label: 'Streamer Name'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/streamers/getStreamers',
			}).then(function success(response) {

					control.streamers = response.data;
					control.groupToPages();
					control.currentPage = 0;

			});
		}
		
		
		control.loadStreamers = function() {

			$http({
				method: 'GET',
				url: '/streamers/getStreamers',
			}).then(function success(response) {

					control.streamers = response.data;
					control.groupToPages();


			}, function errorCallback(response) {
				console.log(response.data);

			  });
		}
		/*
		if(control.streamers.length < 1){
			control.loadStreamers();

		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="11"){
				control.flt_selected = {id: '1', name: 'strname', label: 'Streamer Name'};
				control.loadStreamers();
				control.currentPage = 0;
				control.filter = "";
			}
			
		});
		
		
	}]);
	
	
	
	app.controller('StreamerAddController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 
		  
		  $scope.streamerGames=[];

		  $scope.streamerPackage =[];
		  $scope.streamerPackage[0]="";
		  $scope.streamerPackage[1]="";
		  $scope.streamerPackage[2]={};
		  $scope.streamerPackage[3]= {};
		  $scope.streamerPackage[4]=[];
		  $scope.streamerPackage[5]=[];
		  $scope.streamerArray=[];
		  
		  
		
		

		  $scope.open = function (size) {
			  
			  
			  $http({
					method: 'GET',
					url: '/games/getGamesCompact'
				}).then(function success(response) {
					if (response.data != null) {
						$scope.streamerGames = response.data;
						$scope.selectedStreamerGame = $scope.streamerGames[0];
						for(var i=0;i<response.data.length;i++) { 
							$scope.streamerGames[i].gamename=$scope.streamerGames[i].gamename+" ("+$scope.streamerGames[i].gamery+")";

						}
						$scope.streamerPackage[3]=$scope.streamerGames;
						$scope.streamerPackage[2]=$scope.streamerGames[0];
					}
					
					var modalInstance = $modal.open({
					      templateUrl: 'myStreamerContent.html',
					      controller: ModalInstanceCtrl,
					      size: size,
					      resolve: {
					    	  streamerPackage: function () {
					          return $scope.streamerPackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selected = selectedItem;
					    }, function () {
					
					    });
				});
			  

		    
		  };
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, streamerPackage) {
			
		  streamerPackage[0]="";
		  streamerPackage[1] = "";
		  streamerPackage[2]=streamerPackage[3][0];
		  streamerPackage[4]=[];
		  streamerPackage[5]=[];
		  $scope.streamerPackage = streamerPackage;
		  
		
		  
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
			if ($scope.contains.call(streamerPackage[4], streamerPackage[2].gameid))
			{
				
			} else {
				streamerPackage[4].push(streamerPackage[2].gameid);
				streamerPackage[5].push(streamerPackage[2].gamename);
			}
	
		}
		
		$scope.removeGame = function() {
			if ($scope.contains.call(streamerPackage[4], streamerPackage[2].gameid))
			{
				var index = streamerPackage[4].indexOf(streamerPackage[2].gameid);
				if (index > -1) {
					streamerPackage[4].splice(index, 1);
					streamerPackage[5].splice(index, 1);
				}
				
			} else {
				
			}
		
		}
		  
		  
		

		  $scope.ok = function () {
			  
			 
			  
			  var streamer = {};
			  streamer.strname = streamerPackage[0];
			  streamer.strurl = streamerPackage[1];
			  streamer.games = [];
			  if (streamerPackage[4].length != 0) {
				  for (var i = 0; i < streamerPackage[4].length; i++) {
					  streamer.games.push(streamerPackage[4][i]);
					}
				} else {
			  streamer.games.push(streamerPackage[2].gameid);
				}
			 
			  $scope.streamer = streamer;

			
			  $http({
					method: 'POST',
					url: '/streamers/save',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: streamer
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("StreamerAddResult");
					if($scope.result =='Streamer successfully added'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Streamer successfully added'){  
						setTimeout(function(){$modalInstance.close($scope.streamer);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("StreamerAddResult");
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
	
	
	
	

	
	
	
	app.controller('StreamerUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 
		  
		  $scope.streamerGames=[];

		  $scope.streamerPackage =[];
		  $scope.streamerPackage[0]="";
		  $scope.streamerPackage[1]="";
		  $scope.streamerPackage[2]={};
		  $scope.streamerPackage[3]= {};
		  $scope.streamerPackage[4]=[];
		  $scope.streamerPackage[5]=[];
		  $scope.streamerArray=[];
		  $scope.streamerPackage[6]={};
		  
		 
		
		

		  $scope.open = function (id) {
			  
			  $http({
					method: 'GET',
					url: '/games/getGamesCompact'
				}).then(function success(response) {
					if (response.data != null) {
						$scope.streamerGames = response.data;
						$scope.selectedStreamerGame = $scope.streamerGames[0];
						for(var i=0;i<response.data.length;i++) { 
							$scope.streamerGames[i].gamename=$scope.streamerGames[i].gamename+" ("+$scope.streamerGames[i].gamery+")";

						}
						$scope.streamerPackage[3]=$scope.streamerGames;
						$scope.streamerPackage[2]=$scope.streamerGames[0];
					}
					
					
					$scope.streamerPackage[6]=id;
					  $http({
							method: 'POST',
							url: '/streamers/getStreamer',
							headers: {
								   'Content-Type': 'text/plain'
								 },
								 data: id
						}).then(function success(response) {
							$scope.streamerPackage[0]=response.data.strname;
							$scope.streamerPackage[1]=response.data.strurl;
							
						
						});
					  
					  $http({
							method: 'POST',
							url: '/games/getGamesByStreamer',
							headers: {
								   'Content-Type': 'text/plain'
								 },
								 data: id
						}).then(function success(response) {
							
							var gamestreams = response.data;
							for(var i=0; i<gamestreams.length; i++){
								gamestreams[i].gamename=gamestreams[i].gamename+" ("+gamestreams[i].gamery+")";
								
							}

							for (var j=0; j<$scope.streamerPackage[3].length; j++){
								for(var k=0; k<gamestreams.length; k++){

									if(gamestreams[k].gamename == $scope.streamerPackage[3][j].gamename){
										$scope.streamerPackage[4].push($scope.streamerPackage[3][j].gameid);
										$scope.streamerPackage[5].push($scope.streamerPackage[3][j].gamename);
									}
								}
							}

							 var modalInstance = $modal.open({
							      templateUrl: 'updateStreamerContent.html',
							      controller: ModalInstanceCtrl,
							      resolve: {
							    	  streamerPackage: function () {
							          return $scope.streamerPackage;
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
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, streamerPackage) {
			
		 
		  streamerPackage[2]=streamerPackage[3][0];
		  $scope.streamerPackage = streamerPackage;
		  
		
		  
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
			if ($scope.contains.call(streamerPackage[4], streamerPackage[2].gameid))
			{
				
			} else {
				streamerPackage[4].push(streamerPackage[2].gameid);
				streamerPackage[5].push(streamerPackage[2].gamename);
			}

		}
		
		$scope.removeGame = function() {
			if ($scope.contains.call(streamerPackage[4], streamerPackage[2].gameid))
			{
				var index = streamerPackage[4].indexOf(streamerPackage[2].gameid);
				if (index > -1) {
					streamerPackage[4].splice(index, 1);
					streamerPackage[5].splice(index, 1);
				}
				
			} else {
				
			}

		}
		  
		  
		

		  $scope.ok = function () {
			  
			 
			  
			  var streamer = {};
			  streamer.strname = streamerPackage[0];
			  streamer.strurl = streamerPackage[1];
			  streamer.games = [];
			  streamer.strid = streamerPackage[6];
			  if (streamerPackage[4].length != 0) {
				  for (var i = 0; i < streamerPackage[4].length; i++) {
					  streamer.games.push(streamerPackage[4][i]);
					}
				} else {
			  streamer.games.push(streamerPackage[2].gameid);
				}
			 
			  $scope.streamer = streamer;

			
			  $http({
					method: 'POST',
					url: '/streamers/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: streamer
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("StreamerUpdateResult");
					if($scope.result =='Streamer successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Streamer successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.streamer);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("StreamerUpdateResult");
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