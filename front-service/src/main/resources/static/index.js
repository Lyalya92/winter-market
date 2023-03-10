(function () {
    angular
        .module('market', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
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
        if (!$localStorage.marketWebUserGuestId) {
           $http.get('http://localhost:5555/cart/api/v1/cart/generate_uuid')
                     .then(function (response) {
                          $localStorage.marketWebUserGuestId = response.data.value;
                          });
           }
    }
})();

angular.module('market').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {
    const authContextPath = 'http://localhost:5555/auth/authenticate/';

    $scope.tryToAuth = function () {
        $http.post(authContextPath, $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marketWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.mergeCart();
                    $location.path('/');
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
        $location.path('/');
    };

    $scope.mergeCart = function () {
    $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.marketWebUserGuestId + '/merge_cart')
              .then(function (response) {
              });
    };

    $scope.clearUser = function () {
        delete $localStorage.marketWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.marketWebUser) {
            return true;
        } else {
            return false;
        }
    };
});