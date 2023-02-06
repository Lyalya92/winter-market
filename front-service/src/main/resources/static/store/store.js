angular.module('market').controller('storeController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1/products/';
    const cartContextPath = 'http://localhost:5555/cart/api/v1/cart/';

     $scope.loadProducts = function (page, pageSize) {
             $http({
                    url: contextPath,
                    method: 'GET',
                    params: {
                         page: page,
                         pageSize: pageSize
                    }
                 }).then(function (response) {
                      $scope.products = response.data;
                 });
        };

     $scope.deleteProduct = function (id) {
         http.delete(contextPath + id)
                 .then(function (response) {
                     $scope.loadProducts();
                 });
       };

     $scope.addNewProduct = function () {
          $http.post(contextPath, $scope.newProduct)
                 .then(function (response) {
                     $scope.newProduct = null;
                     $scope.loadProducts();
                 });
       };


     $scope.addProductToCart = function (id) {
          $http.get(cartContextPath + 'add/' + id)
                     .then(function (response) {
                     });
       };


     $scope.loadProducts();
});