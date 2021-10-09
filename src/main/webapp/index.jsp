<%-- 
    Document   : index
    Created on : 5/10/2021, 07:19:40 PM
    Author     : o
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tienda Virtual</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
        
    </head>
    
    <style type="text/css">
        .messages {
            color: #FA787E;
        }
        form.ng-submitted input.ng-invalid {
            border-color: #FA787E;
        }
        form input.ng-invalid.ng-touched {
            border-color: #FA787E;
        }
    </style>
    <body>        
        <div class = “container-fluid” ng-app="TiendaVirtual" ng-controller="productosController as cn">
            <form name="userForm" novalidate>
                <div class="row">
                    <div class="col-12">
                        <center><h1>Tienda Virtual - Misión TIC 2022</h1></center> 
                        <center><h2>Formulario Productos</h2></center>                        
                    </div> 
                </div>
                 <h3>Sección 1</h3>
                <div class="row">
                    <div class="col-12">                        
                        <div class="row">
                            <div class="col-6">
                                <label>Codigo</label>
                                <input  name="identificacion" class="form-control" type="number" min="0" ng-model="cn.codigo" ng-model-options="{updateOn: 'blur'}" required>
                            </div>
                            <div class="col-6">
                                 <label>Nombre</label>
                                 <input  name="nombre" class="form-control" type="text" ng-model="cn.nombre" ng-model-options="{updateOn: 'blur'}" required>
                            </div>
                        </div>                    
                    <div class="row">
                            <div class="col-6">
                                <label>Cantidad</label>
                                <input  name="cantidad" class="form-control" type="number" min="0" ng-model="cn.cantidad" ng-model-options="{updateOn: 'blur'}" required>
                            </div>
                            <div class="col-6">
                                 <label>Precio</label>
                                 <input  name="precio" class="form-control" type="number" min="0" ng-model="cn.precio" ng-model-options="{updateOn: 'blur'}" required>
                            </div>
                    </div>
                    <div class="row">
                            <div class="col-6">
                                <label>Categoria</label>
                                <input  name="categoria" class="form-control" type="text" ng-model="cn.categoria" ng-model-options="{updateOn: 'blur'}" required>
                            </div>
                        
                    </div>
                    <div><br></div>
                    <h3>Sección 2</h3>
                    <div class="row">
                        <div class="col-2">
                            <input class="btn btn-success" type="submit" ng-click="cn.guardarProducto()" value="Guardar Producto" ng-disabled=""/>
                        </div>
                        <div class="col-2">
                            <button class="btn btn-primary" ng-click="cn.listarProductos()">Listar Productos</button>
                        </div>
                        <div class="col-2">
                            <button class="btn btn-danger" ng-click="cn.eliminarProducto()">Eliminar Producto</button>
                        </div>                        
                         <div class="col-2">
                            <button class="btn btn-warning" ng-click="cn.actualizarProducto()">Actualizar Producto</button>
                        </div>
                    
                    </div>
                </div>
                    </div>
            </form>
            <br/>
            <div class="row">
                <div class="col-12 table-responsive-xl">
                    <h3>Sección 3</h3>
                    <table class="table table-striped table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th>Codigo</th>
                                <th>Nombre</th>
                                <th>Cantidad</th>
                                <th>Precio</th>
                                <th>Categoria</th>
                            </tr>
                        </thead>
                        <tr ng-repeat="producto in cn.productos">
                            <td>{{ producto.codigo }}</td>
                            <td>{{ producto.nombre }}</td>
                            <td>{{ producto.cantidad }}</td>
                            <td>{{ producto.precio }}</td>
                            <td>{{ producto.categoria }}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        
        <script src ="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <script>
        
    
        
        angular.module('TiendaVirtual',['ngRoute'])
                .controller('productosController',['$scope', function($scope){
                        $scope.user = {};
                        
                        $scope.update = function(){
                            console.log($scope.user);
                        };
                        
                        $scope.reset = function (form) {
                            $scope.user = {};
                            if (form) {
                                form.$setPristine();
                                form.$setUntouched();
                            }
                        };
                        $scope.reset();
                }]);
            
            var app = angular.module('TiendaVirtual', []);
            app.controller('productosController', ['$http', controladorProductos]);
        
        function validar(){
            return true;
        }
        
        function controladorProductos($http){
            var cn = this;
            
            cn.listarProductos = function (){
                var url = "peticiones.jsp";
                var params = {
                    proceso: "listarProductos"
                };
                $http({
                    method: 'POST',
                    url: 'peticiones.jsp',
                    params: params
                }).then(function(res){
                    cn.productos = res.data.Productos;
                });
            };
            
            cn.guardarProducto = function(){
                var producto = {
                    proceso: "guardarProducto",
                    codigo: cn.codigo,
                    nombre: cn.nombre,
                    cantidad: cn.cantidad,
                    precio: cn.precio,
                    categoria: cn.categoria
                };
                $http({
                    method: 'POST',
                    url: 'peticiones.jsp',
                    params: producto
                }).then(function(res){
                    if (res.data.ok === true){
                        if(res.data[producto.proceso] === true){
                            alert("Guardado con éxito.");
                        } else{
                            alert("Por favor verifique sus datos...");
                        }
                    } else {
                        alert(res.data.errorMsg);
                    }
                });
            };
            
            
            cn.eliminarProducto = function(){
                var producto = {
                    proceso: "eliminarProducto",
                    codigo: cn.codigo                    
                };
                $http({
                    method: 'POST',
                    url: 'peticiones.jsp',
                    params: producto
                }).then(function(res){
                    if (res.data.ok === true){
                        if(res.data[producto.proceso] === true){
                            alert("Eliminado con éxito.");
                        } else{
                            alert("Por favor verifique sus datos...");
                        }
                    } else {
                        alert(res.data.errorMsg);
                    }
                });
            };
            
            
            cn.actualizarProducto = function(){
                var producto = {
                    proceso: "actualizarProducto",
                    codigo: cn.codigo,
                    nombre: cn.nombre,
                    cantidad: cn.cantidad,
                    precio: cn.precio,
                    categoria: cn.categoria
                };
                $http({
                    method: 'POST',
                    url: 'peticiones.jsp',
                    params: producto
                }).then(function(res){
                    if (res.data.ok === true){
                        if(res.data[producto.proceso] === true){
                            alert("Producto modificado con éxito.");
                        } else{
                            alert("Por favor verifique sus datos...");
                        }
                    } else {
                        alert(res.data.errorMsg);
                    }
                });
            };
            
            
            
        }
        
       
    </script>
    </body>
    
</html>
