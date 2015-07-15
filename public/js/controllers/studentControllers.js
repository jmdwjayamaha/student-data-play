/*
 * Student controller handling student operations
 */
angular
	.module("studentApp")
	.controller("AddStudentController", AddStudentController)
	.controller("ViewStudentController", ViewStudentController)
	.controller("EditStudentController", EditStudentController)
	.controller("ListStudentsController", ListStudentsController);

function AddStudentController($scope, $window, StudentResource) {
  
  $scope.student = new StudentResource();
  $scope.addStudent = function() {
    
	  $scope.student.$save(function() {
		  $window.location.href = "#/students";
	  });
  };
}

function ViewStudentController($scope, $routeParams, StudentResource) {
  
  $scope.student = angular.fromJson(StudentResource.get({ id: $routeParams.id}));
}

function EditStudentController($scope, $routeParams, $window, StudentResource) {
  
  var studentId = $routeParams.id;
  $scope.student = angular.fromJson(StudentResource.get({ id: studentId}));
  
  $scope.updateStudent = function() {
      
	  $scope.student.$update({id: studentId}, function() {
		  $window.location.href = "#/students";
	  });
  };
}

function ListStudentsController($scope, $route, $window, StudentResource) {
  
  $scope.studentsList = StudentResource.query();

  $scope.deleteStudent = function(studentId) {
	  
	  if ($window.confirm("The student will be deleted and cannot be undone !")) {
		  StudentResource.delete({ id: studentId}, function() {
			  $route.reload();
		  });
	  }
  };
}