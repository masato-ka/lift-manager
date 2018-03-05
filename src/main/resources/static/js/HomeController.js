angular.module("my-app").controller('HomeController', function ($scope, $http, SharedStateService) {

    $scope.data = SharedStateService;

    //Initial request.
    $http({
        method: 'GET',
        url: '/api/v1/devices'
    }).success(function (data, status, headers, config) {
        $scope.devices = data;
        if (data.length < 0) {
            ons.notification.alert("You don't registrate your device.");
        } else {
            $scope.data.liftId = data[0]['liftId'];
            $scope.data.deviceId = data[0]['deviceId'];
            getDeviceEvent(data[0]['liftId']);
            getDeviceItem(data[0]['liftId']);
            getDeviceSchedule(data[0]['liftId']);
        }
    }).error(function (data, status, headers, config) {
        ons.notification.alert('Failed request.');
    });

    $scope.onSelect = function () {
        $http({
            method: 'GET',
            url: '/api/v1/devices/' + $scope.selectedLiftId
        }).success(function (data, status, headers, config) {
            $scope.data.liftId = data['liftId'];
            $scope.data.deviceId = data['deviceId'];
            getDeviceEvent($scope.selectedLiftId);
            getDeviceItem($scope.selectedLiftId);
            getDeviceSchedule($scope.selectedLiftId);
        });
    };

    getDeviceEvent = function (liftId) {
        $http({
            method: 'GET',
            url: '/api/v1/devices/' + liftId + '/events'
        }).success(function (data, status, headers, config) {
            if (data.length <= 0) {
                $scope.data.imageLift = "images/down.png";
                $scope.data.liftStatus = "DOWN";
                $scope.data.itemStatus = "TAKEN";
                $scope.data.item.weight = 0;
            } else {
                if (data[0]['liftStatus'] == "UP") {
                    $scope.data.imageLift = "images/up.png";
                } else {
                    $scope.data.imageLift = "images/down.png";
                }
                $scope.itemStatus = data[0]['liftStatus'];
                $scope.data.liftStatus = data[0]['liftStatus'];
                $scope.data.item.weight = data[0]['weight'];
            }
        }).error(function (data, status, headers, config) {
            ons.notification.alert("Failed get device details.");
        });

    }

    getDeviceItem = function (liftId) {
        $http({
            method: 'GET',
            url: '/api/v1/devices/' + liftId + '/item'
        }).success(function (data, status, headers, config) {
            $scope.data.item.name = data['itemName'];
        }).error(function (data, status, headers, config) {
            ons.notification.alert("Failed get Item Information");
        });
    }

    getDeviceSchedule = function (liftId) {
        $http({
            method: 'GET',
            url: '/api/v1/devices/' + liftId + '/schedules'
        }).success(function (data, status, headers, config) {
            $scope.data.schedules = data;
        })
    }

});
