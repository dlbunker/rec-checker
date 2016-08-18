(function() {
	console.log("Loading configController...");
	angular.module('app').controller('configController', navController);

	navController.$inject = [ '$scope', 'configPersistance' ];
	function navController($scope, configPersistance) {
		$scope.addSearch = addSearch;
		$scope.removeSearch = removeSearch;

		init();

		function init() {
			var properties = [ 'delay', 'dates', 'searchs', 'on' ];

			for (var i = 0, p; i < properties.length; i++) {
				p = properties[i];
				$scope[p] = configPersistance.getSetting(p);
			}

			$scope.searchs.items = [];
			$scope.searchs._defer.promise.finally(function(){
				$scope.searchs.items = JSON.parse($scope.searchs.value);
			});
		}

		function addSearch() {
			if (currentSearchItem != "") {
				$scope.searchs.items.push($scope.currentSearchItem);
				$scope.currentSearchItem = "";
				saveSearches();
			}
		}

		function removeSearch(index) {
			$scope.searchs.items.splice(index, 1);
			saveSearches();
		}
		
		function saveSearches(){
			$scope.searchs.value = JSON.stringify($scope.searchs.items);
			$scope.searchs.save();
		}
	}
})();
