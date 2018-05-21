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

            $scope.deleteMatch = function(index,$event) {
                var contact = $scope.matches[index];
                $http.delete('/deleteContact/' + contact.email, {}).then(
                    function(response){
                        alertify.success("contact deleted successfully :) ");
                    },
                    function(error){
                        alertify.error('Error in deleting contact 😵',JSON.stringify(error.data));
                });
            };

            $scope.editMatch = function(index,$event) {

            };

        }],
        templateUrl: '/assets/templates/tableCMS.html'
    };
}]);
})();