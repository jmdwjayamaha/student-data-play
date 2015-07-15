/*
 * School handling operations
 */
angular
	.module("studentApp")
	.controller("AddSchoolController", AddSchoolController)
	.controller("ViewSchoolController", ViewSchoolController)
	.controller("EditSchoolController", EditSchoolController)
	.controller("ListSchoolsController", ListSchoolsController);

function AddSchoolController($scope, $window, LocalStorageService) {
  
  $scope.school = {schoolId: '', name: '', address: '', genderType: "MIXED", email: ''};
  $scope.addSchool = function() {
    LocalStorageService.pushItem("sch_" + $scope.school.schoolId, angular.toJson($scope.school));
    $window.location.href = "#/schools";
  };
}

function ViewSchoolController($scope, $routeParams, LocalStorageService) {
  
  $scope.school = angular.fromJson(LocalStorageService.getItem("sch_" + $routeParams.id));
}

function EditSchoolController($scope, $routeParams, $window, LocalStorageService) {
  
  var schoolId = "sch_" + $routeParams.id;
  $scope.school = angular.fromJson(LocalStorageService.getItem(schoolId));
  
  $scope.updateSchool = function() {
    LocalStorageService.pushItem(schoolId, angular.toJson($scope.school));
    $window.location.href = "#/schools";
  };
}

function ListSchoolsController($scope, $route, LocalStorageService, ObjTypeFilterService) {
  
  var resultsList = LocalStorageService.retrieveData();
  $scope.schoolsList = [];
  
  // Filter the list for schools
  for (var index in resultsList) {
    var currentItem = resultsList[index];
    if (ObjTypeFilterService.isSchool(currentItem)) $scope.schoolsList.push(currentItem);
  }
  
  $scope.deleteSchool = function(schoolId) {
    LocalStorageService.removeItem("sch_" + schoolId);
    $route.reload();
  };
}