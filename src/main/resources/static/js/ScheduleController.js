angular.module("my-app").controller("ScheduleController", function ($scope, $http, $filter, $timeout, SharedStateService) {

    $scope.data = SharedStateService;
    $scope.scheduleDate = $filter('date')(new Date(), "yyyy-MM-ddTHH:mm:ss");
    $scope.scheduleName = "";
    $scope.scheduleDescription = "";
    $scope.showInputFeeld = false;

    $scope.new_schedules = [];

    $scope.delete = function (scheduleId) {
        $http({
            method: 'DELETE',
            url: '/api/v1/devices/' + $scope.data.liftId + '/schedules/' + scheduleId,
            header: {"Content-Type": "application/json"}

        }).success(function (data, status, headers, config) {
            $scope.data.schedules.filter(function (element, index, array) {
                return (element['scheduleId'] != scheduleId);
            })
        }).error(function (data, status, headers, config) {
            ons.notification.alert("Failed Update item name");
        })
    };

    $scope.update = function () {
        $http({
            method: 'POST',
            url: '/api/v1/devices/' + $scope.data.liftId + '/schedules',
            header: {"Content-Type": "application/json"},
            data: JSON.stringify({
                "api": "upLift",
                "date": $scope.scheduleDate + ".000",
                "description": $scope.scheduleDescription,
                "name": $scope.scheduleName,
                "status": "create"
            })
        }).success(function (data, status, headers, config) {
            getDeviceSchedule($scope.data.liftId);
            $scope.showInputFeeld = false;
        }).error(function (data, status, headers, config) {
            ons.notification.alert("Failed create job");
        })
    };

    $scope.addNewCard = function () {
        $scope.scheduleDate = $filter('date')(new Date(), "yyyy-MM-ddTHH:mm:ss");
        $scope.scheduleName = "";
        $scope.scheduleDescription = "";
        $scope.showInputFeeld = true;
    }

    $scope.cancelCard = function () {
        $scope.showInputFeeld = false;
    }

    $scope.load = function ($done) {
        $timeout(function () {
            getDeviceSchedule($scope.data.liftId);
            $done();
        }.bind(this), 1000);
    }.bind(this);


    getDeviceSchedule = function (liftId) {
        $http({
            method: 'GET',
            url: '/api/v1/devices/' + liftId + '/schedules'
        }).success(function (data, status, headers, config) {
            $scope.data.schedules = data;
        })
    }

});