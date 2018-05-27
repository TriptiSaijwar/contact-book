(function() {
"use strict";

var tableCMSApp = angular.module('tableCMSApp',[]);

tableCMSApp.directive('tableCms',[function() {
    return {
//        https://www.w3schools.com/angular/angular_directives.asp
//        https://stackoverflow.com/questions/14300986/angularjs-directive-isolated-scope-and-attrs
        restrict: 'E',
        scope: {
            test: '='
        },
        controller: ['$scope', '$http', '$attrs',
        function tableCmsCtrl($scope, $http, $attrs) {
            $scope.matches = [];
            $scope.data = {};
            $scope.isEditable = false;
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
                        $scope.matches.splice(index,1);
                        alertify.success("contact deleted successfully :) ");
                    },
                    function(error){
                        alertify.error('Error in deleting contact 😵',JSON.stringify(error.data));
                });
            };

            $scope.editMatch = function(index,$event) {
                $scope.data = angular.copy($scope.matches[index]);
                $scope.matches[index].isEditing = true;
                $scope.test.name = 'Child';
            };

            $scope.saveEditableMatch = function(index,$event,editNewMatch) {

                if (!editNewMatch) {
                    $scope.matches[index].isEditing = false;
                    $scope.data = {};
                    return;
                }

                if (!$scope.data) {
                    alert("Please fill the entries");
                    return;
                }
                if (!$scope.data.email || !$scope.data.phoneNumber || !$scope.data.userName || !$scope.data.address) {
                    alert("Do not leave entries blank");
                    return;
                }
                if ($scope.matches[index].isEditing && editNewMatch) {
                    $http.put('/editContact/' + $scope.data.email, $scope.data).then(function(response){
                        $scope.matches[index] = response.data;
                        $scope.data = {};
                        $scope.matches[index].isEditing = false;
                        alertify.success("contact edited successfully :) ");
                    },function(error){
                        $scope.data = {};
                        $scope.matches[index].isEditing = true;
                        alertify.error('Error in editing contact 😵',JSON.stringify(error.data));
                    });
                }
            };

        }],
        templateUrl: '/assets/templates/tableCMS.html'
    };
}]);
})();