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
}]), oldFile, allData;

app.controller("main", function ($scope, $http, $interval, $window) {
    $scope.optionsValue = 1;
    $http.get("/exchanger/main/getAllUsers").then(function (response) {
        $scope.users = response.data;
    });

    /*$http.get("/exchanger/main/receiveMessages").then(function (response) {
            allData = response.data;
            position = 0;
            $scope.messages = allData[position];
            $scope.isReceiveMessage = true;
    });

    $http.get("/exchanger/main/getActiveUsers").then(function (response) {
        console.log(response.data);
    });*/

    // Spin getAllActiveUsers

   /* var tick = function() {
         $http.get("/exchanger/main/getActiveUsers").then(function (response) {
             $scope.activeUsers = response.data;
         });
    };
    tick();
    $interval(tick, 10000);*/

    $scope.fillUser = function (user) {
        $scope.user = user;
        $http.get("/exchanger/main/isSubscriber/"+user.id).then(function (response) {
            $scope.isSubscriber = response.data.isSubscriber;
        });
    };
   
    $scope.getUsers = function (optionsValue) {
      if(optionsValue == 1) {
          $http.get("/exchanger/main/getAllUsers").then(function (response) {
              $scope.users = response.data;});
      } else if (optionsValue == 2){
          $http.get("/exchanger/main/getSubscribers").then(function (response) {
          $scope.users = response.data;});
      } else {
          $http.get("/exchanger/main/getActiveUsers").then(function (response) {
          $scope.users = response.data;});
      }
   };

    $scope.subscribeOn = function (user) {
        $http.post("/exchanger/main/subscribeOn", user);
    };

    $scope.subscribeOff = function (user) {
        $http.post("/exchanger/main/subscribeOff", user);
    };

    $scope.goForward = function () {
        if (allData instanceof Array){
            if($scope.position < allData.length-1){
                $scope.position = $scope.position + 1;
                $scope.messages = allData[$scope.position];
            }
        }
    };

    $scope.goBack = function () {
        if($scope.position != null){
            if($scope.position != 0) {
                $scope.position = $scope.position - 1;
                $scope.messages = allData[$scope.position];
            }
        }
    };
    
    $scope.getSendMessages = function () {
        $http.get("/exchanger/main/"+$scope.senderUserId+"/sendMessages").then(function (response) {
            $scope.showMessages = true;
            allData = response.data;
            $scope.position = 0;
            $scope.messages = allData[$scope.position];
            $scope.isSendMessages = true;
        });
    };

    $scope.getReceiveMessages = function () {
        $http.get("/exchanger/main/"+$scope.senderUserId+"/receiveMessages").then(function (response) {
            $scope.showMessages = true;
            allData = response.data;
            $scope.position = 0;
            $scope.messages = allData[$scope.position];
            $scope.isSendMessages = false;
        });
    };

    $scope.downloadFile = function (message) {
        $http.post("/exchanger/main/downloadMessage", message).then(function (response) {
            if (response.status == 200){
                console.log(response.status);
                //$window.location.href = "/exchanger/main/download/"+message.file;
            }
        });
        /*$http.get("/exchanger/main/download/"+message).then(function (response) {
            if (response.status == 200){
                $window.location.href = "/exchanger/main/download/"+message.file;
            }
        });*/
    };

    $scope.postInfo = function (information, fileName) {
        if (information != null){
            information.userId = $scope.senderUserId;
            information.userFullName = $scope.senderFullName;

            if (oldFile != fileName){
            oldFile = fileName;
            var fd = new FormData();
            fd.append('file',fileName);
            $http.post("/exchanger/main/upload", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type':  undefined},
                params:{
                    'fileType':"Informations"
                }
            }).then(function (response) {
                information.file = response.data;
                $http.post("/exchanger/main/postInformation",information).then(function (response) {
                    $scope.statusPostInformation = response.data;
                });
            }).catch(function () {
                $scope.statusPostInformation = 'Помилка додавання інформації';
            });
            } else {
                $http.post("/exchanger/main/postInformation",information).then(function (response) {
                    $scope.statusPostInformation = response.data;
                });
            }
        } else {
            $scope.statusPostInformation = "Тема повинна бути обов'язково";
        }
    };
    
    $scope.getMyInfo = function () {
      $http.get("/exchanger/main/"+$scope.senderUserId+"/getMyInformations").then(function (response) {
         $scope.showMessages = false;
         allData = response.data;
         $scope.position = 0;
         $scope.informations = allData[$scope.position];
      });
    };

    $scope.getAllInfo = function () {
       $http.get("/exchanger/main//getSubscribersInfo").then(function (response) {
         $scope.showMessages = false;
         allData = response.data;
         $scope.position = 0;
         $scope.informations = allData[$scope.position];
       });
    };

    $scope.sendMessage = function (message, fileName) {
        if (message != null){
            message.senderUserId = $scope.senderUserId;
            message.receiverUserId = $scope.user.id;

            message.senderFullName = $scope.senderFullName;
            message.receiverFullName = $scope.user.subvision +" - ("+$scope.user.surname+" "+$scope.user.name+")";

            if (oldFile != fileName){
            oldFile = fileName;
            var fd = new FormData();
            fd.append('file',fileName);
            $http.post("/exchanger/main/upload", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type':  undefined},
                params:{
                    'fileType':"Messages"
                }
            }).then(function (response) {
                message.file = response.data;
                $http.post("/exchanger/main/sendMessage",message).then(function (response) {
                    $scope.statusSendMessage = response.data;
                });
            }).catch(function () {
                console.log(message);
                $scope.statusSendMessage = 'Помилка відправлення';
            });
            } else {
                $http.post("/exchanger/main/sendMessage", message).then(function (response) {
                    $scope.statusSendMessage = response.data;
                });
            }
        } else {
            $scope.statusSendMessage = "Тема повинна бути обов'язково";
        }
    };

    $scope.clearSendMenu = function () {
         var elem = document.querySelector('#messageUploadFile');
         angular.element(elem).val(null);
         elem = document.querySelector('#messageValue');
         angular.element(elem).val(null);
         elem = document.querySelector('#messageTheme');
         angular.element(elem).val(null);
         $scope.statusSendMessage = null;
    };

    $scope.clearPostMenu = function () {
         var elem = document.querySelector('#infoUploadFile');
         angular.element(elem).val(null);
         elem = document.querySelector('#infoValue');
         angular.element(elem).val(null);
         elem = document.querySelector('#infoTheme');
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


