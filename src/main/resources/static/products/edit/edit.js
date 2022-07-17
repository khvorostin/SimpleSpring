var editProductController = function($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8410/market/api/v1';

    $scope.prepareProductForUpdate = function() {
        $http
            .get(contextPath + '/products/' + $routeParams.productId)
            .then(function successCallback(response) {
                $scope.updated_product = response.data;
            }, function failCallback(response) {
                $location.path('/store');
            });
    }

    $scope.updateProduct = function () {
        $http.put(contextPath + '/products', $scope.updated_product)
            .then(function successCallback(response) {
                    $scope.loadProducts(currentPageIndex);
                    $scope.updated_product = null;
                    $location.path('/store');
                }, function failCallback(response) {
                    alert(response.data.message);
                });
    }

    $scope.prepareProductForUpdate();
}

angular
    .module('market-front')
    .controller('editProductController', editProductController);
