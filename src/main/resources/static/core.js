var coord = angular.module('coordApp', []);

coord.controller('CoordController', [ '$http', function($http) {
    var event = this;
    event.name = '';
    event.contacts = [{}];

    event.submitEvent = function() {
      var dataObj = {
    				'name' : event.name,
    				'contacts' : event.asArray(event.contacts, 'email')
    		};
      var res = $http.post('http://localhost:8080/events', dataObj);
    };

    event.addElement = function() {
        event.contacts.push({});
    }

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

coord.directive('autoFocus', function($timeout) {
    return {
        restrict: 'AC',
        link: function(_scope, _element) {
            $timeout(function(){
                _element[0].focus();
            }, 0);
        }
    };
});
