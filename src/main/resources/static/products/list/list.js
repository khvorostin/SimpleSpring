var productsListController = function($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8410/market/api/v1';

    $scope.loadProducts = function(pageIndex = 1) {
        performLoadProducts($scope, $http, contextPath, pageIndex);
    }

    $scope.createNewProduct = function() {
        $location.path('/create_product');
    }

    $scope.editItem = function(productId) {
        $location.path('/edit_product/' + productId);
    }

    $scope.dropItem = function(productId) {
        $http.delete(contextPath + '/products/' + productId);
        $location.path('/store');
    }

    $scope.addItemToCart = function(productId) {
        $http.post(contextPath + '/cart/' + productId);
        $location.path('/cart');
    }

    $scope.dropItemFromCart = function(productId) {
        $http.delete(contextPath + '/cart/' + productId);
        $location.path('/cart');
    }

    $scope.loadProducts(1);
}

var performLoadProducts = function($scope, $http, contextPath, pageIndex) {
    $http({
        url: contextPath + '/products',
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

angular
    .module('market-front')
    .controller('productsListController', productsListController);
