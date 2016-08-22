(function() {
	console.log('Loading mineResultResource...');
	angular.module('app').factory('mineResultResource',
			mineResultResource);

	mineResultResource.$inject = [ '$http' ];
	function mineResultResource($http) {
		factory.prototype.search = search;
		
		return new factory();

		function factory() {

		}
		
		function search(params){
			var resultSet = [];
			resultSet._isLoading = true;
			var url = "/api/mineResults/searchv2?";
			var search = [];
			angular.forEach(params, function(value, key){
				search.push([key,'=',value].join(''));
			});
			search = search.join('&');
			$http.get(url + search).then(function(response){
				console.log(response.data);
				for(var i = 0; i < response.data.length; i++){
					resultSet.push(response.data[i]);
				}
			}).finally(function(){
				resultSet._isLoading = false;
			});
			return resultSet;
		}
	}
})();