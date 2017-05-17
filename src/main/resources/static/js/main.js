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
}]), oldFile, allData, position;

app.controller("main", function ($scope, $http, $interval, $window) {
    $scope.file = null;
    $http.get("/exchanger/main/getAllUsers").then(function (response) {
            $scope.allUsers = response.data;
    });

    // Spin getAllActiveUsers

    /*var tick = function() {
         $http.get("/exchanger/main/getActiveUsers").then(function (response) {
             $scope.activeUsers = response.data;
         });
    };
    tick();
    $interval(tick, 10000);*/

    $scope.goForward = function () {
        if (allData instanceof Array){
            if(position < allData.length-1){
                position = position + 1;
                $scope.messages = allData[position];
            }
        }
    };

    $scope.goBack = function () {
        if(position != null){
            if(position != 0) {
                position = position - 1;
                $scope.messages = allData[position];
            }
        }
    };

    $scope.allMessages = function () {
        $http.get("/exchanger/main/allMessages").then(function (response) {
            allData = response.data;
            position = 0;
            $scope.messages = allData[position];
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
        if(oldFile == file){
            $http.post("/exchanger/main/send", message).then(function (response) {
               $scope.msgStatus = response.data;
           });
        } else {
            oldFile = file;
            var fd = new FormData();
             fd.append('file',file);
             $http.post("/exchanger/main/upload", fd, {
                 transformRequest: angular.identity,
                 headers: {'Content-Type':  undefined}
             }).then(function(response) {
                 // save to db
                 message.file = response.data;
                 $http.post("/exchanger/main/send", message).then(function (response) {
                     $scope.msgStatus = response.data;
                 });
             }).catch(function(error) {
                $scope.msgStatus = "Файл занадто великий";
             });
        }
     };
     
     $scope.downloadFile = function (fileName) {
        $http.get("/exchanger/main/download/"+fileName).then(function (response) {
            if (response.status == 200){
                $window.location.href = "/exchanger/main/download/"+fileName;
            }
        });
     };

     $scope.clearSendMenu = function () {
         $scope.msgStatus = null;
         $scope.mes = null;
         var elem = document.querySelector('#uploadFileId');
         angular.element(elem).val(null);
     };

});

app.controller("currentTimeDate", function ($scope,$interval) {
     var tick = function() {
    $scope.clock = Date.now();
  };
  tick();
  $interval(tick, 1000);
});


