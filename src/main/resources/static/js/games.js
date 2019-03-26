(function() {
	var app = angular.module('gamesJS', []);
	
	
	app.controller("GameController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.games = [];
		control.gap = 10;
		control.players = [];
		control.reviews = [];
		control.streamers = [];
		control.guides = [];
		control.itemsPerPage = 20;
		control.currentPage = 0;
		control.display = [];
		control.selectedGameDev = "";
		control.selectedUser = "";
		control.playedGames = [];
		control.friends = [];
		control.groups = [];
		control.letters = ['#','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
		control.currentLetter = '1';
		control.filters = [
		      {id: '1', name: 'gamename', label: 'Game Name'},
		      {id: '2', name: 'gamery', label: 'Release Year'}
		    ];
		//$window.location.href = '/login.html';
		control.flt_selected = {id: '1', name: 'gamename', label: 'Game Name'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/games/filterName',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						
						control.games = response.data;
						control.groupToPages();
						control.currentPage = 0;

				});
			} else if (control.flt_selected.id == '2') {
				if(isNaN(control.filter)){
					
				} else {
					$http({
						method: 'POST',
						url: '/games/filterYear',
						headers: {
							'Content-Type': 'text/plain'   
							 },
						data: control.filter
					}).then(function success(response) {
							control.currentPage = 0;
							control.games = response.data;
							control.groupToPages();
	
					});
				}
			}
			control.currentLetter = '1';
		}
		
		
		
	
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.games.length; i++) {
	            if (i % control.itemsPerPage === 0) {
	            	control.display[Math.floor(i / control.itemsPerPage)] = [ control.games[i] ];
	            } else {
	            	control.display[Math.floor(i / control.itemsPerPage)].push(control.games[i]);
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
	    
	    control.selectLetter = function (test) {
	    	control.currentLetter = test;
	    	control.currentPage = 0;
	    	$http({
				method: 'POST',
				url: '/games/filterStarting',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: test
			}).then(function success(response) {

					control.games = response.data;
					control.groupToPages();

			});
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
			
			control.flt_selected = {id: '1', name: 'gamename', label: 'Game Name'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/games/getGames',
			}).then(function success(response) {

					control.games = response.data;
					control.currentLetter = '1';
					control.currentPage = 0;
					control.groupToPages();
			});
			
		}
		
		control.removeGame = function(id) {
			
			$http({
				method: 'POST',
				url: '/games/removeGame',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: id
			}).then(function success(response) {
				control.loadGames();
				toastr["success"]('Game ' + response.data.gamename + ' (' + response.data.gamery+') successfully deleted!', "Success!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please make sure the desired game has no items from it on sale in the Market.', "Error!");
			  });
		}
		
		control.loadGames = function() {
			
			$http({
				method: 'GET',
				url: '/games/getGames',
			}).then(function success(response) {
					control.currentLetter = '1';
					control.games = response.data;
					control.groupToPages();

			});
		}
		/*
		if(control.games.length < 1){
			control.loadGames();
		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="0"){
				control.flt_selected = {id: '1', name: 'gamename', label: 'Game Name'};
				control.loadGames();
				control.currentPage = 0;
				control.currentLetter = '1';
				control.filter = "";
			}
			
		});
		
	}]);
	
	
	
	
	app.controller('GameAddController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
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
		  
		 
		  
		  
		  $scope.gamePackage =[];
		  $scope.gamePackage[0]=$scope.gameYear;
		  $scope.gamePackage[1]=$scope.selectedGameYear;
		  $scope.gameImage = "";
		  $scope.gameName = "";
		  $scope.gameDescription = "";
		  $scope.gameDetection = "";
		  $scope.gamePackage[3] = $scope.gameImage;
		  $scope.gamePackage[4] = $scope.gameName;
		  $scope.gamePackage[5] = $scope.gameDescription;
		  $scope.gamePackage[6] = $scope.gameDetection;
		  $scope.gamePackage[8] = [];
		  
		 
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#gameImg').attr('src', e.target.result);
		
			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileGame").change(function(){
				readURL(this);
			});
			
			$scope.removeImg = function() {
				$('#gameImg').attr('src', "");
				$('#gameImg').attr('value', "");
				$('#fileGame').attr('value', "");
				$('#fileGame').trigger("input");
				$('#gameImg').trigger("input");
				gamePackage[3]="";

				fileread="gamePackage[3]";
			}
		  

		  $scope.open = function (size) {
			  
			  $http({
					method: 'GET',
					url: '/games/getDevelopers'
				}).then(function success(response) {
					if (response.data != null) {
						$scope.gameDevelopers = response.data;
						$scope.gamePackage[2]=$scope.gameDevelopers;
						$scope.gameSelectedDeveloper = response.data[0];
						$scope.gamePackage[7] = $scope.gameSelectedDeveloper;
					}
					var modalInstance = $modal.open({
					      templateUrl: 'myGameContent.html',
					      controller: ModalInstanceCtrl,
					      size: size,
					      resolve: {
					    	  gamePackage: function () {
					          return $scope.gamePackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selected = selectedItem;
					    }, function () {

					    });
				});
			  
			  var textAreas = document.getElementsByTagName('textarea');

				 Array.prototype.forEach.call(textAreas, function(elem) {
				     elem.placeholder = elem.placeholder.replace(/\\n/g, '\n');
				 });
			  

		    
		  };
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, gamePackage) {
			
			

		  $scope.gameYear = gamePackage[0];
		  $scope.selectedGameYear = gamePackage[1];
		  $scope.gameDevelopers=gamePackage[2];
		  $scope.gameImage=gamePackage[3];
		  $scope.gameName=gamePackage[4];
		  $scope.gameDescription=gamePackage[5];
		  $scope.gameDetection=gamePackage[6];
		  gamePackage[4]="";
	      gamePackage[1] = {id: '1', name: '2017'};
	      gamePackage[5]= "";
	      gamePackage[7]= gamePackage[2][0];
	      gamePackage[8]=[];
	      gamePackage[6]="";
	      gamePackage[3]="";
		  $scope.gamePackage = gamePackage;
		  
		  $scope.removeImg = function() {
			  $('#gameImg').attr('src', "");
			  $('#gameImg').attr('value', "");
			  $('#fileGame').attr('value', "");
			  $('#fileGame').trigger("input");
				$('#gameImg').trigger("input");
				gamePackage[3]="";

				fileread="gamePackage[3]";
			}
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#gameImg').attr('src', e.target.result);

			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileGame").change(function(){
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
			if ($scope.contains.call(gamePackage[8], gamePackage[7].gdname))
			{
				
			} else {
				gamePackage[8].push(gamePackage[7].gdname);
			}

		}
		
		$scope.removeDev = function() {
			if ($scope.contains.call(gamePackage[8], gamePackage[7].gdname))
			{
				var index = gamePackage[8].indexOf(gamePackage[7].gdname);
				if (index > -1) {
					gamePackage[8].splice(index, 1);
				}
				
			} else {
				
			}

		}
		
		

		  $scope.ok = function () {
			  
			 
			  
			  var game = {};
			  game.gamename = gamePackage[4];
			  game.gamedesc = gamePackage[5];
			  game.gamepc = 0;
			  game.gamepsec = 0;
			  game.gamepmin = 0;
			  game.gamephour = 0;
			  game.gamepday = 0;
			  game.gamepn = 0;
			  game.gimg = gamePackage[3];
			  game.gameDevelopers = [];
			  if (gamePackage[8].length != 0) {
				  for (var i = 0; i < gamePackage[8].length; i++) {
					  game.gameDevelopers.push(gamePackage[8][i]);
					}
				} else {
			  game.gameDevelopers.push(gamePackage[7].gdname);
				}
			  game.gamery = gamePackage[1].name;
			  game.gamedr = gamePackage[6];
			  $scope.game = game;
			
			  $http({
					method: 'POST',
					url: '/games/save',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: game
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("GameAddResult");
					if($scope.result =='Game successfully added'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					
					if($scope.result =='Game successfully added'){  
						setTimeout(function(){$modalInstance.close($scope.game);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("GameAddResult");
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
	
	
	
	
	
	
	app.controller('GameUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
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
		  
		  
		  
		  $scope.gamePackage =[];
		  $scope.gamePackage[0]=$scope.gameYear;
		  $scope.gamePackage[1]=$scope.selectedGameYear;
		  $scope.gameImage = "";
		  $scope.gameName = "";
		  $scope.gameDescription = "";
		  $scope.gameDetection = "";
		  $scope.gamePackage[3] = $scope.gameImage;
		  $scope.gamePackage[4] = $scope.gameName;
		  $scope.gamePackage[5] = $scope.gameDescription;
		  $scope.gamePackage[6] = $scope.gameDetection;
		  $scope.gamePackage[8] = [];
		  $scope.gamePackage[9] = {};
		  $scope.gamePackage[10]="";
		  
		  
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#gameUpdateImg').attr('src', e.target.result);
		
			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateGame").change(function(){
				readURL(this);
			});
			
			
			$scope.removeImg = function() {
				$scope.gamePackage[3] = $scope.gamePackage[10];
				$('#fileUpdateGame').trigger("input");
				$('#gameUpdateImg').trigger("input");
				fileread="gamePackage[3]";
	
			}
		  

		  $scope.open = function (id) {
			  
			  $http({
					method: 'GET',
					url: '/games/getDevelopers'
				}).then(function success(response) {
					if (response.data != null) {
						$scope.gameDevelopers = response.data;
						$scope.gamePackage[2]=$scope.gameDevelopers;
						$scope.gameSelectedDeveloper = response.data[0];
						$scope.gamePackage[7] = $scope.gameSelectedDeveloper;
					}
					
					$http({
						method: 'POST',
						url: '/games/getGame',
						headers: {
							   'Content-Type': 'text/plain'
							 },
							 data: id
					}).then(function success(response) {
						$scope.gamePackage[4]=response.data.gamename;
						for (var j=0; j<$scope.gamePackage[0].length; j++){
							if($scope.gamePackage[0][j].name==response.data.gamery){
								$scope.gamePackage[1]=$scope.gamePackage[0][j];
							}
						}
						$scope.gamePackage[5]=response.data.gamedesc;
						$scope.gamePackage[6]=response.data.gamedr;
						$scope.gamePackage[3]=response.data.gimg;
						$scope.gamePackage[10]=response.data.gimg;
						for(var k=0; k<response.data.gameDevelopers.length; k++){
							if($scope.gamePackage[8].indexOf(response.data.gameDevelopers[k].gdname)==-1)
								$scope.gamePackage[8].push(response.data.gameDevelopers[k].gdname);
						}
						$scope.gamePackage[9] = id;
						
						var modalInstance = $modal.open({
						      templateUrl: 'updateGameContent.html',
						      controller: ModalInstanceCtrl,
						      resolve: {
						    	  gamePackage: function () {
						          return $scope.gamePackage;
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
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, gamePackage) {
			
			

		  $scope.gameYear = gamePackage[0];
		  $scope.selectedGameYear = gamePackage[1];
		  $scope.gameDevelopers=gamePackage[2];
		  $scope.gameImage=gamePackage[3];
		  $scope.gameName=gamePackage[4];
		  $scope.gameDescription=gamePackage[5];
		  $scope.gameDetection=gamePackage[6];
		  $scope.gamePackage = gamePackage;
		  
		  $scope.removeImg = function() {
				$scope.gamePackage[3] = $scope.gamePackage[10];
				$('#fileUpdateGame').trigger("input");
				$('#gameUpdateImg').trigger("input");
				fileread="gamePackage[3]";
	
			}
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#gameUpdateImg').attr('src', e.target.result);

			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateGame").change(function(){
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
			if ($scope.contains.call(gamePackage[8], gamePackage[7].gdname))
			{
				
			} else {
				gamePackage[8].push(gamePackage[7].gdname);
			}
		
		}
		
		$scope.removeDev = function() {
			if ($scope.contains.call(gamePackage[8], gamePackage[7].gdname))
			{
				var index = gamePackage[8].indexOf(gamePackage[7].gdname);
				if (index > -1) {
					gamePackage[8].splice(index, 1);
				}
				
			} else {
				
			}
	
		}
		
		

		  $scope.ok = function () {
			  
			 
			  
			  var game = {};
			  game.gamename = gamePackage[4];
			  game.gamedesc = gamePackage[5];
			  game.gamepc = 0;
			  game.gamepsec = 0;
			  game.gamepmin = 0;
			  game.gamephour = 0;
			  game.gamepday = 0;
			  game.gamepn = 0;
			  game.gimg = gamePackage[3];
			  
	

			  game.gameDevelopers = [];
			  game.gameid = gamePackage[9];
			  if (gamePackage[8].length != 0) {
				  for (var i = 0; i < gamePackage[8].length; i++) {
					  game.gameDevelopers.push(gamePackage[8][i]);
					}
				} else {
			  game.gameDevelopers.push(gamePackage[7].gdname);
				}
			  game.gamery = gamePackage[1].name;
			  game.gamedr = gamePackage[6];
			  $scope.game = game;

			
			  $http({
					method: 'POST',
					url: '/games/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: game
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("GameUpdateResult");
					if($scope.result =='Game information successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Game information successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.game);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("GameUpdateResult");
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
	
	
	app.controller('SpecificGameUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
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
		  
		  
		  
		  $scope.gamePackage =[];
		  $scope.gamePackage[0]=$scope.gameYear;
		  $scope.gamePackage[1]=$scope.selectedGameYear;
		  $scope.gameImage = "";
		  $scope.gameName = "";
		  $scope.gameDescription = "";
		  $scope.gameDetection = "";
		  $scope.gamePackage[3] = $scope.gameImage;
		  $scope.gamePackage[4] = $scope.gameName;
		  $scope.gamePackage[5] = $scope.gameDescription;
		  $scope.gamePackage[6] = $scope.gameDetection;
		  $scope.gamePackage[8] = [];
		  $scope.gamePackage[9] = {};
		  $scope.gamePackage[10]="";
		  
	
		 
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#gameImg').attr('src', e.target.result);
		
			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileGame").change(function(){
				readURL(this);
			});
			
			$scope.removeImg = function() {
				$scope.gamePackage[3] = $scope.gamePackage[10];
				$('#fileUpdateGame').trigger("input");
				$('#gameUpdateImg').trigger("input");
				fileread="gamePackage[3]";
	
			}
		  

		  $scope.open = function (id) {
			  
			  
			  $http({
					method: 'GET',
					url: '/games/getDevelopers'
				}).then(function success(response) {
					if (response.data != null) {
						$scope.gameDevelopers = response.data;
						$scope.gamePackage[2]=$scope.gameDevelopers;
						$scope.gameSelectedDeveloper = response.data[0];
						$scope.gamePackage[7] = $scope.gameSelectedDeveloper;
					}
					
					
					 $http({
							method: 'POST',
							url: '/games/getGame',
							headers: {
								   'Content-Type': 'text/plain'
								 },
								 data: id
						}).then(function success(response) {
							$scope.gamePackage[4]=response.data.gamename;
							for (var j=0; j<$scope.gamePackage[0].length; j++){
								if($scope.gamePackage[0][j].name==response.data.gamery){
									$scope.gamePackage[1]=$scope.gamePackage[0][j];
								}
							}
							$scope.gamePackage[5]=response.data.gamedesc;
							$scope.gamePackage[6]=response.data.gamedr;
							$scope.gamePackage[3]=response.data.gimg;
							$scope.gamePackage[10]=response.data.gimg;
							for(var k=0; k<response.data.gameDevelopers.length; k++){
								if($scope.gamePackage[8].indexOf(response.data.gameDevelopers[k].gdname)==-1)
									$scope.gamePackage[8].push(response.data.gameDevelopers[k].gdname);
							}
							$scope.gamePackage[9] = id;
							
							
							  var modalInstance = $modal.open({
							      templateUrl: 'updateGameContent.html',
							      controller: ModalInstanceCtrl,
							      resolve: {
							    	  gamePackage: function () {
							          return $scope.gamePackage;
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
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, gamePackage) {
			
			

		  $scope.gameYear = gamePackage[0];
		  $scope.selectedGameYear = gamePackage[1];
		  $scope.gameDevelopers=gamePackage[2];
		  $scope.gameImage=gamePackage[3];
		  $scope.gameName=gamePackage[4];
		  $scope.gameDescription=gamePackage[5];
		  $scope.gameDetection=gamePackage[6];
		  $scope.gamePackage = gamePackage;
		  
		  $scope.removeImg = function() {
				$scope.gamePackage[3] = $scope.gamePackage[10];

				$('#fileUpdateGame').trigger("input");
				$('#gameUpdateImg').trigger("input");
				fileread="gamePackage[3]";
	
			}
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#gameUpdateImg').attr('src', e.target.result);

			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateGame").change(function(){
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
			if ($scope.contains.call(gamePackage[8], gamePackage[7].gdname))
			{
				
			} else {
				gamePackage[8].push(gamePackage[7].gdname);
			}
		
		}
		
		$scope.removeDev = function() {
			if ($scope.contains.call(gamePackage[8], gamePackage[7].gdname))
			{
				var index = gamePackage[8].indexOf(gamePackage[7].gdname);
				if (index > -1) {
					gamePackage[8].splice(index, 1);
				}
				
			} else {
				
			}
	
		}
		
		

		  $scope.ok = function () {
			  
			 
			  
			  var game = {};
			  game.gamename = gamePackage[4];
			  game.gamedesc = gamePackage[5];
			  game.gamepc = 0;
			  game.gamepsec = 0;
			  game.gamepmin = 0;
			  game.gamephour = 0;
			  game.gamepday = 0;
			  game.gamepn = 0;
			  game.gimg = gamePackage[3];
			 
			  game.gameDevelopers = [];
			  game.gameid = gamePackage[9];
			  if (gamePackage[8].length != 0) {
				  for (var i = 0; i < gamePackage[8].length; i++) {
					  game.gameDevelopers.push(gamePackage[8][i]);
					}
				} else {
			  game.gameDevelopers.push(gamePackage[7].gdname);
				}
			  game.gamery = gamePackage[1].name;
			  game.gamedr = gamePackage[6];
			  $scope.game = game;
			  
			
			  $http({
					method: 'POST',
					url: '/games/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: game
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("GameUpdateResult");
					if($scope.result =='Game information successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Game information successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.game);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("GameUpdateResult");
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