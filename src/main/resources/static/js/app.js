(function() {
	console.log("Loading app...");
	angular.module('app', [ 'ui.router', 'navController',
			'ngAnimate', 'ui.bootstrap' ])

	// define for requirejs loaded modules
	define('app', [], function() {
		return angular.module('app');
	});

	// function for dynamic load with requirejs of a javascript module for use
	// with a view
	// in the state definition call add property `resolve: req('/views/ui.js')`
	// or `resolve: req(['/views/ui.js'])`
	// or `resolve: req('views/ui')`
	function req(deps) {
		if (typeof deps === 'string')
			deps = [ deps ];
		
		deps_.$inject = [ '$q', '$rootScope' ];
		return {
			deps : deps_
		}

		function deps_($q, $rootScope) {
			var deferred = $q.defer();
			require(deps, function() {
				$rootScope.$apply(function() {
					deferred.resolve();
				});
				deferred.resolve();
			});
			return deferred.promise;
		}
	}

	angular.module('app').config(
			function($stateProvider, $urlRouterProvider, $controllerProvider) {
				var origController = angular.module('app').controller
				angular.module('app').controller = function(name, constructor) {
					$controllerProvider.register(name, constructor);
					return origController.apply(this, arguments);
				}

				var viewsPrefix = 'views/';

				// For any unmatched url, send to /
				$urlRouterProvider.otherwise("/")

				$stateProvider
				// you can set this to no template if you just want to use the
				// html in the page
				.state('home', {
					url : "/",
					templateUrl : viewsPrefix + "home.html",
					data : {
						pageTitle : 'Home'
					}
				}).state('config', {
					url : "/config",
					templateUrl : viewsPrefix + "config.html",
					data : {
						pageTitle : 'Config'
					}
				})
			});
	
	angular.module('app').directive('updateTitle', updateTitle);

	updateTitle.$inject = [ '$rootScope', '$timeout' ];
	function updateTitle($rootScope, $timeout) {
		return {
			link : function(scope, element) {
				var listener = function(event, toState) {
					var title = 'Resource Checker';
					if (toState.data && toState.data.pageTitle)
						title = toState.data.pageTitle + ' - ' + title;
					$timeout(function() {
						element.text(title);
					}, 0, false);
				};

				$rootScope.$on('$stateChangeSuccess', listener);
			}
		};
	}
}());