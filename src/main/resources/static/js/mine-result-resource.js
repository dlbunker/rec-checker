(function() {
	console.log('Loading mineResultResource...');
	angular.module('app').factory('mineResultResource',
			mineResultResource);

	mineResultResource.$inject = [ '$http' ];
	function mineResultResource($http) {
		return new factory();

		function factory() {

		}
	}
})();