angular.module('market-front', []).controller('appController', function ($scope, $http) {
    const contextPath = 'http://localhost:8410/market/';

    $scope.loadProducts = function(pageIndex = 1) {
        $http({
            url: contextPath + 'products',
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response) {
            console.log(response);
            $scope.products = response.data;
        });
    }

    $scope.dropItem = function (product) {
        $http.get(contextPath + 'products/delete/' + product.id);
        $scope.loadProducts(1);
    }

    $scope.loadProducts(1);
});
