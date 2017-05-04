/**
 * Created by Divine on 01.05.2017.
 */
var app = angular.module('timeDate', []);

app.controller("currentTimeDate", function ($scope,$interval) {
     var tick = function() {
    $scope.clock = Date.now();
  };
  tick();
  $interval(tick, 1000);
});