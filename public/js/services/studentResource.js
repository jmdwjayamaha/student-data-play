/**
 * Student Resource end-point service.
 */
angular
	.module("studentApp")
	.factory("StudentResource", function($resource){
		
		return $resource("/students/:id", {id : '@id'}, {
			update : {method : 'PUT'}
		});
	});