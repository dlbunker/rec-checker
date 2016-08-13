(function() {
	console.log("Loading homeController...");
	angular.module('homeController', ['ngAnimate']).controller('home', homeController);

	homeController.$inject = [ '$scope' ];
	function homeController($scope) {
		$scope.search = search;
		$scope.searchMode = false;
		$scope.isLoading = false;
		
		function search(){
			$scope.searchMode = !$scope.searchMode;
			$scope.isLoading = true;
		}
	}
	
})();
