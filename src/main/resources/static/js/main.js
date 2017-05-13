
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

app.controller("main", function ($scope, $http, $interval) {
    $scope.user = null;
    $scope.fileName = null;
   /* var tick = function() {
    $http.get("/exchanger/allMessages").then(function (response) {
            $scope.messages = response.data;
        });
    };
    tick();
    $interval(tick, 30000);*/


    $scope.allMessages = function () {
        $http.get("/exchanger/main/allMessages").then(function (response) {
            $scope.messages = response.data;
        });
    };

     $scope.sendMessages = function () {
        $http.get("/exchanger/main/sendMessages").then(function (response) {
            $scope.messages = response.data;
        });
    };

     $scope.receiveMessages = function () {
        $http.get("/exchanger/main/receiveMessages").then(function (response) {
            $scope.messages = response.data;
        });
    };
    
     $scope.send = function (message, file) {
      //upload file
         if (file == null){
           $http.post("/exchanger/main/send", message);
         } else {
             var fd = new FormData();
             fd.append('file',file);
             $http.post("/exchanger/main/upload", fd, {
                 transformRequest: angular.identity,
                 headers: {'Content-Type':  undefined}
             }).then(function (response) {
                 // save to db
                 message.file = response.data;
                 $http.post("/exchanger/main/send", message);
             });
         }
     };
});

app.controller("currentTimeDate", function ($scope,$interval) {
     var tick = function() {
    $scope.clock = Date.now();
  };
  tick();
  $interval(tick, 1000);
});


