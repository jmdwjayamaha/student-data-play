/*
 * Define studentApp angular module 
 */
angular
  	.module("studentApp", ["ngRoute", "ngResource"])
	.config(["$routeProvider", setupRoutes])
	.run(setAuthorizationHeader);

// Route configurations
function setupRoutes($routeProvider) {
  $routeProvider
  	.when("/students/new", {
   	templateUrl : "views/addStudent.html",
    	controller: "AddStudentController"
  	})
   .when("/students/:id", {
      templateUrl : "views/viewStudent.html",
      controller : "ViewStudentController"
   })
  	.when("/students/:id/edit", {
      templateUrl : "views/editStudent.html",
      controller : "EditStudentController"   
   })
   .when("/students", {
   	templateUrl : "views/listStudents.html",
      controller : "ListStudentsController"
   })
  
   .when("/schools/new", {
   	templateUrl : "views/schools/addSchool.html",
    	controller: "AddSchoolController"
  	})
   .when("/schools/:id", {
      templateUrl : "views/schools/viewSchool.html",
      controller : "ViewSchoolController"
   })
  	.when("/schools/:id/edit", {
      templateUrl : "views/schools/editSchool.html",
      controller : "EditSchoolController"   
   })
   .when("/schools", {
      templateUrl : "views/schools/listSchools.html",
      controller : "ListSchoolsController"
   })
  
   .otherwise({
     	redirectTo: "/students"
   });
}
	
function setAuthorizationHeader($http) {
	$http.defaults.headers.common.Authorization = 'Token 123';
}
