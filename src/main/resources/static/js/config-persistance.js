(function() {
	console.log('Loading configPersistance...');
	angular.module('app').factory('configPersistance',
			configPersistance);

	configPersistance.$inject = [ '$http' ];
	function configPersistance($http) {
		factory.prototype.getSetting = getSetting;
		configItem.prototype.save = save;
		configItem.prototype.getName

		return new factory();

		function factory() {

		}
		function getSetting(name) {
			return new configItem(name);
		}
		function configItem(name) {
			var that = this;
			this.name = name;
			this._loading = true;
			$http.get('/api/settings/search/findByName?name=' + name).then(
					function successCallback(response) {
						angular.forEach(response.data, function(value, key) {
							//Handle boolean conversions
							if(typeof value == "string"){
								if(value.toLowerCase() == "false"){
									value = false;
								}else if(value.toLowerCase() == "true"){
									value = true;
								}
							}
							that[key] = value;
						});
					}, function errorCallback() {
						that._new = true;
					}).finally(function(){
						that._loading = false;
					});
		}
		function save() {
			var that = this;
			this._loading = true;
			if (this._links && this._links.self && typeof this.value != "undefined") {
				var data = {
					"value" : this.value
				};
				$http.patch(this._links.self.href, data).finally(function(){
					that._loading = false;
				});
			} else if (this._new) {
				delete this._new;
				var data = {
					name: this.name,
					value: this.value
				};
				$http.post('/api/settings/', data).then( function successCallback(response) {
					angular.forEach(response.data, function(value, key) {
						that[key] = value;
					});
				}).finally(function(){
					that._loading = false;
				});

			} else {
				console.error("WARN: Unable to save setting.");
				that._loading = false;
			}
		}
	}
})();