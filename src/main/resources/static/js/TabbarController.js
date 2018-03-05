angular.module("my-app").controller('TabbarController', function ($scope, $http, SharedStateService) {

    $scope.data = SharedStateService;

    $scope.title = 'Home';
    $scope.updateTitle = function ($event) {
        $scope.title = angular.element($event.tabItem).attr('label');
        $scope.$apply();

    };
})