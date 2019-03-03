(function() {
	var app = angular.module('itemsJS', []);
	
	
	
	
	app.controller("ItemController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.items = [];
		control.gap = 10;
		control.currentPage = 0;
		control.itemsPerPage = 20;
		control.display = [];
		control.filters = [
		      {id: '1', name: 'itemname', label: 'Item Name'},
		      {id: '2', name: 'itemprice', label: 'Item Price'},
		      {id: '3', name: 'itemcurr', label: 'Item Currency'},
		      {id: '4', name: 'gameUserUname', label: 'Seller'}
		    ];
		//$window.location.href = '/login.html';
		control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/items/filterName',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '2') {
				if(isNaN(control.filter)){
					
				} else {

					$http({
						method: 'POST',
						url: '/items/filterPrice',
						headers: {
							'Content-Type': 'text/plain'   
							 },
						data: control.filter
					}).then(function success(response) {
							control.currentPage = 0;
							control.items = response.data;
							control.groupToPages();
	
					});
				}
			} else if (control.flt_selected.id == '3') {

				$http({
					method: 'POST',
					url: '/items/filterCurrency',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '4') {

				$http({
					method: 'POST',
					url: '/items/filterSeller',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			}
		}
		
		control.isInStock = function(stat){
			  if (stat=="IN STOCK"){
				  return true;
			  } else {
				  return false;
			  }
		  }
		
		control.removeItem = function(id,uname) {
			
			var itemKey = {};
			itemKey.id = id;
			itemKey.uname = uname;
			
			$http({
				method: 'POST',
				url: '/items/removeItem',
				headers: {
					'Content-Type': 'application/json'  
					 },
				data: itemKey
			}).then(function success(response) {
				control.loadItems();
				toastr["success"]('Item '+response.data.itemname+' successfully deleted!', "Success!");
			}, function errorCallback(response) {
				toastr["error"]('Operation failed! Please make sure the desired entry has no relation to other entries in the database.', "Error!");
			  });
		}
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.items.length; i++) {
	            if (i % control.itemsPerPage === 0) {
	            	control.display[Math.floor(i / control.itemsPerPage)] = [ control.items[i] ];
	            } else {
	            	control.display[Math.floor(i / control.itemsPerPage)].push(control.items[i]);
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
			control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/items/getItems',
			}).then(function success(response) {

					control.items = response.data;
					control.groupToPages();
					control.currentPage = 0;

			});
		}
		
		
		control.loadItems = function() {

			$http({
				method: 'GET',
				url: '/items/getItems',
			}).then(function success(response) {

					control.items = response.data;
					control.groupToPages();


			}, function errorCallback(response) {
				console.log(response.data);

			  });
		}
		
		/*
		if(control.items.length < 1){
			control.loadItems();

		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="3"){
				control.loadItems();
				control.currentPage = 0;
				control.filter = "";
				control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};
			}
			
		});
		
		
	}]);
	
	
	
	
	
	
	
	
	app.controller('ItemAddController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 
		 $scope.currencies = [
		      {id: '1', name: 'AUS'},
		      {id: '2', name: 'CHF'},
		      {id: '3', name: 'EUR'},
		      {id: '4', name: 'GBP'},
		      {id: '5', name: 'RSD'},
		      {id: '6', name: 'USD'},
		      {id: '7', name: 'YEN'}
		 ]
		 $scope.selectedCurrency = {id: '1', name: 'AUS'};
		  
		  
		  $scope.itemGames = [];
		  $scope.itemPackage =[];
		  $scope.itemPackage[0]="";
		  $scope.itemPackage[1]="";
		  $scope.itemPackage[2]=$scope.selectedCurrency;
		  $scope.itemPackage[3]="";
		  $scope.itemPackage[4]=$scope.currencies;
		  $scope.itemPackage[5]={};
		  $scope.itemPackage[6]={};
		  $scope.itemPackage[7]=1;
		  
		  
		  
		  

		  
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#itemImg').attr('src', e.target.result);
		
			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileItem").change(function(){
				readURL(this);
			});
		
			
			$scope.removeImg = function() {
				$('#itemImg').attr('src', "");
				$('#itemImg').attr('value', "");
				$('#fileItem').attr('value', "");
				$('#fileItem').trigger("input");
				$('#itemImg').trigger("input");
				itemPackage[3]="";

				fileread="itemPackage[3]";
			}
		  

		  $scope.open = function (size) {
			  
			  $http({
					method: 'GET',
					url: '/games/getGamesCompact'
				}).then(function success(response) {
					if (response.data != null) {
						$scope.itemGames = response.data;
						$scope.selectedItemGame = $scope.itemGames[0];
						for(var i=0;i<response.data.length;i++) { 
							$scope.itemGames[i].gamename=$scope.itemGames[i].gamename+" ("+$scope.itemGames[i].gamery+")";

						}
						$scope.itemPackage[6]=$scope.itemGames;
						$scope.itemPackage[5]=$scope.itemGames[0];
					}
					
					var modalInstance = $modal.open({
					      templateUrl: 'myItemContent.html',
					      controller: ModalInstanceCtrl,
					      size: size,
					      resolve: {
					    	  itemPackage: function () {
					          return $scope.itemPackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedAdm = selectedItem;
					    }, function () {
				
					    });
					    
				});
			  

		    
		  };
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, itemPackage) {
	

			itemPackage[0]="";
			itemPackage[1]="";
			itemPackage[2]=itemPackage[4][0];
			itemPackage[3]="";
			itemPackage[5]=itemPackage[6][0];
			itemPackage[7]=1;
			$scope.itemPackage = itemPackage;
			$scope.currencies = itemPackage[4];
			
			$scope.removeImg = function() {
				$('#itemImg').attr('src', "");
				$('#itemImg').attr('value', "");
				$('#fileItem').attr('value', "");
				$('#fileItem').trigger("input");
				$('#itemImg').trigger("input");
				itemPackage[3]="";

				fileread="itemPackage[3]";
			}
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#itemImg').attr('src', e.target.result);

			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileItem").change(function(){
				readURL(this);
			});

		  $scope.ok = function () {
			  
			 
			
			  var item = {};
			  item.itemcurr = itemPackage[2].name;
			  item.itemim = itemPackage[3];
			  item.itemname = itemPackage[0];
			  item.itemprice = itemPackage[1];
			  item.gameId = itemPackage[5].gameid;
			  item.itemq = itemPackage[7];

			  $scope.item = item;
	
			
			  $http({
					method: 'POST',
					url: '/items/addItem',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: item
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("ItemAddResult");
					if($scope.result =='Item successfully added'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Item successfully added'){  
						setTimeout(function(){$modalInstance.close($scope.item);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("ItemAddResult");
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
	
	
	
	app.controller('ItemUpdateController', ['$scope', '$modal', '$log', '$http', function ($scope, $modal, $log, $http) {
		 var control = this;
		 
		 $scope.currencies = [
		      {id: '1', name: 'AUS'},
		      {id: '2', name: 'CHF'},
		      {id: '3', name: 'EUR'},
		      {id: '4', name: 'GBP'},
		      {id: '5', name: 'RSD'},
		      {id: '6', name: 'USD'},
		      {id: '7', name: 'YEN'}
		 ]
		 $scope.selectedCurrency = {id: '1', name: 'AUS'};
		  
		  
		  
		  $scope.itemPackage =[];
		  $scope.itemPackage[0]="";
		  $scope.itemPackage[1]="";
		  $scope.itemPackage[2]=$scope.selectedCurrency;
		  $scope.itemPackage[3]="";
		  $scope.itemPackage[4]=$scope.currencies;
		  $scope.itemPackage[5]={};
		  $scope.itemPackage[6]={};
		  $scope.itemPackage[7]="";
		  $scope.itemPackage[8]=1;

		  
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#itemUpdateImg').attr('src', e.target.result);
		
			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateItem").change(function(){
				readURL(this);
			});
			
			
			$scope.removeImg = function() {
				$scope.itemPackage[3] = $scope.itemPackage[7];
				$('#fileUpdateItem').trigger("input");
				$('#itemUpdateImg').trigger("input");
				fileread="itemPackage[3]";
	
			}

		  $scope.open = function (id, uname) {
			  var itemKey = {};
			  itemKey.id = id;
			  itemKey.uname = uname;
			  
			  $http({
					method: 'POST',
					url: '/items/getItem',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: itemKey
				}).then(function success(response) {
					$scope.itemPackage[0]=response.data.itemname;
					$scope.itemPackage[1]=response.data.itemprice;
					for (var j=0; j<$scope.itemPackage[4].length; j++){
						if($scope.itemPackage[4][j].name==response.data.itemcurr){
							$scope.itemPackage[2]=$scope.itemPackage[4][j];
						}
					}
					$scope.itemPackage[3]=response.data.itemim;
					$scope.itemPackage[7]=response.data.itemim;
					$scope.itemPackage[5]=id;
				    $scope.itemPackage[6]=uname;
				    $scope.itemPackage[8]=response.data.itemq;
				    
				    var modalInstance = $modal.open({
					      templateUrl: 'updateItemContent.html',
					      controller: ModalInstanceCtrl,
					      resolve: {
					    	  itemPackage: function () {
					          return $scope.itemPackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedAdm = selectedItem;
					    }, function () {
					
					    });
				
				});
			  

		   
		  };
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, itemPackage) {
	

			$scope.itemPackage = itemPackage;
			$scope.currencies = itemPackage[4];
			
		  
			$scope.removeImg = function() {
				$scope.itemPackage[3] = $scope.itemPackage[7];
				$('#fileUpdateItem').trigger("input");
				$('#itemUpdateImg').trigger("input");
				fileread="itemPackage[3]";
	
			}

		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#itemUpdateImg').attr('src', e.target.result);

			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateItem").change(function(){
				readURL(this);
			});

		  $scope.ok = function () {
			  
			 
			
			  var item = {};
			  item.itemcurr = itemPackage[2].name;
			  item.itemim = itemPackage[3];
			  item.itemname = itemPackage[0];
			  item.itemprice = itemPackage[1];
			  item.id = itemPackage[5];
			  item.uname = itemPackage[6];
			  item.itemq = itemPackage[8];

			  $scope.item = item;
	
			
			  $http({
					method: 'POST',
					url: '/items/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: item
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("ItemUpdateResult");
					if($scope.result =='Item successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Item successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.item);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("ItemUpdateResult");
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
	
	
	app.controller('SpecificItemUpdateController', ['$scope', '$modal', '$log', '$http', '$window', '$rootScope', function ($scope, $modal, $log, $http, $window,$rootScope) {
		 var control = this;
		 
		 $scope.currencies = [
		      {id: '1', name: 'AUS'},
		      {id: '2', name: 'CHF'},
		      {id: '3', name: 'EUR'},
		      {id: '4', name: 'GBP'},
		      {id: '5', name: 'RSD'},
		      {id: '6', name: 'USD'},
		      {id: '7', name: 'YEN'}
		 ]
		 $scope.selectedCurrency = {id: '1', name: 'AUS'};
		  
		  
		  
		  $scope.itemPackage =[];
		  $scope.itemPackage[0]="";
		  $scope.itemPackage[1]="";
		  $scope.itemPackage[2]=$scope.selectedCurrency;
		  $scope.itemPackage[3]="";
		  $scope.itemPackage[4]=$scope.currencies;
		  $scope.itemPackage[5]={};
		  $scope.itemPackage[6]={};
		  $scope.itemPackage[7]="";
		  $scope.itemPackage[8]=1;

		  
		  $scope.buyItem = function(id,uname) {
				
				var itemKey = {};
				itemKey.id = id;
				itemKey.uname = uname;
				
				$http({
					method: 'POST',
					url: '/items/buyItem',
					headers: {
						'Content-Type': 'application/json'  
						 },
					data: itemKey
				}).then(function success(response) {
					toastr["success"]('Item '+response.data.itemname+' successfully added to ongoing buy deals list!', "Success!");
					$rootScope.selectItem(id,uname);
		
				}, function errorCallback(response) {
					toastr["error"]('Something went wrong. Please reload the page and try again later!', "Error!");
					  

				  });
				
			}
		  
		  $scope.isNotInStock = function(stat){
			  if (stat!="IN STOCK"){
				  return true;
			  } else {
				  return false;
			  }
		  }
		  
		  $scope.isInStock = function(stat){
			  if (stat=="IN STOCK"){
				  return true;
			  } else {
				  return false;
			  }
		  }
		  
		  $scope.isSold = function(stat){
			  if(stat=="SOLD"){
				  return true;
			  } else {
				  return false;
			  }
		  }
		  
		  $scope.finalizeDeal = function(id,uname) {
				
				var itemKey = {};
				itemKey.id = id;
				itemKey.uname = uname;
				
				$http({
					method: 'POST',
					url: '/items/finalizeDeal',
					headers: {
						'Content-Type': 'application/json'  
						 },
					data: itemKey
				}).then(function success(response) {
					toastr["success"]('Item '+response.data.itemname+' trade deal successfully finalized!', "Success!");
					$rootScope.selectItem(id,uname);
		
				}, function errorCallback(response) {
					toastr["error"]('Something went wrong. Please reload the page and try again later!', "Error!");
					  

				  });

			}
		  
		  $scope.cancelDeal = function(id,uname) {
				
				var itemKey = {};
				itemKey.id = id;
				itemKey.uname = uname;
				
				$http({
					method: 'POST',
					url: '/items/cancelDeal',
					headers: {
						'Content-Type': 'application/json'  
						 },
					data: itemKey
				}).then(function success(response) {
					toastr["warning"]('Item '+response.data.itemname+' trade deal cancelled!', "Warning!");
					$rootScope.selectItem(id,uname);
		
				}, function errorCallback(response) {
					toastr["error"]('Something went wrong. Please reload the page and try again later!', "Error!");
					  

				  });

			}
		  
		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#itemUpdateImg').attr('src', e.target.result);
		
			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateItem").change(function(){
				readURL(this);
			});
			
			
			$scope.removeImg = function() {
				$scope.itemPackage[3] = $scope.itemPackage[7];
				$('#fileUpdateItem').trigger("input");
				$('#itemUpdateImg').trigger("input");
				fileread="itemPackage[3]";
	
			}

		  $scope.open = function (id, uname) {
			  var itemKey = {};
			  itemKey.id = id;
			  itemKey.uname = uname;
			  
			  $http({
					method: 'POST',
					url: '/items/getItem',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: itemKey
				}).then(function success(response) {
					$scope.itemPackage[0]=response.data.itemname;
					$scope.itemPackage[1]=response.data.itemprice;
					for (var j=0; j<$scope.itemPackage[4].length; j++){
						if($scope.itemPackage[4][j].name==response.data.itemcurr){
							$scope.itemPackage[2]=$scope.itemPackage[4][j];
						}
					}
					$scope.itemPackage[3]=response.data.itemim;
					$scope.itemPackage[7]=response.data.itemim;
					$scope.itemPackage[5]=id;
				    $scope.itemPackage[6]=uname;
				    $scope.itemPackage[8]=response.data.itemq;
				    
				    var modalInstance = $modal.open({
					      templateUrl: 'updateItemContent.html',
					      controller: ModalInstanceCtrl,
					      resolve: {
					    	  itemPackage: function () {
					          return $scope.itemPackage;
					        }
					      }
					    });

					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedAdm = selectedItem;
					    }, function () {
					
					    });
				
				});
			  

		   
		  };
	


		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, itemPackage) {
	

			$scope.itemPackage = itemPackage;
			$scope.currencies = itemPackage[4];
			
		  
			$scope.removeImg = function() {
				$scope.itemPackage[3] = $scope.itemPackage[7];
				$('#fileUpdateItem').trigger("input");
				$('#itemUpdateImg').trigger("input");
				fileread="itemPackage[3]";
	
			}

		  
		  function readURL(input) {
			    if (input.files && input.files[0]) {
			        var reader = new FileReader();

			        reader.onload = function (e) {
			            $('#itemUpdateImg').attr('src', e.target.result);

			        }

			        reader.readAsDataURL(input.files[0]);
			    }
			}
			
			$("#fileUpdateItem").change(function(){
				readURL(this);
			});

		  $scope.ok = function () {
			  
			 
			
			  var item = {};
			  item.itemcurr = itemPackage[2].name;
			  item.itemim = itemPackage[3];
			  item.itemname = itemPackage[0];
			  item.itemprice = itemPackage[1];
			  item.id = itemPackage[5];
			  item.uname = itemPackage[6];
			  item.itemq = itemPackage[8];

			  $scope.item = item;
	
			
			  $http({
					method: 'POST',
					url: '/items/update',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: item
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("ItemUpdateResult");
					if($scope.result =='Item successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Item successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.item);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("ItemUpdateResult");
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
	
	
	
	app.controller("ItemBoughtController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.items = [];
		control.gap = 5;
		control.currentPage = 0;
		control.itemsPerPage = 20;
		control.display = [];
		control.filters = [
		      {id: '1', name: 'itemname', label: 'Item Name'},
		      {id: '2', name: 'itemprice', label: 'Item Price'},
		      {id: '3', name: 'itemcurr', label: 'Item Currency'},
		      {id: '4', name: 'gameUserUname', label: 'Seller'}
		    ];
		//$window.location.href = '/login.html';
		control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/items/filterNameBought',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '2') {
				if(isNaN(control.filter)){
					
				} else {

					$http({
						method: 'POST',
						url: '/items/filterPriceBought',
						headers: {
							'Content-Type': 'text/plain'   
							 },
						data: control.filter
					}).then(function success(response) {
							control.currentPage = 0;
							control.items = response.data;
							control.groupToPages();
	
					});
				}
			} else if (control.flt_selected.id == '3') {

				$http({
					method: 'POST',
					url: '/items/filterCurrencyBought',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '4') {

				$http({
					method: 'POST',
					url: '/items/filterSellerBought',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			}
		}
		
		
		
		control.cancelDeal = function(id,uname) {
			
			var itemKey = {};
			itemKey.id = id;
			itemKey.uname = uname;
			
			$http({
				method: 'POST',
				url: '/items/cancelDeal',
				headers: {
					'Content-Type': 'application/json'  
					 },
				data: itemKey
			}).then(function success(response) {
				toastr["warning"]('Item '+response.data.itemname+' trade deal cancelled!', "Warning!");
				control.loadItems();
	
			}, function errorCallback(response) {
				toastr["error"]('Something went wrong. Please reload the page and try again later!', "Error!");
				  

			  });
			
		}
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.items.length; i++) {
	            if (i % control.itemsPerPage === 0) {
	            	control.display[Math.floor(i / control.itemsPerPage)] = [ control.items[i] ];
	            } else {
	            	control.display[Math.floor(i / control.itemsPerPage)].push(control.items[i]);
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
			control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/items/getAllByBuyer',
			}).then(function success(response) {

					control.items = response.data;
					control.groupToPages();
					control.currentPage = 0;

			});
		}
		
		
		control.loadItems = function() {

			$http({
				method: 'GET',
				url: '/items/getAllByBuyer',
			}).then(function success(response) {

					control.items = response.data;
					control.groupToPages();


			}, function errorCallback(response) {
				console.log(response.data);

			  });
		}
		
		/*
		if(control.items.length < 1){
			control.loadItems();

		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="25"){
				control.loadItems();
				control.currentPage = 0;
				control.filter = "";
				control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};
			}
			
		});
		
		
	}]);
	
	
	app.controller("ItemSoldController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.items = [];
		control.gap = 5;
		control.currentPage = 0;
		control.itemsPerPage = 20;
		control.display = [];
		control.filters = [
		      {id: '1', name: 'itemname', label: 'Item Name'},
		      {id: '2', name: 'itemprice', label: 'Item Price'},
		      {id: '3', name: 'itemcurr', label: 'Item Currency'},
		      {id: '4', name: 'itembuy', label: 'Buyer'}
		    ];
		//$window.location.href = '/login.html';
		control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/items/filterNameSold',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '2') {
				if(isNaN(control.filter)){
					
				} else {

					$http({
						method: 'POST',
						url: '/items/filterPriceSold',
						headers: {
							'Content-Type': 'text/plain'   
							 },
						data: control.filter
					}).then(function success(response) {
							control.currentPage = 0;
							control.items = response.data;
							control.groupToPages();
	
					});
				}
			} else if (control.flt_selected.id == '3') {

				$http({
					method: 'POST',
					url: '/items/filterCurrencySold',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '4') {

				$http({
					method: 'POST',
					url: '/items/filterBuyerSold',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			}
		}
		
		
		
		control.cancelDeal = function(id,uname) {
			
			var itemKey = {};
			itemKey.id = id;
			itemKey.uname = uname;
			
			$http({
				method: 'POST',
				url: '/items/cancelDeal',
				headers: {
					'Content-Type': 'application/json'  
					 },
				data: itemKey
			}).then(function success(response) {
				toastr["warning"]('Item '+response.data.itemname+' trade deal cancelled!', "Warning!");
				control.loadItems();
	
			}, function errorCallback(response) {
				toastr["error"]('Something went wrong. Please reload the page and try again later!', "Error!");
				  

			  });

		}
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.items.length; i++) {
	            if (i % control.itemsPerPage === 0) {
	            	control.display[Math.floor(i / control.itemsPerPage)] = [ control.items[i] ];
	            } else {
	            	control.display[Math.floor(i / control.itemsPerPage)].push(control.items[i]);
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
			control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/items/getAllBySeller',
			}).then(function success(response) {

					control.items = response.data;
					control.groupToPages();
					control.currentPage = 0;

			});
		}
		
		
		control.loadItems = function() {

			$http({
				method: 'GET',
				url: '/items/getAllBySeller',
			}).then(function success(response) {

					control.items = response.data;
					control.groupToPages();


			}, function errorCallback(response) {
				console.log(response.data);

			  });
		}
		
		/*
		if(control.items.length < 1){
			control.loadItems();

		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="26"){
				control.loadItems();
				control.currentPage = 0;
				control.filter = "";
				control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};
			}
			
		});
		
		
	}]);
	
	
	app.controller("ItemFinalizedController", ['$scope', '$http', '$window', '$rootScope', function($scope, $http, $window, $rootScope) {
		var control = this;
		control.items = [];
		control.gap = 5;
		control.currentPage = 0;
		control.itemsPerPage = 20;
		control.display = [];
		control.filters = [
		      {id: '1', name: 'itemname', label: 'Item Name'},
		      {id: '2', name: 'itemprice', label: 'Item Price'},
		      {id: '3', name: 'itemcurr', label: 'Item Currency'},
		      {id: '4', name: 'itembuy', label: 'Buyer'},
		      {id: '5', name: 'gameUserUname', label: 'Seller'}
		    ];
		//$window.location.href = '/login.html';
		control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};

		control.flt_reverse = false;
		
		control.filter = "";
		
		control.select = function() {
			if (control.flt_selected.id == '1') {
				$http({
					method: 'POST',
					url: '/items/filterNameFinalized',
					headers: {
						'Content-Type': 'text/plain'  
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '2') {
				if(isNaN(control.filter)){
					
				} else {

					$http({
						method: 'POST',
						url: '/items/filterPriceFinalized',
						headers: {
							'Content-Type': 'text/plain'   
							 },
						data: control.filter
					}).then(function success(response) {
							control.currentPage = 0;
							control.items = response.data;
							control.groupToPages();
	
					});
				}
			} else if (control.flt_selected.id == '3') {

				$http({
					method: 'POST',
					url: '/items/filterCurrencyFinalized',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '4') {

				$http({
					method: 'POST',
					url: '/items/filterBuyerFinalized',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			} else if (control.flt_selected.id == '5') {

				$http({
					method: 'POST',
					url: '/items/filterSellerFinalized',
					headers: {
						'Content-Type': 'text/plain'   
						 },
					data: control.filter
				}).then(function success(response) {
						control.currentPage = 0;
						control.items = response.data;
						control.groupToPages();

				});
			}
		}
		
		
		
	
		
		control.groupToPages = function () {
			control.display = [];
	        
	        for (var i = 0; i < control.items.length; i++) {
	            if (i % control.itemsPerPage === 0) {
	            	control.display[Math.floor(i / control.itemsPerPage)] = [ control.items[i] ];
	            } else {
	            	control.display[Math.floor(i / control.itemsPerPage)].push(control.items[i]);
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
			control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};
			control.filter = "";
			control.flt_reverse = false;
			$http({
				method: 'GET',
				url: '/items/getAllFinalized',
			}).then(function success(response) {

					control.items = response.data;
					control.groupToPages();
					control.currentPage = 0;

			});
		}
		
		
		control.loadItems = function() {

			$http({
				method: 'GET',
				url: '/items/getAllFinalized',
			}).then(function success(response) {

					control.items = response.data;
					control.groupToPages();


			}, function errorCallback(response) {
				console.log(response.data);

			  });
		}
		
		/*
		if(control.items.length < 1){
			control.loadItems();

		}
		*/
		$scope.$watch(angular.bind(this, function (tab) {
			  return $rootScope.tab;
			}), function (newVal, oldVal) {
			if($rootScope.tab=="27"){
				control.loadItems();
				control.currentPage = 0;
				control.filter = "";
				control.flt_selected = {id: '1', name: 'itemname', label: 'Item Name'};
			}
			
		});
		
		
	}]);
	
	
	
	})();