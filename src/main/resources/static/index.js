angular.module('market', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    $scope.loadProducts = function () {
         $http.get(contextPath + '/products')
             .then(function (response) {
                 $scope.products = response.data;
             });
     }

     $scope.showProductInfo = function (productId) {
        $http.get(contextPath + '/products/' + productId)
                     .then(function (response) {
                         console.log(response.data);
                         alert(response.data.title)
                     });
     }


    $scope.showCart = function () {
        $http.get(contextPath + '/cart')
            .then(function (response) {
                $scope.cartContent = response.data;
            });
    }

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + '/products/' + id)
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    $scope.addNewProduct = function () {
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.loadProducts();
            });
    }


    $scope.addProductToCart = function (id) {
         $http.get(contextPath + '/cart/add/' + id)
                .then(function (response) {
                    $scope.showCart();
                });
    }

     $scope.deleteProductFromCart = function (id) {
         $http.get(contextPath + '/cart/delete/' + id)
                    .then(function (response) {
                        $scope.showCart();
                    });
     }

     $scope.changeQuantity = function (id, delta) {
             $http({
                 url: contextPath + '/cart/change_quantity',
                 method: 'GET',
                 params: {
                     id: id,
                     delta: delta
                 }
             }).then(function (response) {
                 $scope.showCart();
             });
     }

    $scope.clearCart = function () {
         $http.delete(contextPath + '/cart')
                .then(function (response) {
                    $scope.showCart();
                });
    }

    $scope.loadProducts();
    $scope.showCart();
});