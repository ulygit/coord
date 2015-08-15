angular.module('coordApp', [])
  .controller('CoordController', [ '$http', function($http) {
    var event = this;
    event.name = '';

    event.createEvent = function(name) {
      var dataObj = {
    				'name' : name,
    		};
      var res = $http.post('http://localhost:8080/events', dataObj);
    };

    event.createEvent = function(name) {
      var dataObj = {
    				'name' : name,
    		};
      var res = $http.post('http://localhost:8080/events', dataObj);
    };

//    todoList.archive = function() {
//      var oldTodos = todoList.todos;
//      todoList.todos = [];
//      angular.forEach(oldTodos, function(todo) {
//        if (!todo.done) todoList.todos.push(todo);
//      });
//    };
  }]);


angular.module('noteApp', [])
  .controller('Note', function() {
    var note = this;
    note.items = [];

    note.add = function() {
      console.log("before")
      note.items.push({
        inlineChecked: false,
        question: "",
        questionPlaceholder: "foo",
        text: ""
      })
      console.log("after")
    }

  });
