(function() {
	var app = angular.module('profileJS', []);
	
	
	app.controller("FriendRequestController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.requests = [];
		control.gap = 5;

		control.guides = [];
		control.itemsPerPage = 25;
		control.currentPage = 0;
		control.display = [];
		
		//$window.location.href = '/login.html';
		
		
	
		
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
		
	    control.accept = function(id) {
			
			$http({
				method: 'POST',
				url: '/invreq/acceptFriendRequest',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: id
			}).then(function success(response) {
				control.loadRequests();
				toastr["success"]('Friend request from ' + response.data + ' accepted.', "Friend Request accepted!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please reload the page and try again later.', "Error!");
			  });

		}
	    
	    control.decline = function(id) {
	    	
	    	$http({
				method: 'POST',
				url: '/invreq/declineFriendRequest',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: id
			}).then(function success(response) {
				control.loadRequests();
				toastr["warning"]('Friend request from '+response.data+' declined!', 'Warning!');
			}, function errorCallback(response) {

				toastr["error"]('Operation failed! Please reload the page and try again later.', "Error!");
			  });

	    	
	    }
		
	
		
		control.loadRequests = function() {
			
			$http({
				method: 'GET',
				url: '/invreq/getFriendRequests',
			}).then(function success(response) {

					control.requests = response.data;
					for(var i=0; i<control.requests.length; i++){
						var str = control.requests[i].reqdate;
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
						control.requests[i].reqdate=newStr;
					}
					control.groupToPages();

			}, function error(response) {
				console.log(response);
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
			if($rootScope.tab=="5"){
				control.loadRequests();
				control.currentPage = 0;
			}
			
		});
		
	}]);
	
	
	app.controller("GroupInviteController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.invites = [];
		control.gap = 5;


		control.itemsPerPage = 25;
		control.currentPage = 0;
		control.display = [];
		
		//$window.location.href = '/login.html';
		
		
	
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.invites.length; i++) {
	            if (i % control.itemsPerPage === 0) {
	            	control.display[Math.floor(i / control.itemsPerPage)] = [ control.invites[i] ];
	            } else {
	            	control.display[Math.floor(i / control.itemsPerPage)].push(control.invites[i]);
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
		
	    control.accept = function(id) {
			
			$http({
				method: 'POST',
				url: '/invreq/acceptGroupInvite',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: id
			}).then(function success(response) {
				control.loadInvites();
				toastr["success"]('Invite to ' + response.data + ' accepted!', "Success!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please reload the page and try again later.', "Error!");
			  });

		}
	    
	    control.decline = function(id) {
	    	
	    	$http({
				method: 'POST',
				url: '/invreq/declineGroupInvite',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: id
			}).then(function success(response) {
				control.loadInvites();
				toastr["warning"]('Group Invite to '+ response.data + ' declined!', "Warning!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please reload the page and try again later.', "Error!");
			  });
	    	
	    }
		
	
		
		control.loadInvites = function() {
			
			$http({
				method: 'GET',
				url: '/invreq/getGroupInvites',
			}).then(function success(response) {

					control.invites = response.data;
					for(var i=0; i<control.invites.length; i++){
						var str = control.invites[i].gidate;
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
						control.invites[i].gidate=newStr;
					}
					control.groupToPages();

			}, function error(response) {
				console.log(response);
			});
		}
		/*
		if(control.invites.length < 1){
			control.loadInvites();
		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="4"){
				control.loadInvites();
				control.currentPage = 0;
			}
			
		});
		
	}]);
	
	
	app.controller("ProfileController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window,$rootScope) {
		
		var control = this;
		control.playedGames = [];
		control.friends = [];
		control.achievements = [];
		control.groups = [];
		control.user = null;
	
		
	
	
	control.loadProfile = function() {
	
		$http({
			method: 'GET',
			url: '/users/getActive',
		}).then(function success(response) {

			if (response.data.length != 0) {
				
				control.user = response.data;
				
				var str = control.user.udob;
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
				control.user.udob=newStr;
				
				str = control.user.gtUser.udate;
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
				control.user.gtUser.udate=newStr;

				control.groups = control.user.gtUser.gameGroups;
			}
		});
		
		
		$http({
			method: 'GET',
			url: '/users/getMyTopGames',
		}).then(function success(response) {


				control.playedGames = response.data;
				
				for(var i=0;i<control.playedGames.length;i++) { 
					  while(control.playedGames[i].ptsec >= 60){
						  control.playedGames[i].ptsec = control.playedGames[i].ptsec - 60;
						  control.playedGames[i].ptmin = control.playedGames[i].ptmin +1;
					  }
					  while(control.playedGames[i].ptmin >= 60){
						  control.playedGames[i].ptmin = control.playedGames[i].ptmin - 60;
						  control.playedGames[i].pthour = control.playedGames[i].pthour +1;
					  }
					  
					  while(control.playedGames[i].pthour >= 24){
						  control.playedGames[i].pthour = control.playedGames[i].pthour - 24;
						  control.playedGames[i].ptday = control.playedGames[i].ptday +1;
					  }
					  control.playedGames[i].time = control.playedGames[i].ptday+"d "+control.playedGames[i].pthour+"h "+control.playedGames[i].ptmin+"min "+control.playedGames[i].ptsec+"sec";
					}
				

		});
		
		
		$http({
			method: 'GET',
			url: '/users/getMyFriends',
		}).then(function success(response) {

				control.friends =  response.data;


		});
		
		$http({
			method: 'GET',
			url: '/users/getMyLatestAchievements',
		}).then(function success(response) {

				control.achievements =  response.data;


		});
		
		
	};
	/*
		if(control.user == null) {
			control.loadProfile();

		}
		*/
		
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="6"){
				control.loadProfile();
			}
			
		});
		
		

		

	
}]);
	
	

	
	
	
	app.controller("UpdateProfile", ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		
		 
		 
		 $scope.profilePackage = [];
		 $scope.profilePackage[0]=  [
		      {id: '1', name: 'Afghanistan'},
		      {id: '2', name: 'Albania'},
		      {id: '3', name: 'Algeria'},
		      {id: '4', name: 'Andorra'},
		      {id: '5', name: 'Angola'},
		      {id: '6', name: 'Antigua and Barbuda'},
		      {id: '7', name: 'Argentina'},
		      {id: '8', name: 'Armenia'},
		      {id: '9', name: 'Australia'},
		      {id: '10', name: 'Austria'},
		      {id: '11', name: 'Azerbaijan'},
		      {id: '12', name: 'Bahamas'},
		      {id: '13', name: 'Bahrain'},
		      {id: '14', name: 'Bangladesh'},
		      {id: '15', name: 'Barbados'},
		      {id: '16', name: 'Belarus'},
		      {id: '17', name: 'Belgium'},
		      {id: '18', name: 'Belize'},
		      {id: '19', name: 'Benin'},
		      {id: '20', name: 'Bhutan'},
		      {id: '21', name: 'Bolivia'},
		      {id: '22', name: 'Bosnia and Herzegovina'},
		      {id: '23', name: 'Botswana'},
		      {id: '24', name: 'Brazil'},
		      {id: '25', name: 'Brunei'},
		      {id: '26', name: 'Bulgaria'},
		      {id: '27', name: 'Burkina Faso'},
		      {id: '28', name: 'Burundi'},
		      {id: '29', name: 'Cambodia'},
		      {id: '30', name: 'Cameroon'},
		      {id: '31', name: 'Canada'},
		      {id: '32', name: 'Cape Verde'},
		      {id: '33', name: 'Central African Republic'},
		      {id: '34', name: 'Chad'},
		      {id: '35', name: 'Chile'},
		      {id: '36', name: 'China'},
		      {id: '37', name: 'Colombia'},
		      {id: '38', name: 'Comoros'},
		      {id: '39', name: 'Congo DR'},
		      {id: '40', name: 'Congo Republic'},
		      {id: '41', name: 'Cook Islands'},
		      {id: '42', name: 'Costa Rica'},
		      {id: '43', name: 'Croatia'},
		      {id: '44', name: 'Cuba'},
		      {id: '45', name: 'Cyprus'},
		      {id: '46', name: 'Czech Republic'},
		      {id: '47', name: 'Denmark'},
		      {id: '48', name: 'Djibouti'},
		      {id: '49', name: 'Dominica'},
		      {id: '50', name: 'Dominican Republic'},
		      {id: '51', name: 'East Timor'},
		      {id: '52', name: 'Ecuador'},
		      {id: '53', name: 'Egypt'},
		      {id: '54', name: 'El Salvador'},
		      {id: '55', name: 'Equatorial Guinea'},
		      {id: '56', name: 'Eritrea'},
		      {id: '57', name: 'Estonia'},
		      {id: '58', name: 'Ethiopia'},
		      {id: '59', name: 'Fiji'},
		      {id: '60', name: 'Finland'},
		      {id: '61', name: 'France'},
		      {id: '62', name: 'Gabon'},
		      {id: '63', name: 'Gambia'},
		      {id: '64', name: 'Georgia'},
		      {id: '65', name: 'Germany'},
		      {id: '66', name: 'Ghana'},
		      {id: '67', name: 'Greece'},
		      {id: '68', name: 'Grenada'},
		      {id: '69', name: 'Guatemala'},
		      {id: '70', name: 'Guinea'},
		      {id: '71', name: 'Guinea-Bissau'},
		      {id: '72', name: 'Guyana'},
		      {id: '73', name: 'Haiti'},
		      {id: '74', name: 'Honduras'},
		      {id: '75', name: 'Hungary'},
		      {id: '76', name: 'Iceland'},
		      {id: '77', name: 'India'},
		      {id: '78', name: 'Indonesia'},
		      {id: '79', name: 'Iran'},
		      {id: '80', name: 'Iraq'},
		      {id: '81', name: 'Ireland'},
		      {id: '82', name: 'Israel'},
		      {id: '83', name: 'Italy'},
		      {id: '84', name: 'Ivory Coast'},
		      {id: '85', name: 'Jamaica'},
		      {id: '86', name: 'Japan'},
		      {id: '87', name: 'Jordan'},
		      {id: '88', name: 'Kazakhstan'},
		      {id: '89', name: 'Kenya'},
		      {id: '90', name: 'Kiribati'},
		      {id: '91', name: 'Korea DPR'},
		      {id: '92', name: 'Kuwait'},
		      {id: '93', name: 'Kyrgyzstan'},
		      {id: '94', name: 'Laos'},
		      {id: '95', name: 'Latvia'},
		      {id: '96', name: 'Lebanon'},
		      {id: '97', name: 'Lesotho'},
		      {id: '98', name: 'Liberia'},
		      {id: '99', name: 'Libya'},
		      {id: '100', name: 'Liechtenstein'},
		      {id: '101', name: 'Lithuania'},
		      {id: '102', name: 'Luxembourg'},
		      {id: '103', name: 'Macedonia'},
		      {id: '104', name: 'Madagascar'},
		      {id: '105', name: 'Malawi'},
		      {id: '106', name: 'Malaysia'},
		      {id: '107', name: 'Maldives'},
		      {id: '108', name: 'Mali'},
		      {id: '109', name: 'Malta'},
		      {id: '110', name: 'Marshall Islands'},
		      {id: '111', name: 'Mauritania'},
		      {id: '112', name: 'Mauritius'},
		      {id: '113', name: 'Mexico'},
		      {id: '114', name: 'Micronesia'},
		      {id: '115', name: 'Moldova'},
		      {id: '116', name: 'Monaco'},
		      {id: '117', name: 'Mongolia'},
		      {id: '118', name: 'Montenegro'},
		      {id: '119', name: 'Morocco'},
		      {id: '120', name: 'Mozambique'},
		      {id: '121', name: 'Myanmar'},
		      {id: '122', name: 'Namibia'},
		      {id: '123', name: 'Nauru'},
		      {id: '124', name: 'Nepal'},
		      {id: '125', name: 'Netherlands'},
		      {id: '126', name: 'New Zealand'},
		      {id: '127', name: 'Nicaragua'},
		      {id: '128', name: 'Niger'},
		      {id: '129', name: 'Nigeria'},
		      {id: '130', name: 'Norway'},
		      {id: '131', name: 'Oman'},
		      {id: '132', name: 'Pakistan'},
		      {id: '133', name: 'Palau'},
		      {id: '134', name: 'Palestine'},
		      {id: '135', name: 'Panama'},
		      {id: '136', name: 'Papua New Guinea'},
		      {id: '137', name: 'Paraguay'},
		      {id: '138', name: 'Peru'},
		      {id: '139', name: 'Philippines'},
		      {id: '140', name: 'Poland'},
		      {id: '141', name: 'Portugal'},
		      {id: '142', name: 'Qatar'},
		      {id: '143', name: 'Republic of Korea'},
		      {id: '144', name: 'Romania'},
		      {id: '145', name: 'Russia'},
		      {id: '146', name: 'Rwanda'},
		      {id: '147', name: 'Saint Kitts and Nevis'},
		      {id: '148', name: 'Saint Lucia'},
		      {id: '149', name: 'Saint Vincent and the Grenadines'},
		      {id: '150', name: 'Samoa'},
		      {id: '151', name: 'San Marino'},
		      {id: '152', name: 'Saudi Arabia'},
		      {id: '153', name: 'Senegal'},
		      {id: '154', name: 'Serbia'},
		      {id: '155', name: 'Seychelles'},
		      {id: '156', name: 'Sierra Leone'},
		      {id: '157', name: 'Singapore'},
		      {id: '158', name: 'Slovakia'},
		      {id: '159', name: 'Slovenia'},
		      {id: '160', name: 'Solomon Islands'},
		      {id: '161', name: 'Somalia'},
		      {id: '162', name: 'South Africa'},
		      {id: '163', name: 'South Sudan'},
		      {id: '164', name: 'Spain'},
		      {id: '165', name: 'Sri Lanka'},
		      {id: '166', name: 'Sudan'},
		      {id: '167', name: 'Suriname'},
		      {id: '168', name: 'Swaziland'},
		      {id: '169', name: 'Sweden'},
		      {id: '170', name: 'Switzerland'},
		      {id: '171', name: 'Syria'},
		      {id: '172', name: 'São Tomé and Príncipe'},
		      {id: '173', name: 'Taiwan'},
		      {id: '174', name: 'Tajikistan'},
		      {id: '175', name: 'Tanzania'},
		      {id: '176', name: 'Thailand'},
		      {id: '177', name: 'Togo'},
		      {id: '178', name: 'Tonga'},
		      {id: '179', name: 'Trinidad and Tobago'},
		      {id: '180', name: 'Tunisia'},
		      {id: '181', name: 'Turkey'},
		      {id: '182', name: 'Turkmenistan'},
		      {id: '183', name: 'Tuvalu'},
		      {id: '184', name: 'Uganda'},
		      {id: '185', name: 'Ukraine'},
		      {id: '186', name: 'United Arab Emirates'},
		      {id: '187', name: 'United Kingdom'},
		      {id: '188', name: 'United States'},
		      {id: '189', name: 'Uruguay'},
		      {id: '190', name: 'Uzbekistan'},
		      {id: '191', name: 'Vanuatu'},
		      {id: '192', name: 'Vatican'},
		      {id: '193', name: 'Venezuela'},
		      {id: '194', name: 'Vietnam'},
		      {id: '195', name: 'Yemen'},
		      {id: '196', name: 'Zambia'},
		      {id: '197', name: 'Zimbabwe'}
		    ];
		 

		 $scope.profilePackage[1] = {id: '1', name: 'Afghanistan'};

		
		$scope.profilePackage[2]= "";
		$scope.profilePackage[3]= "";
			
		$scope.profilePackage[4] = "";
		$scope.profilePackage[5] = "";
		$scope.profilePackage[6] = "";
		$scope.profilePackage[7] = "";
		
		
	
		  
		  
		  

		  $scope.open = function () {
			 
					
					$http({
						method: 'GET',
						url: '/users/getActive',
					}).then(function success(response) {

						if (response.data.length != 0) {
							$scope.profilePackage[2]= response.data.rname;
							$scope.profilePackage[3]= response.data.ucountry;
							for(var i=0; i<$scope.profilePackage[0].length; i++){
								if ($scope.profilePackage[3]==$scope.profilePackage[0][i].name){
									$scope.profilePackage[1]=$scope.profilePackage[0][i];
								}
							}
							$scope.profilePackage[4] = response.data.ubio;
							
							$scope.profilePackage[5] = response.data.gtUser.uavat;
							$scope.profilePackage[7] = response.data.gtUser.uavat;

							$scope.profilePackage[6] = response.data.uname;
						}
						
						 var modalInstance = $modal.open({
						      templateUrl: 'updateProfileContent.html',
						      controller: ModalInstanceCtrl,
						      resolve: {
						    	  profilePackage: function () {
						          return $scope.profilePackage;
						        }
						      }
						    });

						    modalInstance.result.then(function (selectedItem) {
						      $scope.selectedUser = selectedItem;
						    }, function () {
						
						    });
					});
					
					
					$scope.removeImg = function() {
						$scope.profilePackage[5] = $scope.profilePackage[7];
						$('#profileFile').trigger("input");
						$('#profileAvatar').trigger("input");
						fileread="profilePackage[5]";
			
					}

		   
		  };
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, profilePackage) {
	

		
			
			$scope.profilePackage = profilePackage;

			$scope.removeImg = function() {
				$scope.profilePackage[5] = $scope.profilePackage[7];
				$('#profileFile').trigger("input");
				$('#profileAvatar').trigger("input");
				fileread="profilePackage[5]";
	
			}

		  $scope.ok = function () {
			  
			 

			var newUser = {}
			newUser.uname = profilePackage[6];
			newUser.ucountry =profilePackage[1].name;
			newUser.ubio = profilePackage[4];

			newUser.uavat = profilePackage[5];
			newUser.rname = profilePackage[2];
			 
			
			
			  $http({
					method: 'POST',
					url: '/users/updateProfile',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: newUser
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("ProfileUpdateResult");
					if($scope.result =='Profile successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Profile successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.newUser);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("ProfileUpdateResult");
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
	
	

	app.controller("LibraryController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.myGames = [];
		control.gamesPerPage = 30;
		control.gap = 5;
		control.currentPage = 0;
		control.display = [];



		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.myGames.length; i++) {
	            if (i % control.gamesPerPage === 0) {
	            	control.display[Math.floor(i / control.gamesPerPage)] = [ control.myGames[i] ];
	            } else {
	            	control.display[Math.floor(i / control.gamesPerPage)].push(control.myGames[i]);
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
		
		
		
		control.loadGames = function() {

			$http({
				method: 'GET',
				url: '/users/getMyLibrary',
			}).then(function success(response) {

					control.myGames = response.data;
					control.groupToPages();


			}, function errorCallback(response) {


			  });
		}
		/*
		if(control.myGames.length < 1){
			control.loadGames();

		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="15"){
				control.loadGames();
				control.currentPage = 0;
			}
			
		});
		
		
	}]);
	
	
	app.controller("ChatController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.friends = [];
		control.messages = [];
		control.home=1;
		control.username = {};
		control.messageContent="";
		control.header = {};
		
		
		
		
		
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="28"){
				control.loadFriends();
			}
			
		});
		
		control.isSet = function(username){
			if(control.username==username && control.home==0){
				return true;
			} else {
				return false;
			}
			
		}
		
		control.selectUser = function(username) {
			control.username=username;
			control.home=0;
			$http({
				method: 'POST',
				url: '/users/getMessages',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: username
			}).then(function success(response) {
				control.messages=response.data;
				control.setupWebsocket();
				control.scrollBottom();

			}, function errorCallback(response) {
			

			  });
			
		}
		
		
		control.scrollBottom = function(){
			var objDiv = document.getElementById("chatbox");
			objDiv.scrollTop = objDiv.scrollHeight;
		}
		
		control.reloadMessages = function(){
			
			$http({
				method: 'POST',
				url: '/users/getMessages',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: control.username
			}).then(function success(response) {
				control.messages=response.data;
				var objDiv = document.getElementById("chatbox");
				objDiv.scrollTop = objDiv.scrollHeight;

			}, function errorCallback(response) {
			

			  });
			
		}
		
		control.sendMessage = function() {
			
			var message = {};
			message.receiver = control.username;
			message.content = control.messageContent;
			
			$http({
				method: 'POST',
				url: '/users/sendMessage',
				headers: {
					'Content-Type': 'application/json' 
					 },
				data: message
			}).then(function success(response) {
				control.messageContent="";

			}, function errorCallback(response) {
			

			  });
			
		}
		
		control.loadFriends = function() {

			$http({
				method: 'GET',
				url: '/users/getMyFriends',
			}).then(function success(response) {

					control.friends = response.data;

					

			}, function errorCallback(response) {
				console.log(response.data);

			  });
			
			
		}
		/*
		if(control.friends.length < 1){
			control.loadFriends();

		}
		*/
		
		
		
		control.setupWebsocket = function() {
			var socket = new SockJS('/stomp');
			var stompClient = Stomp.over(socket);
			stompClient.connect({}, function(frame) {
				
			if($rootScope.active!=null){
				if(control.username>$rootScope.active.uname){
					control.header = control.username + "+" + $rootScope.active.uname;
				} else {
					control.header = $rootScope.active.uname + "+" + control.username;
				}
				var str = "chats?betweenID=" + control.header;
				stompClient.subscribe("/topic/" + str, function(data) {
					var message = data.body;
					req = angular.fromJson(message);
					control.reloadMessages();
					$scope.$apply();
				})
			}
			})
		}
		
		
		
		
		
		
	}]);
	
	
	app.controller("FriendsController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.friends = [];
		control.friendsPerPage = 5;
		control.gap = 5;
		control.currentPage = 0;
		control.display = [];
		
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="1"){
				control.loadFriends();
			}
			
		});
		
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.friends.length; i++) {
	            if (i % control.friendsPerPage === 0) {
	            	control.display[Math.floor(i / control.friendsPerPage)] = [ control.friends[i] ];
	            } else {
	            	control.display[Math.floor(i / control.friendsPerPage)].push(control.friends[i]);
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
		
		
		
		control.loadFriends = function() {

			$http({
				method: 'GET',
				url: '/users/getFriendsInGame',
			}).then(function success(response) {

					control.friends = response.data;
					
					control.groupToPages();


			}, function errorCallback(response) {
				console.log(response.data);

			  });
		}
		/*
		if(control.friends.length < 1){
			control.loadFriends();

		}
		*/
		
	}]);
	
	
		
	})();