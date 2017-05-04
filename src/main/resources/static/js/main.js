/**
 * Created by Divine on 28.04.2017.
 */
var app = angular.module("app", [])
    .directive('fileModel', ['$parse', function ($parse) {   //parse input="file" and set file object into $scope
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

app.controller("upload", function ($scope, $http) {
    $scope.fileName = null;

    $scope.uploadFile = function (file) {
      var fd = new FormData();
      fd.append('file',file);
      $http.post("/upload", fd, {
          transformRequest: angular.identity,
          headers: {'Content-Type':  undefined}
        }).then(function (response) {
            $scope.fileName = null;
      });
    };
});


