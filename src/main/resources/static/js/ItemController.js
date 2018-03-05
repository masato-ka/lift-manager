angular.module("my-app").controller("ItemController", function ($scope, $http, SharedStateService) {

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

});
