/*
 * Controller for handling navigation bar operations
 */
angular
  .module("studentApp")
  .controller("NavigationController", NavigationController);

function NavigationController($scope, $location) {
  
  var currentPath = $location.path().substring(1);
  var seperatorIndex = currentPath.indexOf('/');
  var currentObjType;
  
  if (seperatorIndex > 0) {
    currentObjType = currentPath.substring(0, seperatorIndex);
  } else {
    currentObjType = currentPath.substring(0);
  }
  
  $scope.currentNav =  currentObjType || "students";
  
  $scope.selectStudents = selectStudents;
  $scope.selectSchools = selectSchools;
  
  function selectStudents() {
    $scope.currentNav = "students";
  }
  
  function selectSchools() {
  	 $scope.currentNav = "schools";
  }
}