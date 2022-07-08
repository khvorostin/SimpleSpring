var appController = function ($scope, $http) {

    const contextPath = 'http://localhost:8410/market/';
    $scope.pageTitle = 'Каталог';

    $scope.loadProducts = function(pageIndex = 1) {
        performLoadProducts($scope, $http, contextPath, pageIndex);
    }

    $scope.dropItem = function (product) {
        performDropProduct($scope, $http, contextPath, product);
        $scope.loadProducts(1);
    }

    $scope.loadProducts(1);
}

var performLoadProducts = function($scope, $http, contextPath, pageIndex) {
    $http({
        url: contextPath + 'products',
        method: 'GET',
        params: {
            p: pageIndex
        }
    }).then(function (response) {
        console.log(response);
        $scope.products = response.data.content;
        $scope.pages = new Array();
        var i = 0;
        while (i < response.data.totalPages) {
            $scope.pages[i] = ++i;
        }
        $scope.totalPages = response.data.totalPages;
        $scope.currPage = response.data.number + 1;

        console.log($scope.pages);
    });
}

var performDropProduct = function($scope, $http, contextPath, product) {
    $http.get(contextPath + 'products/delete/' + product.id);
}

angular
    .module('market-front', [])
    .controller('appController', appController);
