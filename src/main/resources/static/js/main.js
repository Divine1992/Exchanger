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

app.controller("sortController", function ($scope) {
   $scope.sortType= "sendDate";
   $scope.sortReverse= true;
   $scope.searchSurname= '';
});

app.controller("main", function ($scope, $http, $interval, $window) {
    $http.get("/main/getAllUsers").then(function (response) {
            $scope.users = response.data;
        }
    );

    $scope.deleteInformation = function (id) {
      $http.get("/main/deleteInformation/"+id).then(function (response) {
         $scope.headShow = true;
         $scope.showMessages = false;
         $scope.isMyInfo = true;
         allData = response.data;
         $scope.position = 0;
         $scope.informations = allData[$scope.position];
      });
    };

    $scope.deleteMessage = function (id) {
      $http.get("/main/deleteMessage/"+id).then(function (response) {
         $scope.headShow = true;
            $scope.showMessages = true;
            allData = response.data;
            $scope.position = 0;
            $scope.messages = allData[$scope.position];
            $scope.isSendMessages = true;
      });
    };

    $scope.fillUser = function (user) {
        $scope.user = user;
        $http.get("/main/isSubscriber/"+user.id).then(function (response) {
            $scope.isSubscriber = response.data.isSubscriber;
        });
    };
   
    $scope.getUsers = function (optionsValue) {
      if(optionsValue == 1) {
          $http.get("/main/getAllUsers").then(function (response) {
            $scope.users = response.data;});
      } else if (optionsValue == 2){
          $http.get("/main/getSubscribers").then(function (response) {
            $scope.users = response.data;});
      } else {
          $http.get("/main/getActiveUsers").then(function (response) {
            $scope.users = response.data;});
      }
   };

    $scope.subscribeOn = function (user) {
        $http.post("/main/subscribeOn", user);
    };

    $scope.subscribeOff = function (user) {
        $http.post("/main/subscribeOff", user);
    };

    $scope.goForward = function () {
        if (allData instanceof Array){
            if($scope.position < allData.length-1){
                $scope.position = $scope.position + 1;
                $scope.messages = allData[$scope.position];
                $scope.informations = allData[$scope.position];
            }
        }
    };

    $scope.goBack = function () {
        if($scope.position != null){
            if($scope.position != 0) {
                $scope.position = $scope.position - 1;
                $scope.messages = allData[$scope.position];
                $scope.informations = allData[$scope.position];
            }
        }
    };
    
    $scope.getSendMessages = function () {
        $http.get("/main/"+$scope.senderUserId+"/sendMessages").then(function (response) {
            $scope.headShow = true;
            $scope.showMessages = true;
            allData = response.data;
            $scope.position = 0;
            $scope.messages = allData[$scope.position];
            $scope.isSendMessages = true;
        });
    };

    $scope.getReceiveMessages = function () {
        $http.get("/main/"+$scope.senderUserId+"/receiveMessages").then(function (response) {
            $scope.headShow = true;
            $scope.showMessages = true;
            allData = response.data;
            $scope.position = 0;
            $scope.messages = allData[$scope.position];
            $scope.isSendMessages = false;
        });
    };

    $scope.postInfo = function (information, fileName) {
        if (information != null){
            information.userId = $scope.senderUserId;
            information.userFullName = $scope.senderFullName;

            if (oldFile != fileName){
            oldFile = fileName;
            var fd = new FormData();
            fd.append('file',fileName);
            $http.post("/main/uploadFile", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type':  undefined}
            }).then(function (response) {
                information.file = response.data.file;
                $http.post("/main/postInformation",information).then(function (response) {
                    $scope.isAlertShow = response.data;
                    $scope.isSuccess = response.data.isSuccess;
                    $scope.body = response.data.body;
                });
            })} else {
                $http.post("/main/postInformation",information).then(function (response) {
                    $scope.isAlertShow = response.data;
                    $scope.isSuccess = response.data.isSuccess;
                    $scope.body = response.data.body;
                });
            }
        }
    };
    
    $scope.getMyInfo = function () {
      $http.get("/main/"+$scope.senderUserId+"/getMyInformations").then(function (response) {
         $scope.headShow = true;
         $scope.showMessages = false;
         $scope.isMyInfo = true;
         allData = response.data;
         $scope.position = 0;
         $scope.informations = allData[$scope.position];
      });
    };

    $scope.getAllInfo = function () {
       $http.get("/main//getSubscribersInfo").then(function (response) {
         $scope.headShow = true;
         $scope.showMessages = false;
         $scope.isMyInfo = false;
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
            $http.post("/main/uploadFile", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type':  undefined}
            }).then(function (response) {
                message.file = response.data.file;
                $http.post("/main/sendMessage",message).then(function (response) {
                    $scope.isAlertShow = response.data;
                    $scope.isSuccess = response.data.isSuccess;
                    $scope.body = response.data.body;
                });
            })} else {
                $http.post("/main/sendMessage", message).then(function (response) {
                    $scope.isAlertShow = response.data;
                    $scope.isSuccess = response.data.isSuccess;
                    $scope.body = response.data.body;
                });
            }
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


