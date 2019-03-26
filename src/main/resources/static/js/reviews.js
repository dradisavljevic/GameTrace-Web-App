(function() {
	var app = angular.module('reviewsJS', []);
	
	
	app.controller('SpecificReviewAddController', ['$scope', '$modal', '$log', '$http', '$location', function ($scope, $modal, $log, $http, $location) {
		 var control = this;
		 
		 $scope.reviewPackage = [];
		 $scope.reviewPackage[0]="";
		 $scope.reviewPackage[1]={};
		 $scope.reviewPackage[2]="";


		  $scope.open = function (size) {
			  

		    var modalInstance = $modal.open({
		      templateUrl: 'mySpecificReviewContent.html',
		      controller: ModalInstanceCtrl,
		      size: size,
		      resolve: {
		    	  reviewPackage: function () {
		          return $scope.reviewPackage;
		        }
		      }
		    });

		    modalInstance.result.then(function (selectedItem) {
		      $scope.selectedReview = selectedItem;
		    }, function () {

		    });
		  };
	

	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, reviewPackage) {
	
			
			reviewPackage[0]="";
			reviewPackage[2]="";
			var putanja = $location.path();

			var deljeno = putanja.split('/');
			reviewPackage[1]=deljeno[2];
			$scope.reviewPackage = reviewPackage;
			
			
			

			$scope.ok = function () {
			  
			 

			  var review = {};
			  review.revtitle = reviewPackage[0];
			  review.revcont = reviewPackage[2];
			  review.gameGameid = reviewPackage[1];

			  $scope.review = review;

			
			  $http({
					method: 'POST',
					url: '/reviews/addReview',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: review
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("ReviewAddResult");
					if($scope.result =='Review successfully added'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Review successfully added'){  
						setTimeout(function(){$modalInstance.close($scope.review);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("ReviewAddResult");
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
	
	
	
	app.controller('SpecificReviewUpdateController', ['$scope', '$modal', '$log', '$http', '$location', function ($scope, $modal, $log, $http, $location) {
		 var control = this;
		 
		 $scope.reviewPackage = [];
		 $scope.reviewPackage[0]="";
		 $scope.reviewPackage[1]={};
		 $scope.reviewPackage[2]="";
		 $scope.reviewPackage[3]={};
		 $scope.reviewPackage[4]={};
		 $scope.reviewPackage[5]={};


		  $scope.open = function (id,gameId, uname) {
			  

			  $scope.reviewPackage[3]=id;
			  $scope.reviewPackage[4]=gameId;
			  $scope.reviewPackage[5]=uname;
			  var reviewKey = {};
			  reviewKey.id = id;
			  reviewKey.gameId = gameId;
			  reviewKey.uname = uname;
			  
			  
			  $http({
					method: 'POST',
					url: '/reviews/getReview',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: reviewKey
				}).then(function success(response) {
					$scope.reviewPackage[0]=response.data.revtitle;
					$scope.reviewPackage[2]=response.data.revcont;
					
					
					    var modalInstance = $modal.open({
					      templateUrl: 'updateSpecificReviewContent.html',
					      controller: ModalInstanceCtrl,
					      resolve: {
					    	  reviewPackage: function () {
					          return $scope.reviewPackage;
					        }
					      }
					    });
			
					    modalInstance.result.then(function (selectedItem) {
					      $scope.selectedReview = selectedItem;
					    }, function () {
			
					    });
				});
		  };
	

	

		var ModalInstanceCtrl = function ($scope, $modalInstance, $window, reviewPackage) {
	
			var putanja = $location.path();

			var deljeno = putanja.split('/');
			reviewPackage[1]=deljeno[2];
			$scope.reviewPackage = reviewPackage;
			
			
			

			$scope.ok = function () {
			  
			 

			  var review = {};
			  review.revtitle = reviewPackage[0];
			  review.revcont = reviewPackage[2];
			  review.gameGameid = reviewPackage[1];
			  review.userUname = reviewPackage[5];
			  review.gameGameid = reviewPackage[4];
			  review.revid = reviewPackage[3];

			  $scope.review = review;

			
			  $http({
					method: 'POST',
					url: '/reviews/updateReview',
					headers: {
						   'Content-Type': 'application/json'
						 },
						 data: review
				}).then(function success(response) {
					$scope.result = response.data;
					var elem = document.getElementById("ReviewUpdateResult");
					if($scope.result =='Review successfully updated'){
						elem.style.color = "Green";
					} else {
						elem.style.color = "Red";
					}
					if($scope.result =='Review successfully updated'){  
						setTimeout(function(){$modalInstance.close($scope.review);$window.location.reload();},1000);
						
					}
				}, function error(response) {
					var elem = document.getElementById("ReviewUpdateResult");
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