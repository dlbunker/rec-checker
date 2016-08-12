(function() {
	console.log("Loading configController...");
	angular.module('configController', ['configPersistance']).controller('config', navController);

	navController.$inject = [ '$scope', 'configPersistance'];
	function navController($scope, configPersistance) {
		$scope.delay = configPersistance.getSetting('delay');
		$scope.dates = configPersistance.getSetting('dates');
		console.log($scope.dates);
		$scope.ids = configPersistance.getSetting('ids');
	}
})();
