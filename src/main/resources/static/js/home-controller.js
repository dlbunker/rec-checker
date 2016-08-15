(function() {
	console.log("Loading homeController...");
	angular.module('app').controller(
			'homeController', homeController);

	homeController.$inject = [ '$scope', 'mineResultResource' ];
	function homeController($scope, mineResultResource) {
		$scope.search = search;
		$scope.searchMode = false;
		$scope.isLoading = false;

		function search() {
			$scope.searchMode = false;
			$scope.isLoading = true;
			console.log($scope.searchParams);
		}
	}

})();
