angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/market/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marketWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
    };

    $scope.clearUser = function () {
        delete $localStorage.marketWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.marketWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.isAdmin = function () {
        return false;
//            if ($localStorage.marketWebUser) {
//                return true;
//            } else {
//                return false;
//            }
        };

    $scope.showHelp = function () {
        alert("Войдите под своей учетной записью или зарегистрируйтесь для оформления заказа")
    }

    if ($localStorage.marketWebUser) {
        try {
            let jwt = $localStorage.marketWebUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!");
                $scope.clearUser;
            }
        } catch (e) {
        }

        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketWebUser.token;

    }

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
        $http.get('http://localhost:8190/market-carts/api/v1/cart')
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
         $http.get('http://localhost:8190/market-carts/api/v1/cart/add/' + id)
                .then(function (response) {
                    $scope.showCart();
                });
    }

     $scope.deleteProductFromCart = function (id) {
         $http.get('http://localhost:8190/market-carts/api/v1/cart/delete/' + id)
                    .then(function (response) {
                        $scope.showCart();
                    });
     }

     $scope.changeQuantity = function (id, delta) {
             $http({
                 url: 'http://localhost:8190/market-carts/api/v1/cart/change_quantity',
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
         $http.delete('http://localhost:8190/market-carts/api/v1/cart')
                .then(function (response) {
                    $scope.showCart();
                });
    }

    $scope.createOrder = function () {
        $http.post(contextPath + '/orders', $scope.orderData)
                    .then(function (response) {
                        $scope.orderData = null;
                        $scope.clearCart();
                        $scope.showCart();
                    });
    }

    $scope.loadProducts();
    $scope.showCart();
});