angular.module('market').controller('cartController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/cart/api/v1/cart/';
    const orderContextPath = 'http://localhost:5555/core/api/v1/orders/';

    $scope.loadCart = function () {
        $http.get(contextPath + $localStorage.marketWebUserGuestId)
            .then(function (response) {
                $scope.cartContent = response.data;
            });
    };

    $scope.showHelp = function () {
        alert("Войдите под своей учетной записью или зарегистрируйтесь для оформления заказа")
    };

    $scope.deleteProductFromCart = function (id) {
         $http.get(contextPath + $localStorage.marketWebUserGuestId + '/delete/' + id)
                    .then(function (response) {
                        $scope.loadCart();
                    });
     };

     $scope.changeQuantity = function (id, delta) {
             $http({
                 url: contextPath + 'change_quantity',
                 method: 'GET',
                 params: {
                     uuid: $localStorage.marketWebUserGuestId,
                     id: id,
                     delta: delta
                 }
             }).then(function (response) {
                 $scope.loadCart();
             });
     };

    $scope.clearCart = function () {
         $http.get(contextPath + $localStorage.marketWebUserGuestId +  '/clear')
                .then(function (response) {
                    $scope.loadCart();
                });
    };

    $scope.createOrder = function () {
        $http.post(orderContextPath, $scope.orderData)
             .then(function (response) {
                  $scope.orderData = null;
                  $scope.clearCart();
                  $scope.loadCart();
             });
      };


    $scope.loadCart();
});

