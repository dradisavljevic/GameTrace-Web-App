(function() {
	var app = angular.module('index', ['ui.bootstrap', 'ui.router', 'gamesJS', 'requestsJS', 'usersJS', 'groupsJS', 'guidesJS', 'streamersJS', 'developersJS', 'itemsJS', 'profileJS', 'ticketsJS', 'reviewsJS']).directive("fileread", [function () {
	    return {
	        scope: {
	            fileread: "="
	        },
	        link: function (scope, element, attributes) {
	            element.on("change submit input", function (changeEvent) {
	                var reader = new FileReader();
	                reader.onload = function (loadEvent) {
	                    scope.$apply(function () {
	                        scope.fileread = loadEvent.target.result;
	                       
	                    });
	                }
	                reader.readAsDataURL(changeEvent.target.files[0]);
	            });
	            element.on("click", function (changeEvent) {
	            	$(this).val('');
	            });
	        }
	    }
	}]);
	
	angular.module('index').directive('ngReallyClick', [function() {
	    return {
	        restrict: 'A',
	        link: function(scope, element, attrs) {
	            element.bind('click', function() {
	                var message = attrs.ngReallyMessage;
	                if (message && confirm(message)) {
	                    scope.$apply(attrs.ngReallyClick);
	                }
	            });
	        }
	    }
	}]);
	
	
	app.config(function($stateProvider) {
		
		$stateProvider
	    .state('games', {
	        url: "/games/:gamesId"
	    })
	    $stateProvider.state('root', {
	        url: "/:rootPath"
	    })
	    $stateProvider.state('users', {
	        url: "/users/:username"
	    })
	    $stateProvider.state('guides', {
	        url: "/guides/:guideGameId/:guideUsername/:guideId"
	    })
	    $stateProvider.state('developers', {
	        url: "/developers/:developerId"
	    })
	    $stateProvider.state('groups', {
	        url: "/groups/:groupId/:groupGM"
	    })
	    $stateProvider.state('items', {
	        url: "/items/:itemId/:itemSell"
	    })
	    $stateProvider.state('requests', {
	        url: "/requests/:reqId"
	    })
	    $stateProvider.state('tickets', {
	        url: "/tickets/:ticketId"
	    })
	});
	
	app.run(function($rootScope, $http,$location) {
		$rootScope.tab = 1;
		$rootScope.activeType = "1";
		$rootScope.active = null;
		$rootScope.GMGroups = [];
		$rootScope.inviteDisable = false;
		$rootScope.showRecommend=false;
		$rootScope.requestSent=false;
		
		$rootScope.isSet = function(checkTab) {
	      return $rootScope.tab === checkTab;
	    };
	    
	    $rootScope.isFriend = function(){
	    	if ($rootScope.friends == null){
	    		return false;
	    	} else {
	    		if($rootScope.active == null){
	    			return false
	    		}
	    		var notFound = true;
	    		for(var i = 0; i < $rootScope.friends.length; i++) {
	    		    if ($rootScope.friends[i].uname == $rootScope.active.uname) {
	    		    	notFound = false;
	    		        break;
	    		    }
	    		}
	    		return notFound;
	    	}
	    }

	    $rootScope.setTab = function(setTab) {
	    	$rootScope.tab = setTab;
	    };
	    
	    $rootScope.isGameUser = function(){
			return $rootScope.activeType == "1";
		}
	    
	    $rootScope.isAdmin = function(){
			return $rootScope.activeType == "0";
		}
	    
	    $rootScope.isAuthor = function(name){
	    	if($rootScope.active!=null){
	    		return $rootScope.active.uname == name;
	    	}
	    }
		
		$rootScope.$on("$locationChangeStart", function(event, next, current) { 
			window.scrollTo(0,0);
			var putanja = $location.path();
			var deljeno = putanja.split('/');

			if(deljeno.length==3){

				if(deljeno[1]=="games"){
					$rootScope.setTab(9);

					$rootScope.selectGame(parseInt(deljeno[2]));
				} else if (deljeno[1]=="users"){
					if($rootScope.active!=null){
						if($rootScope.active.uname == deljeno[2]){
							$rootScope.setTab(6);
							$location.path(deljeno[0]+'/6')
						}
					} else {
						$rootScope.setTab(10);

						$rootScope.selectUser(deljeno[2]);
					}
				}  else if (deljeno[1]=="developers"){
					$rootScope.setTab(16);
					$rootScope.selectDeveloper(deljeno[2]);
				} else if (deljeno[1]=="requests"){
					$rootScope.setTab(19);
					$rootScope.selectRequest(deljeno[2]);
				} else if (deljeno[1]=="tickets"){
					$rootScope.setTab(22);
					$rootScope.selectTicket(deljeno[2]);
				} else {
					$rootScope.setTab(20);
				}
			} else if (deljeno.length==5){
				if (deljeno[1]=="guides"){
					$rootScope.setTab(13);
					$rootScope.selectGuide(deljeno[4],deljeno[2],deljeno[3]);
				} else {
					$rootScope.setTab(20);
				}
			} else if (deljeno.length==4){
				if (deljeno[1]=="groups"){
					$rootScope.setTab(17);
					$rootScope.selectGroup(deljeno[2],deljeno[3]);
				} else if (deljeno[1]=="items"){
					$rootScope.setTab(18);
					$rootScope.selectItem(deljeno[2],deljeno[3]);
				} else {
					$rootScope.setTab(20);
				}
			}
			
			
			
			if(deljeno.length==2){
				if($rootScope.pageArray.indexOf(parseInt(deljeno[1]))==-1){
					$rootScope.setTab(20);
				} else {
					$rootScope.setTab(parseInt(deljeno[1]));
				}
			}
			if(deljeno.length==1){
				$rootScope.setTab(1);
			}
			
	    });
		
		$rootScope.pageArray=[0,1,2,3,4,5,6,7,8,11,12,14,15,21,23,24,25,26,27,28];
		$rootScope.gap = 5;
		$rootScope.itemsPerPage = 10;
		$rootScope.itemsPerPageDevelopedGames = 18;
		$rootScope.currentPageReviews = 0;
		$rootScope.displayReviews = [];
		

		$rootScope.currentPageGroups = 0;
		$rootScope.displayGroups = [];
		
		

		$rootScope.currentPageGuides = 0;
		$rootScope.displayGuides = [];


		$rootScope.currentPageStreamers = 0;
		$rootScope.displayStreamers = [];
		
		$rootScope.currentPageDevelopedGames = 0;
		$rootScope.displayDevelopedGames = [];
		
		$rootScope.currentPageGroupMembers = 0;
		$rootScope.displayGroupMembers = [];
		
		$rootScope.groupToPagesReviews = function () {
			$rootScope.displayReviews = [];
	        
	        for (var i = 0; i < $rootScope.reviews.length; i++) {
	            if (i % $rootScope.itemsPerPage === 0) {
	            	$rootScope.displayReviews[Math.floor(i / $rootScope.itemsPerPage)] = [ $rootScope.reviews[i] ];
	            } else {
	            	$rootScope.displayReviews[Math.floor(i / $rootScope.itemsPerPage)].push($rootScope.reviews[i]);
	            }
	        }
	    };
	    
	    
	    $rootScope.range = function(size, start, end) {
	    	var ret = [];

	    	
	    	if(size < end) {
	    		end = size;
	    		start = size - $rootScope.gap;
	    	}
	    	for(var i = start; i<end; i++) {
	    		ret.push(i);
	    	}

	    return ret;
	    };
	    
	   
	    
	    
	    $rootScope.prevPageReviews = function () {
	        if ($rootScope.currentPageReviews > 0) {
	        	$rootScope.currentPageReviews--;
	        }
	    };
	    
	    $rootScope.nextPageReviews = function () {
	        if ($rootScope.currentPageReviews < $rootScope.displayReviews.length - 1) {
	        	$rootScope.currentPageReviews++;
	        }
	    };
	    
	    $rootScope.setPageReviews = function (test) {
	    	$rootScope.currentPageReviews = test;
	    };
	    
	    
	    $rootScope.groupToPagesStreamers = function () {
	    	$rootScope.displayStreamers = [];
	        
	        for (var i = 0; i < $rootScope.streamers.length; i++) {
	            if (i % $rootScope.itemsPerPage === 0) {
	            	$rootScope.displayStreamers[Math.floor(i / $rootScope.itemsPerPage)] = [ $rootScope.streamers[i] ];
	            } else {
	            	$rootScope.displayStreamers[Math.floor(i / $rootScope.itemsPerPage)].push($rootScope.streamers[i]);
	            }
	        }
	    };
	    
	    
	
	    
	    
	    $rootScope.prevPageStreamers = function () {
	        if ($rootScope.currentPageStreamers > 0) {
	        	$rootScope.currentPageStreamers--;
	        }
	    };
	    
	    $rootScope.nextPageStreamers = function () {
	        if ($rootScope.currentPageStreamers < $rootScope.displayStreamers.length - 1) {
	        	$rootScope.currentPageStreamers++;
	        }
	    };
	    
	    $rootScope.setPageStreamers = function (test) {
	    	$rootScope.currentPageStreamers = test;
	    };
	    
	    
	    $rootScope.groupToPagesGroups = function () {
	    	$rootScope.displayGroups = [];
	        
	        for (var i = 0; i < $rootScope.groups.length; i++) {
	            if (i % $rootScope.itemsPerPage === 0) {
	            	$rootScope.displayGroups[Math.floor(i / $rootScope.itemsPerPage)] = [ $rootScope.groups[i] ];
	            } else {
	            	$rootScope.displayGroups[Math.floor(i / $rootScope.itemsPerPage)].push($rootScope.groups[i]);
	            }
	        }
	    };
	    
	    
	    
	    
	    $rootScope.prevPageGroups = function () {
	        if ($rootScope.currentPageGroups > 0) {
	        	$rootScope.currentPageGroups--;
	        }
	    };
	    
	    $rootScope.nextPageGroups = function () {
	        if ($rootScope.currentPageGroups < $rootScope.displayGroups.length - 1) {
	        	$rootScope.currentPageGroups++;
	        }
	    };
	    
	    $rootScope.setPageGroups = function (test) {
	    	$rootScope.currentPageGroups = test;
	    };
	    
	    
	    $rootScope.groupToPagesGuides = function () {
	    	$rootScope.displayGuides = [];
	        
	        for (var i = 0; i < $rootScope.guides.length; i++) {
	            if (i % $rootScope.itemsPerPage === 0) {
	            	$rootScope.displayGuides[Math.floor(i / $rootScope.itemsPerPage)] = [ $rootScope.guides[i] ];
	            } else {
	            	$rootScope.displayGuides[Math.floor(i / $rootScope.itemsPerPage)].push($rootScope.guides[i]);
	            }
	        }
	    };
	    
	    
	    
	    
	    $rootScope.prevPageGuides = function () {
	        if ($rootScope.currentPageGuides > 0) {
	        	$rootScope.currentPageGuides--;
	        }
	    };
	    
	    $rootScope.nextPageGuides = function () {
	        if ($rootScope.currentPageGuides < $rootScope.displayGuides.length - 1) {
	        	$rootScope.currentPageGuides++;
	        }
	    };
	    
	    $rootScope.setPageGuides = function (test) {
	    	$rootScope.currentPageGuides = test;
	    };
	    
	    $rootScope.groupToPagesDevelopedGames = function () {
	    	$rootScope.displayDevelopedGames = [];
	        
	        for (var i = 0; i < $rootScope.developedGames.length; i++) {
	            if (i % $rootScope.itemsPerPageDevelopedGames === 0) {
	            	$rootScope.displayDevelopedGames[Math.floor(i / $rootScope.itemsPerPageDevelopedGames)] = [ $rootScope.developedGames[i] ];
	            } else {
	            	$rootScope.displayDevelopedGames[Math.floor(i / $rootScope.itemsPerPageDevelopedGames)].push($rootScope.developedGames[i]);
	            }
	        }
	    };

	    $rootScope.prevPageDevelopedGames = function () {
	        if ($rootScope.currentPageDevelopedGames > 0) {
	        	$rootScope.currentPageDevelopedGames--;
	        }
	    };
	    
	    $rootScope.nextPageDevelopedGames = function () {
	        if ($rootScope.currentPageDevelopedGames < $rootScope.displayDevelopedGames.length - 1) {
	        	$rootScope.currentPageDevelopedGames++;
	        }
	    };
	    
	    $rootScope.setPageDevelopedGames = function (test) {
	    	$rootScope.currentPageDevelopedGames = test;
	    };
	    
	    
	    
	    
	    $rootScope.groupToPagesGroupMembers = function () {
	    	$rootScope.displayGroupMembers = [];
	        
	        for (var i = 0; i < $rootScope.selectedGroup.gameUsers.length; i++) {
	            if (i % $rootScope.itemsPerPageDevelopedGames === 0) {
	            	$rootScope.displayGroupMembers[Math.floor(i / $rootScope.itemsPerPageDevelopedGames)] = [ $rootScope.selectedGroup.gameUsers[i] ];
	            } else {
	            	$rootScope.displayGroupMembers[Math.floor(i / $rootScope.itemsPerPageDevelopedGames)].push($rootScope.selectedGroup.gameUsers[i]);
	            }
	        }
	    };
	    
	    
	    $rootScope.prevPageGroupMembers = function () {
	        if ($rootScope.currentPageGroupMembers > 0) {
	        	$rootScope.currentPageGroupMembers--;
	        }
	    };
	    
	    $rootScope.nextPageGroupMembers = function () {
	        if ($rootScope.currentPageGroupMembers < $rootScope.displayGroupMembers.length - 1) {
	        	$rootScope.currentPageGroupMembers++;
	        }
	    };
	    
	    $rootScope.setPageGroupMembers = function (test) {
	    	$rootScope.currentPageGroupMembers = test;
	    };
	    
	    
	    
	    
		
        $rootScope.selectGame = function(id) {
        	$http({
				method: 'POST',
				url: '/games/getGame',
				headers: {
					'Content-Type': 'text/plain'   
					 },
				data: JSON.stringify(id)
			}).then(function success(response) {

				if(response.data == ""){
					$rootScope.setTab(20);
				} else {

					$rootScope.selectedGame = response.data;
					$rootScope.selectedGameDev=$rootScope.selectedGame.gameDevelopers;
					
					for(var j=0; j<$rootScope.selectedGame.reviews.length; j++){
						var str = $rootScope.selectedGame.reviews[j].revdate;
						var spl = str.split("-");
						var newStr = spl[2]+". ";
						if(spl[1]=="01"){
							newStr = newStr + "Janury ";
						} else if(spl[1]=="02"){
							newStr = newStr + "February ";
						} else if(spl[1]=="03"){
							newStr = newStr + "March ";
						} else if(spl[1]=="04"){
							newStr = newStr + "April ";
						} else if(spl[1]=="05"){
							newStr = newStr + "May ";
						} else if(spl[1]=="06"){
							newStr = newStr + "June ";
						} else if(spl[1]=="07"){
							newStr = newStr + "July ";
						} else if(spl[1]=="08"){
							newStr = newStr + "August ";
						} else if(spl[1]=="09"){
							newStr = newStr + "September ";
						} else if(spl[1]=="10"){
							newStr = newStr + "October ";
						} else if(spl[1]=="11"){
							newStr = newStr + "November ";
						} else if(spl[1]=="12"){
							newStr = newStr + "December ";
						}
						
						newStr = newStr + spl[0]+".";
						$rootScope.selectedGame.reviews[j].revdate=newStr;
					}
					
					$rootScope.reviews = $rootScope.selectedGame.reviews;
					$rootScope.groupToPagesReviews();

					$rootScope.streamers = $rootScope.selectedGame.streamers;
					$rootScope.groupToPagesStreamers();
				}

			});
        	
        	$http({
				method: 'POST',
				url: '/guides/getGuidesByGame',
				headers: {
					'Content-Type': 'text/plain'   
					 },
				data: id
			}).then(function success(response) {

					$rootScope.guides = response.data;
					$rootScope.groupToPagesGuides();

			});
			
			

			
			$http({
				method: 'POST',
				url: '/games/getPlayers',
				headers: {
					'Content-Type': 'text/plain'   
					 },
				data: id
			}).then(function success(response) {


					$rootScope.players = response.data;
					
					for(var i=0;i<$rootScope.players.length;i++) { 
						  while($rootScope.players[i].ptsec >= 60){
							  $rootScope.players[i].ptsec = $rootScope.players[i].ptsec - 60;
							  $rootScope.players[i].ptmin = $rootScope.players[i].ptmin +1;
						  }
						  while($rootScope.players[i].ptmin >= 60){
							  $rootScope.players[i].ptmin = $rootScope.players[i].ptmin - 60;
							  $rootScope.players[i].pthour = $rootScope.players[i].pthour +1;
						  }
						  
						  while($rootScope.players[i].pthour >= 24){
							  $rootScope.players[i].pthour = $rootScope.players[i].pthour - 24;
							  $rootScope.players[i].ptday = $rootScope.players[i].ptday +1;
						  }
						  $rootScope.players[i].time = $rootScope.players[i].ptday+"d "+$rootScope.players[i].pthour+"h "+$rootScope.players[i].ptmin+"min "+$rootScope.players[i].ptsec+"sec";
						}
					

			});
        };
        
        $rootScope.selectUser = function(username){
			
			$http({
				method: 'POST',
				url: '/users/getUser',
				headers: {
					'Content-Type': 'text/plain'   
					 },
				data: username
			}).then(function success(response) {
				if(response.data == ""){
					$rootScope.setTab(20);
				} else {
					$rootScope.selectedUser = response.data;
					$rootScope.groups = $rootScope.selectedUser.gtUser.gameGroups;
					$rootScope.groupToPagesGroups();
					
					var str = $rootScope.selectedUser.udob;
					var spl = str.split("-");
					var newStr = spl[2]+". ";
					if(spl[1]=="01"){
						newStr = newStr + "Janury ";
					} else if(spl[1]=="02"){
						newStr = newStr + "February ";
					} else if(spl[1]=="03"){
						newStr = newStr + "March ";
					} else if(spl[1]=="04"){
						newStr = newStr + "April ";
					} else if(spl[1]=="05"){
						newStr = newStr + "May ";
					} else if(spl[1]=="06"){
						newStr = newStr + "June ";
					} else if(spl[1]=="07"){
						newStr = newStr + "July ";
					} else if(spl[1]=="08"){
						newStr = newStr + "August ";
					} else if(spl[1]=="09"){
						newStr = newStr + "September ";
					} else if(spl[1]=="10"){
						newStr = newStr + "October ";
					} else if(spl[1]=="11"){
						newStr = newStr + "November ";
					} else if(spl[1]=="12"){
						newStr = newStr + "December ";
					}
					
					newStr = newStr + spl[0]+".";
					$rootScope.selectedUser.udob=newStr;
					
					str = $rootScope.selectedUser.gtUser.udate;
					spl = str.split("-");
					newStr = spl[2]+". ";
					if(spl[1]=="01"){
						newStr = newStr + "Janury ";
					} else if(spl[1]=="02"){
						newStr = newStr + "February ";
					} else if(spl[1]=="03"){
						newStr = newStr + "March ";
					} else if(spl[1]=="04"){
						newStr = newStr + "April ";
					} else if(spl[1]=="05"){
						newStr = newStr + "May ";
					} else if(spl[1]=="06"){
						newStr = newStr + "June ";
					} else if(spl[1]=="07"){
						newStr = newStr + "July ";
					} else if(spl[1]=="08"){
						newStr = newStr + "August ";
					} else if(spl[1]=="09"){
						newStr = newStr + "September ";
					} else if(spl[1]=="10"){
						newStr = newStr + "October ";
					} else if(spl[1]=="11"){
						newStr = newStr + "November ";
					} else if(spl[1]=="12"){
						newStr = newStr + "December ";
					}
					
					newStr = newStr + spl[0]+".";
					$rootScope.selectedUser.gtUser.udate=newStr;

					
				}

			});
			
			$http({
				method: 'POST',
				url: '/users/getFriends',
				headers: {
					'Content-Type': 'text/plain'   
					 },
				data: username
			}).then(function success(response) {

					$rootScope.friends =  response.data;


			});
			
			$http({
				method: 'POST',
				url: '/invreq/getFriendRequestSent',
				headers: {
					'Content-Type': 'text/plain'   
					 },
				data: username
			}).then(function success(response) {

				if(response.data=="You've already sent friend request to this user."){
					$rootScope.requestSent =  true;
				} else {
					$rootScope.requestSent =  false;
				}


			});
			
			$http({
				method: 'POST',
				url: '/users/getUserLatestAchievements',
				headers: {
					'Content-Type': 'text/plain'   
					 },
				data: username
			}).then(function success(response) {

					$rootScope.achievements =  response.data;


			});
			
			
			$http({
				method: 'POST',
				url: '/users/getTopGames',
				headers: {
					'Content-Type': 'text/plain'   
					 },
				data: username
			}).then(function success(response) {


					$rootScope.playedGames = response.data;
					
					for(var i=0;i<$rootScope.playedGames.length;i++) { 
						  while($rootScope.playedGames[i].ptsec >= 60){
							  $rootScope.playedGames[i].ptsec = $rootScope.playedGames[i].ptsec - 60;
							  $rootScope.playedGames[i].ptmin = $rootScope.playedGames[i].ptmin +1;
						  }
						  while($rootScope.playedGames[i].ptmin >= 60){
							  $rootScope.playedGames[i].ptmin = $rootScope.playedGames[i].ptmin - 60;
							  $rootScope.playedGames[i].pthour = $rootScope.playedGames[i].pthour +1;
						  }
						  
						  while($rootScope.playedGames[i].pthour >= 24){
							  $rootScope.playedGames[i].pthour = $rootScope.playedGames[i].pthour - 24;
							  $rootScope.playedGames[i].ptday = $rootScope.playedGames[i].ptday +1;
						  }
						  $rootScope.playedGames[i].time = $rootScope.playedGames[i].ptday+"d "+$rootScope.playedGames[i].pthour+"h "+$rootScope.playedGames[i].ptmin+"min "+$rootScope.playedGames[i].ptsec+"sec";
						}
					
	
			});
			
			
			
			 $http({
					method: 'POST',
					url: '/groups/getGroupsByGMAndMember',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: username
				}).then(function success(response) {
					if (response.data == null || response.data.length==0) {
						$rootScope.inviteDisable = true;
					} else {
						$rootScope.inviteDisable = false;
					}
						
				});
			
			
		};
		
		
		$rootScope.selectGuide = function(id,gameId, uname){
			
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
				if(response.data == ""){
					$rootScope.setTab(20);
				} else {
					$rootScope.selectedGuide = response.data;
				}

			});
			
			$http({
				method: 'POST',
				url: '/games/getGame',
				headers: {
					'Content-Type': 'text/plain'   
					 },
				data: gameId
			}).then(function success(response) {

					$rootScope.guideGame =  response.data;


			});
			
			
			
		};
		
		
		   $rootScope.selectDeveloper = function(id){
				
				$http({
					method: 'POST',
					url: '/developers/getDeveloper',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: id
				}).then(function success(response) {
					if(response.data == ""){
						$rootScope.setTab(20);
					} else {
						$rootScope.selectedDeveloper = response.data;
					}

				});
				
				$http({
					method: 'POST',
					url: '/developers/getDevelopedGames',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: id
				}).then(function success(response) {

						$rootScope.developedGames =  response.data;
						$rootScope.groupToPagesDevelopedGames();

				});
				
				
				
			};
			
			
			
			   $rootScope.selectGroup = function(id, uname){
				   
				   var group = {};
				   group.id = id;
				   group.uname = uname;
					
					$http({
						method: 'POST',
						url: '/groups/getGroup',
						headers: {
							'Content-Type': 'application/json'   
							 },
						data: group
					}).then(function success(response) {
						if(response.data == ""){
							$rootScope.setTab(20);
						} else {
							$rootScope.selectedGroup = response.data;
							
							$http({
								method: 'POST',
								url: '/users/getUser',
								headers: {
									'Content-Type': 'text/plain'   
									 },
								data: $rootScope.selectedGroup.grgm
							}).then(function success(response) {
								var bool = false;
								for (var i = 0; i < $rootScope.selectedGroup.gameUsers.length; i++) {
									if($rootScope.selectedGroup.gameUsers[i].uname == response.data.uname){
										bool = true;
									}
								}
								if(bool==false){
									$rootScope.selectedGroup.gameUsers.unshift(response.data);
								}
								$rootScope.groupToPagesGroupMembers();
							});
						
							
							
							
						}

					});
					
				
					
					
					
				};
				
				
				 $rootScope.selectItem = function(id, uname){
					   
					   var item = {};
					   item.id = id;
					   item.uname = uname;
						
						$http({
							method: 'POST',
							url: '/items/getItem',
							headers: {
								'Content-Type': 'application/json'   
								 },
							data: item
						}).then(function success(response) {

							if(response.data == ""){
								$rootScope.setTab(20);
							} else {
								$rootScope.selectedItem = response.data;
								var itemGame = $rootScope.selectedItem.gameGameid;
								$http({
									method: 'POST',
									url: '/items/getItemsByGame',
									headers: {
										'Content-Type': 'text/plain'   
										 },
									data: itemGame
								}).then(function success(response) {

										$rootScope.itemsGame =  response.data;
										for (var i=0; i<$rootScope.itemsGame.length; i++){
											if($rootScope.itemsGame[i].itemid==$rootScope.selectedItem.itemid && $rootScope.itemsGame[i].gameUserUname==$rootScope.selectedItem.gameUserUname){
												$rootScope.itemsGame.splice(i,1);
											}
										}

								});
								
								
							}

						});

						
					};
					
					
					
				$rootScope.selectRequest = function(id){
						   
						  
							
							$http({
								method: 'POST',
								url: '/requests/getRequest',
								headers: {
									'Content-Type': 'text/plain'  
									 },
								data: id
							}).then(function success(response) {
								if(response.data == ""){
									$rootScope.setTab(20);
								} else {
									$rootScope.selectedRequest = response.data;
								}

							}, function error(response) {
								console.log(response);
							});

							
						};
						
						
				$rootScope.selectTicket = function(id){
						   
						  
							
							$http({
								method: 'POST',
								url: '/tickets/getTicket',
								headers: {
									'Content-Type': 'text/plain'  
									 },
								data: id
							}).then(function success(response) {
								if(response.data == ""){
									$rootScope.setTab(20);
								} else {
									$rootScope.selectedTicket = response.data;
									if ($rootScope.selectedTicket.tickstat=="ANSWERED"){
										$http({
											method: 'POST',
											url: '/tickets/getTicketAnswerByTicket',
											headers: {
												'Content-Type': 'text/plain'  
												 },
											data: id
										}).then(function success(response) {
											if(response.data == ""){
												$rootScope.setTab(20);
											} else {
												$rootScope.selectedAnswer = response.data[0];
											}
										});
										}
									}

							}, function error(response) {
								console.log(response);
							});

							
						};
						
						
		
		
    });
	
	app.controller('ReportController', ['$scope', '$log', '$http', '$location', function( $scope, $log, $http, $location){
		var control = this;
		var myreport;
		var reportURI = "/reports/GameAge";
		var reportURI2 = "/reports/developeri";
		var id = 3;
		var gameName = "World of Warcraft";
		var devId = 1;
		var devName = "Blizzard Entertainment";
		control.games = [];
		control.developers = [];
		control.selectedGame = {};
		control.selectedDeveloper = {};
		control.pagesNumber=1;
		control.currentPage=1;
		control.pagesNumberDev=1;
		control.currentPageDev=1;
		$('#buttonsDev').hide();
		$('#buttonsGame').hide();
		
		 $http({
				method: 'GET',
				url: '/games/getGamesCompact'
			}).then(function success(response) {
				if (response.data != null) {
					control.games = response.data;
					control.selectedGame = control.games[0];
					for(var i=0;i<response.data.length;i++) { 
						control.games[i].gamename=control.games[i].gamename+" ("+control.games[i].gamery+")";

					}
					
				}
					
			});
		 
		 $http({
				method: 'GET',
				url: '/developers/getDevelopers'
			}).then(function success(response) {
				if (response.data != null) {
					control.developers = response.data;
					control.selectedDeveloper = control.developers[0];
					
					
				}
					
			});
		

		
		 this.show = function () {
			 control.id = control.selectedGame.gameid;
			 control.gameName = control.selectedGame.gamename;
			 
			 visualize({
				    auth: {
				        name: "jasperadmin",
				        password: "jasperadmin"
				    }
				}, function (v) {

				    report = v.report({ 
				            resource: reportURI, 
				            container: "#container",
				            success: function() {
				            	report.export({
						    		outputFormat: "pdf"
						    	}).done(function (link) {

							    	window.open(link.href); // open new window to download report
							    	control.currentPage=1;
							    	control.pagesNumber=report.data().totalPages;
							    	if(control.pagesNumber>1){
							    		$('#buttonsGame').show();
							    		angular.element(document.getElementById('previousPageGame'))[0].disabled = true;
				
							    	} else {
							    		$('#buttonsGame').hide();
							    	}
							    	})
							    	.fail(function (err) {
							    		alert(err.message);

				            });
				            },
				            params: { "GameID": [control.id],
				            		  "GameName": [control.gameName]}
				             });
				  

				       
				});
		 
		 };
		 
		 this.showDeveloper = function () {
			 control.devid = control.selectedDeveloper.gdid;
			 control.devName = control.selectedDeveloper.gdname;
		
			 visualize({
				    auth: {
				        name: "jasperadmin",
				        password: "jasperadmin"
				    }
				}, function (v) {
					
				    report = v.report({ 
				            resource: reportURI2, 
				            container: "#container2",
				            success: function() {
				            	
				            	report.export({
						    		outputFormat: "pdf"
						    	}).done(function (link) {
						    		
							    	window.open(link.href); // open new window to download report
							    	control.currentPageDev=1;
							    	control.pagesNumberDev=report.data().totalPages;
							    	if(control.pagesNumberDev>1){
							    		$('#buttonsDev').show();
							    		angular.element(document.getElementById('previousPageDev'))[0].disabled = true;
				
							    	} else {
							    		$('#buttonsDev').hide();
							    	}

							    	})
							    	.fail(function (err) {
							    		alert(err.message);

				            });
				            },
				            params: { "p_devid": [control.devid],
				            		  "p_gdname": [control.devName]}
				             });
				  

				       
				});
				
		 
		 };
		 
		 this.nextPageDeveloper = function () {
			 control.devid = control.selectedDeveloper.gdid;
			 control.devName = control.selectedDeveloper.gdname;
			 control.currentPageDev = control.currentPageDev +1;
			 angular.element(document.getElementById('previousPageDev'))[0].disabled = false;
			 if(control.currentPageDev == control.pagesNumberDev){
				 angular.element(document.getElementById('nextPageDev'))[0].disabled = true;
			 } else {
				 angular.element(document.getElementById('nextPageDev'))[0].disabled = false;
			 }
			 visualize({
				    auth: {
				        name: "jasperadmin",
				        password: "jasperadmin"
				    }
				}, function (v) {
					
				    report = v.report({ 
				            resource: reportURI2, 
				            container: "#container2",
				            pages: control.currentPageDev,
				            params: { "p_devid": [control.devid],
				            		  "p_gdname": [control.devName]}
				             });
				  

				       
				});
				
		 
		 };
		 
		 this.previousPageDeveloper = function () {
			 control.devid = control.selectedDeveloper.gdid;
			 control.devName = control.selectedDeveloper.gdname;
			 control.currentPageDev = control.currentPageDev -1;
			 angular.element(document.getElementById('nextPageDev'))[0].disabled = false;
			 if(control.currentPageDev == 1){
				 angular.element(document.getElementById('previousPageDev'))[0].disabled = true;
			 } else {
				 angular.element(document.getElementById('previousPageDev'))[0].disabled = false;
			 }
			 visualize({
				    auth: {
				        name: "jasperadmin",
				        password: "jasperadmin"
				    }
				}, function (v) {
					
				    report = v.report({ 
				            resource: reportURI2, 
				            container: "#container2",
				            pages: control.currentPageDev,
				            params: { "p_devid": [control.devid],
				            		  "p_gdname": [control.devName]}
				             });
				  

				       
				});
				
		 
		 };
		 
		 
		 
		 this.nextPageGame = function () {
			 control.id = control.selectedGame.gameid;
			 control.gameName = control.selectedGame.gamename;
			 control.currentPage = control.currentPage +1;
			 angular.element(document.getElementById('previousPageGame'))[0].disabled = false;
			 if(control.currentPage == control.pagesNumber){
				 angular.element(document.getElementById('nextPageGame'))[0].disabled = true;
			 } else {
				 angular.element(document.getElementById('nextPageGame'))[0].disabled = false;
			 }
			 visualize({
				    auth: {
				        name: "jasperadmin",
				        password: "jasperadmin"
				    }
				}, function (v) {
					
				    report = v.report({ 
				            resource: reportURI, 
				            container: "#container",
				            pages: control.currentPage,
				            params: { "GameID": [control.id],
			            		  "GameName": [control.gameName]}
			             });
				  

				       
				});
				
		 
		 };
		 
		 this.previousPageGame = function () {
			 control.id = control.selectedGame.gameid;
			 control.gameName = control.selectedGame.gamename;
			 control.currentPage = control.currentPage -1;
			 angular.element(document.getElementById('nextPageGame'))[0].disabled = false;
			 if(control.currentPage == 1){
				 angular.element(document.getElementById('previousPageGame'))[0].disabled = true;
			 } else {
				 angular.element(document.getElementById('previousPageGame'))[0].disabled = false;
			 }
			 visualize({
				    auth: {
				        name: "jasperadmin",
				        password: "jasperadmin"
				    }
				}, function (v) {
					
				    report = v.report({ 
				            resource: reportURI, 
				            container: "#container",
				            pages: control.currentPage,
				            params: { "GameID": [control.id],
			            		  "GameName": [control.gameName]}
			             });
				  

				       
				});
				
		 
		 };
		
		
	}]);
	
	
	
	
	app.controller('IndexController', ['$http', '$window', '$rootScope', function($http, $window,$rootScope) {
		
		this.authorize = function() {
			
			$http({
				method: 'POST',
				url: '/login/authorize',
				headers: {
					   'Content-Type': 'text/plain'
					 },
					 data: 'index'
			}).then(function success(response) {
				if (response.data == "Not logged in") {
					$window.location.href = '/login.html';
				} else {
					
					$http({
						method: 'GET',
						url: '/users/getActiveType',
					}).then(function success(response) {

						$rootScope.activeType = response.data;
						if(response.data=="1"){
							$http({
								method: 'GET',
								url: '/users/getActive',
							}).then(function success(response) {

								$rootScope.active = response.data;
								$http({
									method: 'GET',
									url: '/groups/getGroupsByGM',
									
								}).then(function success(response) {
									if(response.data!=null){
										$rootScope.GMGroups=response.data;
									}
								
								});
								$http({
									method: 'GET',
									url: '/users/getMyLibrary',
									
								}).then(function success(response) {
									if(response.data!=null){
										if(response.data.length>5){
											$http({
												method: 'GET',
												url: '/users/getRecommendation',
												
											}).then(function success(response) {
												if(response.data!=null){
													$rootScope.recommendation=response.data[0];
													$rootScope.showRecommend=true;
												}
											
											});
										}
									}
								
								});
									

							}, function errorCallback(response) {
								console.log(response.data);
							  });
						}
							

					}, function errorCallback(response) {
						console.log(response.data);
					  });
					
			
				}
			});
		}
		
		this.logout = function() {
			$http.get("/login/logout").then(function(response) {
				$window.location.href = '/login.html';
			});
			
		}
		
		
				
		this.authorize();

	}]);
	
	
	
	
	
	
	
	
	
	
})();

