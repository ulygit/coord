var coord = angular.module('coordApp', []);

coord.controller('CoordController', [ '$http', function($http) {
    var event = this;
    event.name = '';
    event.contacts = [{}];

    event.submitEvent = function() {
        classify = function(contact) {
            modcontact = {};
            if (contact.type === 'email') {
                modcontact.email = contact.emailOrPhone;
            } else if (contact.type === 'phone') {
                modcontact.phone = contact.emailOrPhone;
            }
            return modcontact;
        }

        deleteUnknown = function(contact) {
            return 'email' in contact || 'phone' in contact;
        }
      var dataObj = {
    				'name' : event.name,
    				'contacts' : event.contacts.map(classify)
    		};
      var res = $http.post('http://localhost:8080/events', dataObj);
    };

    event.addElement = function() {
        event.contacts.push({});
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
