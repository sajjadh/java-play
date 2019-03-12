var form1 = angular.module('createBook', []);
createBook.controller('bookList', function($scope) {
    $scope.addToBookList = function(addBook){
        if (!(addBook === undefined || addItem === '')){
            $scope.lst.push({item: addItem, needed: false});
            $scope.NoItemError = '';
        } else {
            $scope.NoItemError = 'Please enter an item';
        }
    }
})


