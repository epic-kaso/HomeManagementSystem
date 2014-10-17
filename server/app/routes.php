<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the Closure to execute when that URI is requested.
|
*/

Route::get('/', function()
{
	return View::make('hello');
});


Route::get('/units/messages/new',function(){
	return json_encode([
		"messageType" => 0,
		"message"	=> "Checking In"
	]);
});

Route::post('/units/messages/new',function(){

});

Route::any('/units/login',function(){
	return "xscmnldpmjenksp";
});