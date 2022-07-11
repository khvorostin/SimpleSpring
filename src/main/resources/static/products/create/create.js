var createProductController = function($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8410/market/api/v1';

    $scope.createNewProduct = function () {
        if ($scope.new_product == null) {
            alert('Форма не заполнена');
            return;
        }

        $http.post(contextPath + '/products', $scope.new_product)
            .then(function successCallback(response) {
                    $scope.loadProducts(currentPageIndex);
                    $scope.new_product = null;
                    $location.path('/store');
                }, function failCallback(response) {
                    alert(response.data.message);
                }
            );
    }
}

angular
    .module('market-front')
    .controller('createProductController', createProductController);
