(function() {
	console.log("Loading homeController...");
	angular.module('app').controller('homeController', homeController);

	homeController.$inject = [ '$scope', 'mineResultResource' ];
	function homeController($scope, mineResultResource) {
		$scope.search = search;
		$scope.searchMode = false;
		$scope.isLoading = false;

		function search() {
			$scope.searchMode = $scope.searchParams
					&& ($scope.searchParams.date || $scope.searchParams.parkID || $scope.searchParams.entranceID);
			if ($scope.searchMode) {
				
				var params = {};
				if ($scope.searchParams.date){
					params.date;
				}
				if ($scope.searchParams.parkID){
					var parkID = parseInt($scope.searchParams.parkID, 10);
					if($scope.searchParams.parkID == parkID){
						params.parkID = parkID;
					}else{
						params.park = $scope.searchParams.parkID;
					}
				}
				if ($scope.searchParams.entranceID){
					var entranceID = parseInt($scope.searchParams.entranceID, 10);
					if($scope.searchParams.entranceID == entranceID){
						params.entranceID = entranceID;
					}else{
						params.entrance = $scope.searchParams.entranceID;
					}
				}

				$scope.resultSet = mineResultResource.search(params);
				console.log($scope.resultSet);
			}
		}
	}

})();
