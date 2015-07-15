/*
 * JSON Object type filter to filter among following types
 * Student
 * School
 */
angular
  .module("studentApp")
  .service("ObjTypeFilterService", function() {
  
  	this.isStudent = isStudent;
  	this.isSchool = isSchool;
  
   function isStudent(obj) {
     return (obj.studentNumber !== undefined);
   }
  
  	function isSchool(obj) {
     return (obj.schoolId !== undefined);
   }
  
});