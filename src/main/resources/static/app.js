(function(){

    angular
        .module('market-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'lead/lead.html',
                controller: 'leadController'
            })
            .when('/store', {
                templateUrl: 'products/list/list.html',
                controller: 'productsListController'
            })
            .when('/create_product', {
                templateUrl: 'products/create/create.html',
                controller: 'createProductController'
            })
            .when('/edit_product/:productId', {
                templateUrl: 'products/edit/edit.html',
                controller: 'editProductController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/register', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http) {}

})();

var appController = function ($rootScope, $scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8410/market/api/v1/';

    $scope.tryToAuth = function () {
        $http.post(contextPath + 'auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.webMarketUser) {
            return true;
        } else {
            return false;
        }
    };
}

angular
    .module('market-front')
    .controller('appController', appController);


