/**
 * Created by Divine on 01.05.2017.
 */
angular.module('admin', [])
    .controller("registration", function ($http, $scope) {

    $scope.registration = function (user) {
        $http.post("/registration", user)
            .then(function (response) {
        });
    };
});