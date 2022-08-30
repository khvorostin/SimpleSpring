var registrationController = function($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8410/market/api/v1';

    $scope.createNewUser = function () {
        if ($scope.new_user == null) {
            alert('Форма не заполнена');
            return;
        }

        $http.post(contextPath + '/auth/register', $scope.new_user)
            .then(function successCallback(response) {
                    $scope.loadProducts(currentPageIndex);
                    $scope.new_user = null;
                    $location.path('/store');
                }, function failCallback(response) {
                    alert(response.data.message);
                }
            );
    }
}

angular
    .module('market-front')
    .controller('registrationController', registrationController);
