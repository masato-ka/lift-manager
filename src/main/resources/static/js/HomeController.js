angular.module("my-app").controller('HomeController', function ($scope, $http, $timeout, SharedStateService) {

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
            this.getDeviceEvent(data[0]['liftId']);
            this.getDeviceItem(data[0]['liftId']);
            this.getDeviceSchedule(data[0]['liftId']);
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
            this.getDeviceEvent($scope.selectedLiftId);
            this.getDeviceItem($scope.selectedLiftId);
            this.getDeviceSchedule($scope.selectedLiftId);
        });
    };

    $scope.load = function ($done) {
        $timeout(function () {
            getDeviceEvent($scope.data.liftId);
            $done();
        }.bind(this), 1000);
    }.bind(this);


    getDeviceEvent = function (liftId) {
        $http({
            method: 'GET',
            url: '/api/v1/devices/' + liftId + '/events'
        }).success(function (data, status, headers, config) {
            $scope.data.event = [];
            for (var d of data) {
                var event = {
                    datetime: d['eventTime'],
                    itemStatus: d['itemStatus'],
                    liftStatus: d['liftStatus'],
                    weight: d['weight']
                }
                $scope.data.event.unshift(event);
                if (d['liftStatus'] === "UP") {
                    $scope.data.imageLift = "images/up.png";
                } else {
                    $scope.data.imageLift = "images/down.png";
                }

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

    $scope.load = function ($done) {
        $timeout(function () {
            getDeviceEvent($scope.data.liftId);
            getDeviceSchedule($scope.data.liftId);
            getDeviceItem($scope.data.liftId);
            $done();
        }.bind(this), 1000);
    }.bind(this);

});
