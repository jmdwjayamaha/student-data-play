/*
 * Local Storage Service
 */
angular
  .module("studentApp")
  .service("LocalStorageService", function() {
    this.pushItem = pushItem;
	 this.removeItem = removeItem;
    this.getItem = getItem;
    this.retrieveData = retrieveData;
    
    function pushItem(key, value) {
        localStorage.setItem(key, value);
    };
  
    function removeItem(key) {
        localStorage.removeItem(key);
    };
  
    function getItem(key) {
        return localStorage.getItem(key);
    }; 
  
    function retrieveData() {
        var resultsArray = [];
        for (var key in localStorage) {
            var value = localStorage.getItem(key);
            resultsArray.push(angular.fromJson(value));
        }
      
        return resultsArray;
    };
});