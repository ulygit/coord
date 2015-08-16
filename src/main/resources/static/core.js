angular.module('coordApp', [])
  .controller('CoordController', [ '$http', function($http, $scope) {
    var event = this;
    event.name = '';
    event.contacts = [];

    event.createEvent = function() {
      var dataObj = {
    				'name' : event.name,
    				'contacts' : event.asArray(event.contacts, 'email')
    		};
      var res = $http.post('http://localhost:8080/events', dataObj);
    };

    event.addContact = function(contact) {
        event.contacts.push(angular.copy(contact));
        contact.email = '';
        console.log(this)
    };

    event.asArray = function(objects, field) {
        var arr = [];
        for (var i=0; i < objects.length ; ++i)
            arr.push(objects[i][field]);
        return arr;
    }

  }]);
