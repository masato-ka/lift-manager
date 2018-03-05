angular.module("my-app").controller("ItemController", function ($scope, $http, $timeout, SharedStateService) {

    $scope.data = SharedStateService;

    $scope.update = function () {
        $http({
            method: 'POST',
            url: '/api/v1/devices/' + $scope.data.liftId + '/item',
            header: {"Content-Type": "application/json"},
            data: JSON.stringify({
                "itemName": $scope.data.item.name
            })
        }).success(function (data, status, headers, config) {
            $scope.data.item.name = data["itemName"];
            ons.notification.alert('Sccess update item');
        }).error(function (data, status, headers, config) {
            ons.notification.alert("Failed Update item name");
        })
    };

    $scope.load = function ($done) {
        $timeout(function () {
            getDeviceEvent($scope.data.liftId);
            $done();
        }.bind(this), 1000);
    }.bind(this);

    /*    getDeviceEvent = function (liftId) {
            $http({
                method: 'GET',
                url: '/api/v1/devices/' + liftId + '/events'
            }).success(function (data, status, headers, config) {
                if (data.length <= 0) {
                    $scope.data.imageLift = "images/down.png";
                    $scope.data.itemStatus = "TAKEN";
                    $scope.data.liftStatus = "DOWN";
                    $scope.data.item.weight = 0;
                } else {
                    if (data[0]['liftStatus'] == "UP") {
                        $scope.data.imageLift = "images/up.png";
                    } else {
                        $scope.data.imageLift = "images/down.png";
                    }
                    $scope.itemStatus = data[0]['itemStatus'];
                    $scope.data.liftStatus = data[0]['liftStatus'];
                    $scope.data.item.weight = data[0]['weight'];
                }
            }).error(function (data, status, headers, config) {
                ons.notification.alert("Failed get device details.");
            });

        }
    */

});
