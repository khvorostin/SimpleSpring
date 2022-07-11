var cartController = function($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8410/market/api/v1';

    $scope.loadCart = function() {
        $http({
            url: contextPath + '/cart',
            method: 'GET'
        }).then(function (response) {
            console.log(response);
            $scope.cart = response.data.content;
        });
    }

    $scope.loadCart();
}

angular
    .module('market-front')
    .controller('cartController', cartController);