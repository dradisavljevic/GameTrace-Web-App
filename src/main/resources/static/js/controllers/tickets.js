(function() {
	var app = angular.module('ticketsJS', []);
	
	
	app.controller("MyTicketController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.tickets = [];
		control.ticketsPerPage = 10;
		control.gap = 5;
		control.currentPage = 0;
		control.display = [];

		control.isNotAnswered = function(stat){
			if(stat=="SUBMITTED"){
				return true;
			} else {
				return false;
			}
		}
		
		control.removeTicket = function(id) {
			
			
			$http({
				method: 'POST',
				url: '/tickets/removeTicket',
				headers: {
					'Content-Type': 'text/plain'  
					 },
				data: id
			}).then(function success(response) {
				control.loadTickets();
				toastr["success"]('Ticket '+response.data.tickt+' successfully deleted!', "Success!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please reload the page and try again later.', "Error!");
			  });

		}
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.tickets.length; i++) {
	            if (i % control.ticketsPerPage === 0) {
	            	control.display[Math.floor(i / control.ticketsPerPage)] = [ control.tickets[i] ];
	            } else {
	            	control.display[Math.floor(i / control.ticketsPerPage)].push(control.tickets[i]);
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
		
		
		
		control.loadTickets = function() {

			$http({
				method: 'GET',
				url: '/tickets/getMyTickets',
			}).then(function success(response) {

					control.tickets = response.data;
					
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
			if($rootScope.tab=="21"){
				control.loadTickets();
				control.currentPage = 0;
				control.filter = "";
			}
			
		});
		
		
	}]);
	
	
	
	app.controller('TicketAddController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;

		 
		 $scope.ticketPackage = [];
		 $scope.ticketPackage[0]="";
		 $scope.ticketPackage[1]="";
		  
		  

		  $scope.open = function (size) {
			  	
					var modalInstance = $modal.open({
					      templateUrl: 'myTicketContent.html',
					      controller: ModalInstanceCtrl,
					      size: size,
					      resolve: {
					    	  ticketPackage: function () {
					          return $scope.ticketPackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedTicket = selectedItem;
					    }, function () {
					
					    });
					
			  

		    
		  };
	

	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, ticketPackage) {
	

			ticketPackage[0]="";
			ticketPackage[1]="";
			
			$scope.ticketPackage = ticketPackage;

		  

		  $scope.ok = function () {
			  
			 

			  var ticket = {};
			  ticket.name = ticketPackage[0];
			  ticket.description = ticketPackage[1];

			  $scope.ticket = ticket;

			
			  $http({
					method: 'POST',
					url: '/tickets/addTicket',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: ticket
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("TicketAddResult");
					if($scope.result =='Ticket successfully submitted'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Ticket successfully submitted'){  
						setTimeout(function(){$modalInstance.close($scope.ticket);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("TicketAddResult");
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
	
	
	
	app.controller('MyTicketUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 
		 
		 $scope.ticketPackage = [];
		 $scope.ticketPackage[0]="";
		 $scope.ticketPackage[1]="";
		 $scope.ticketPackage[2] = {};
		  
		  
		  
		  

		  $scope.open = function (id) {

			  $scope.ticketPackage[2]=id;

			
			  $http({
					method: 'POST',
					url: '/tickets/getTicket',
					headers: {
						   'Content-Type': 'text/plain'
						 },
						 data: id
				}).then(function success(response) {
					$scope.ticketPackage[0]=response.data.tickt;
					$scope.ticketPackage[1]=response.data.tickdesc;
					
					var modalInstance = $modal.open({
					      templateUrl: 'updateTicketContent.html',
					      controller: ModalInstanceCtrl,
					      resolve: {
					    	  ticketPackage: function () {
					          return $scope.ticketPackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedTicket = selectedItem;
					    }, function () {
					
					    });
				
				});
			  

		    
		  };
	

	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window,  ticketPackage) {
	

		
			
			$scope.ticketPackage = ticketPackage;

		  

		  $scope.ok = function () {
			  
			 

			  var ticket = {};
			  ticket.name = ticketPackage[0];
			  ticket.description = ticketPackage[1];
			  ticket.id = ticketPackage[2];

			  $scope.ticket = ticket;

			
			  $http({
					method: 'POST',
					url: '/tickets/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: ticket
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("TicketUpdateResult");
					if($scope.result =='Ticket successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Ticket successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.ticket);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("TicketUpdateResult");
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
	
	
	app.controller('AnswerAddController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;

		 
		 $scope.answerPackage = [];
		 $scope.answerPackage[0]="";
		 $scope.answerPackage[1]={};
		  
		 $scope.isNotAnswered = function(stat){
				if(stat=="SUBMITTED"){
					return true;
				} else {
					return false;
				}
			}

		  $scope.open = function (id) {

			  $scope.answerPackage[1]=id;

			
					var modalInstance = $modal.open({
					      templateUrl: 'answerTicketContent.html',
					      controller: ModalInstanceCtrl,
					      resolve: {
					    	  answerPackage: function () {
					          return $scope.answerPackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedAnswer = selectedItem;
					    }, function () {
					
					    });


		    
		  };
	

	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, answerPackage) {
	

			answerPackage[0]="";
			
			$scope.answerPackage = answerPackage;

		  

		  $scope.ok = function () {
			  
			 

			  var answer = {};
			  answer.content = answerPackage[0];
			  answer.id = answerPackage[1];

			  $scope.answer = answer;

			
			  $http({
					method: 'POST',
					url: '/tickets/addAnswer',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: answer
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("AnswerAddResult");
					if($scope.result =='Ticket successfully answered'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Ticket successfully answered'){  
						setTimeout(function(){$modalInstance.close($scope.answer);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("AnswerAddResult");
					elem.style.color = "Red";
					$scope.result= 'Unkown error occured. Please re-check your input, reload the page and try again later';
					console.log(response);
				});
				
			  
			 
		  };
		  
		  

		  $scope.cancel = function () {
		    $modalInstance.dismiss('cancel');
		  };
		};
	}]);
	
	
	app.controller('AnswerUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 
		 
		 $scope.answerPackage = [];
		 $scope.answerPackage[0]="";
		 $scope.answerPackage[1]={};
		  
		 $scope.isNotAnswered = function(stat){
				if(stat=="SUBMITTED"){
					return true;
				} else {
					return false;
				}
			}
		  

		  $scope.open = function (id) {

			  $scope.answerPackage[1]=id;

			
			  $http({
					method: 'POST',
					url: '/tickets/getAnswer',
					headers: {
						   'Content-Type': 'text/plain'
						 },
						 data: id
				}).then(function success(response) {
					$scope.answerPackage[0]=response.data.tanscont;
					
					var modalInstance = $modal.open({
					      templateUrl: 'updateAnswerContent.html',
					      controller: ModalInstanceCtrl,
					      resolve: {
					    	  answerPackage: function () {
					          return $scope.answerPackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedAnswer = selectedItem;
					    }, function () {
					
					    });
				
				});
			  

		    
		  };
	

	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window,  answerPackage) {
	

		
			
			$scope.answerPackage = answerPackage;

		  

		  $scope.ok = function () {
			  
			 

			  var answer = {};
			  answer.content = answerPackage[0];
			  answer.id = answerPackage[1];

			  $scope.answer = answer;

			
			  $http({
					method: 'POST',
					url: '/tickets/updateAnswer',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: answer
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("AnswerUpdateResult");
					if($scope.result =='Answer successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Answer successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.answer);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("AnswerUpdateResult");
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
	
	
	app.controller("AllTicketController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.tickets = [];
		control.ticketsPerPage = 25;
		control.gap = 5;
		control.currentPage = 0;
		control.display = [];
		
		control.filters = [
		      {id: '1', name: 'tickt', label: 'Ticket Title'},
		      {id: '2', name: 'tickstat', label: 'Ticket Status'},
		      {id: '3', name: 'ticksub', label: 'Ticket Submitter'}
		    ];
		control.flt_selected = {id: '1', name: 'tickt', label: 'Ticket Title'};

		control.flt_reverse = false;
		
		control.filter = "";

		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/tickets/filterTitle',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {

						control.tickets = response.data;
						control.currentPage = 0;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '2') {

				$http({
					method: 'POST',
					url: '/tickets/filterStatus',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {

						control.tickets = response.data;
						control.currentPage = 0;
						control.groupToPages();

				});
			}  else if (control.flt_selected.id == '3') {

				$http({
					method: 'POST',
					url: '/tickets/filterSubmit',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {

						control.tickets = response.data;
						control.currentPage = 0;
						control.groupToPages();

				});
			}
			 
		}
		
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.tickets.length; i++) {
	            if (i % control.ticketsPerPage === 0) {
	            	control.display[Math.floor(i / control.ticketsPerPage)] = [ control.tickets[i] ];
	            } else {
	            	control.display[Math.floor(i / control.ticketsPerPage)].push(control.tickets[i]);
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
			control.flt_selected = {id: '1', name: 'tickt', label: 'Ticket Title'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/tickets/getAllTickets',
			}).then(function success(response) {

					control.tickets = response.data;
					
					control.groupToPages();
					control.currentPage = 0;

			});
		}
		
		
		control.loadTickets = function() {

			$http({
				method: 'GET',
				url: '/tickets/getAllTickets',
			}).then(function success(response) {

					control.tickets = response.data;
					
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
			if($rootScope.tab=="23"){
				control.flt_selected = {id: '1', name: 'tickt', label: 'Ticket Title'};
				control.loadTickets();
				control.currentPage = 0;
			}
			
		});
		
		
	}]);
	
	
	app.controller("NewTicketController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.tickets = [];
		control.ticketsPerPage = 25;
		control.gap = 5;
		control.currentPage = 0;
		control.display = [];
		
		control.filters = [
		      {id: '1', name: 'tickt', label: 'Ticket Title'},
		      {id: '2', name: 'ticksub', label: 'Ticket Submitter'}
		    ];
		control.flt_selected = {id: '1', name: 'tickt', label: 'Ticket Title'};

		control.flt_reverse = false;
		
		control.filter = "";

		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/tickets/filterTitleUnanswered',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {

						control.tickets = response.data;
						control.currentPage = 0;
						control.groupToPages();

				});
			}  else if (control.flt_selected.id == '2') {

				$http({
					method: 'POST',
					url: '/tickets/filterSubmitUnanswered',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {

						control.tickets = response.data;
						control.currentPage = 0;
						control.groupToPages();

				});
			}
			 
		}
		
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.tickets.length; i++) {
	            if (i % control.ticketsPerPage === 0) {
	            	control.display[Math.floor(i / control.ticketsPerPage)] = [ control.tickets[i] ];
	            } else {
	            	control.display[Math.floor(i / control.ticketsPerPage)].push(control.tickets[i]);
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
			control.flt_selected = {id: '1', name: 'tickt', label: 'Ticket Title'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/tickets/getAllUnanswered',
			}).then(function success(response) {

					control.tickets = response.data;
					
					control.groupToPages();
					control.currentPage = 0;

			});
		}
		
		
		control.loadTickets = function() {

			$http({
				method: 'GET',
				url: '/tickets/getAllUnanswered',
			}).then(function success(response) {

					control.tickets = response.data;
					
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
			if($rootScope.tab=="24"){
				control.flt_selected = {id: '1', name: 'tickt', label: 'Ticket Title'};
				control.loadTickets();
				control.currentPage = 0;
			}
			
		});
		
		
	}]);
	
	
	
})();