/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var input_search_1 = __webpack_require__(1);
	$(function () {
	    var id = "#input-search-box";
	    var inputSearch = new input_search_1.InputSearch(id, getMenuData);
	    function getMenuData() {
	    }
	});


/***/ }),
/* 1 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var MenuDataItem = (function () {
	    function MenuDataItem() {
	    }
	    return MenuDataItem;
	}());
	exports.MenuDataItem = MenuDataItem;
	var InputSearch = (function () {
	    function InputSearch(inputSearchId, getMenuData) {
	        this.inputSearchItem = $(inputSearchId);
	        this.input = this.inputSearchItem.find('input');
	        this.menuList = this.inputSearchItem.find("ul");
	        this.init(getMenuData);
	    }
	    InputSearch.prototype.init = function (getMenuData) {
	        var self = this;
	        this.inputSearchItem.find('input').change(function () {
	            var val = $(this).val();
	            var menuData = [];
	            if (!getMenuData) {
	                console.log("getMenuData is empty");
	            }
	            else {
	                menuData = getMenuData(val);
	            }
	            self.menuList.html("");
	            menuData.forEach(function (item) {
	                self.menuList.append();
	            });
	            self.menuList.show();
	        });
	    };
	    return InputSearch;
	}());
	exports.InputSearch = InputSearch;
	exports.default = InputSearch;


/***/ })
/******/ ]);
//# sourceMappingURL=input-search-pack.js.map