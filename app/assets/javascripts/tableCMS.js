(function() {
"use strict";

var tableCMSApp = angular.module('tableCMSApp',[]);

tableCMSApp.directive('tableCms',[function() {
    return {
//        https://www.w3schools.com/angular/angular_directives.asp
//        https://stackoverflow.com/questions/14300986/angularjs-directive-isolated-scope-and-attrs
        restrict: 'E',
        controller: ['$scope', '$http', '$attrs',
        function tableCmsCtrl($scope, $http, $attrs) {
            $scope.matches = [];
            $scope.data = {};
            $http({
                method : 'GET',
                url    : $attrs.getContentUrl,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(
                function(response) {
                    $scope.matches = response.data;
    //                console.log('User Contacts fetched.');
                },
                function(error) {
                    alertify.error('Error in fetching data',JSON.stringify(error.data));
                }
            );
        }],
        templateUrl: '/assets/templates/tableCMS.html'
    };
}]);
})();