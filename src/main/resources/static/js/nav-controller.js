(function() {
	console.log("Loading navController...");
	angular.module('navController', []).controller('nav', navController);

	navController.$inject = [ '$scope', '$state' ];
	function navController($scope, $state) {
		$scope.title = 'Project name';

		// returns true if the current router url matches the passed in url
		// so views can set 'active' on links easily
		$scope.isUrl = function(url) {
			if (url === '#')
				return false;
			return ('#' + $state.$current.url.source + '/').indexOf(url + '/') === 0;
		};

		$scope.pages = [ {
			name : 'Home',
			url : '#/'
		}, {
			name : 'About',
			url : '#/about'
		}, {
			name : 'Contact',
			url : '#/contact'
		}, {
			name : 'Theme Example',
			url : '#/theme'
		}, {
			name : 'Blog',
			url : '#/blog'
		}, {
			name : 'Grid',
			url : '#/grid'
		}, {
			name : 'UI',
			url : '#/ui'
		}, {
			name : 'Dropdown Example',
			url : '#',
			subPages : [ {
				name : 'About',
				url : '#/about'
			}, {}, {
				name : 'Header',
			}, {
				name : 'Contact',
				url : '#/contact'
			} ]
		} ]
	}
})();
