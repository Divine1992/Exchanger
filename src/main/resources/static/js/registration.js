/**
 * Created by Divine on 04.05.2017.
 */
var app = angular.module("admin", []);

app.controller("registration", function ($window, $scope, $http) {
    
    $scope.registerUser = function (user) {
        $http.post("/registration", user)
            .then(function (response) {
               if(response.status == 200){
                    $window.location.href="/exchanger";
               }
            });
    }
});

app.controller("currentTimeDate", function ($scope,$interval) {
     var tick = function() {
    $scope.clock = Date.now();
  };
  tick();
  $interval(tick, 1000);
});