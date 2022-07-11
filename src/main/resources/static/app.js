(function(){

    angular
        .module('market-front', ['ngRoute'])
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
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http) {}

})();

var appController = function ($rootScope, $scope, $http) {
    const contextPath = 'http://localhost:8410/market/api/v1/';
}

angular
    .module('market-front')
    .controller('appController', appController);


