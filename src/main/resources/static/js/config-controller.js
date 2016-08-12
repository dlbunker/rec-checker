(function() {
	console.log("Loading configController...");
	angular.module('configController', ['configPersistance']).controller('config', navController);

	navController.$inject = [ '$scope', 'configPersistance'];
	function navController($scope, configPersistance) {
		var properties = ['delay','dates','ids','on'];
		
		for(var i = 0, p; i < properties.length; i++){
			p = properties[i];
			$scope[p] = configPersistance.getSetting(p);
			console.log(p);
			console.log($scope[p]);
		}
	}
})();
