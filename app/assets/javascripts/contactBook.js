(function() {
    'use strict';

var contactBookApp = angular.module('contactBook',['tableCMSApp']);

contactBookApp.controller('ContactBookController',['$scope','$http',
    function($scope,$http){

    $scope.contact = {};
    $scope.openBook = false;

    $scope.test = {
        name : 'Parent'
    };

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

    $scope.showSavedContacts = function($event) {
        if ($event.target.innerText == "Open Book") {
            $scope.openBook = true;
            $event.target.innerText = "Close Book";
        }
        else {
            $scope.openBook = false;
            $event.target.innerText = "Open Book";
        }

    };

  }]);
})();

