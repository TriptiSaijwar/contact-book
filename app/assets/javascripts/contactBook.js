(function() {
    'use strict';

var contactBookApp = angular.module('contactBook',[]);

contactBookApp.controller('ContactBookController',['$scope','$http',
    function($scope,$http){

    $scope.contact = {};

    $scope.saveContact = function($event) {
        if (!$scope.contact) {
            alert("Please fill the entries");
            return;
        }
        if (!$scope.contact.email || !$scope.contact.phoneNumber || !$scope.contact.userName || !$scope.contact.address) {
            alert("Do not leave entries blank");
            return;
        }

        var parameter = JSON.stringify($scope.contact);
            $http.post('/addContact/'+ $scope.contact.email, parameter).then(function(response) {
                $scope.contact = {};
                alertify.success("contact added successfully :) ");
             }, function(error) {
                alertify.error('Error in saving contact 😵',JSON.stringify(error.data));
//                alertify.alert('Error in saving contact 😵', JSON.stringify(error.data));
            });
    };

  }]);
})();

