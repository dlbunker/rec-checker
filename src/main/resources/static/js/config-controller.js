(function() {
	console.log("Loading configController...");
	angular.module('app').controller('configController', navController);

	navController.$inject = [ '$scope', 'configPersistance' ];
	function navController($scope, configPersistance) {
		$scope.addSearch = addSearch;
		$scope.removeSearch = removeSearch;
		$scope.selectElement = selectElement;

		init();

		function init() {
			var properties = [ 'delay', 'dates', 'searches', 'on' ];

			for (var i = 0, p; i < properties.length; i++) {
				p = properties[i];
				$scope[p] = configPersistance.getSetting(p);
			}

			$scope.searches.items = [];
			$scope.searches._defer.promise.finally(function(){
				$scope.searches.items = JSON.parse($scope.searches.value);
			});
		}

		function addSearch() {
			if (currentSearchItem != "") {
				$scope.searches.items.push($scope.currentSearchItem);
				$scope.currentSearchItem = "";
				saveSearches();
			}
		}

		function removeSearch(index) {
			$scope.currentSearchItem = $scope.searches.items[index];
			$scope.searches.items.splice(index, 1);
			saveSearches();
		}
		
		function saveSearches(){
			$scope.searches.value = JSON.stringify($scope.searches.items);
			$scope.searches.save();
		}
		
		function selectElement(event){
			var input = event.target;
			if(input && typeof input != 'undefined' && input.value.length && typeof input.setSelectionRange == 'function'){
				input.setSelectionRange(0, input.value.length);
			}
		}
	}
})();
