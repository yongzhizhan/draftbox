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
	var tab_1 = __webpack_require__(2);
	$(function () {
	    var list = $("#testTab").children('a');
	    console.log(list);
	    list.each(function () {
	        console.log($(this));
	    });
	    var tab = new tab_1.default("#testTab");
	});


/***/ }),
/* 1 */,
/* 2 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var Tab = (function () {
	    function Tab(tabId) {
	        this.tab = $(tabId);
	        this.init();
	    }
	    Tab.prototype.init = function () {
	        var self = this;
	        var index = 0;
	        this.tab.children("a").each(function () {
	            var tabItem = $(this);
	            tabItem.attr("tab-index", index);
	            if (0 != index) {
	                var curContentId = tabItem.attr("href");
	                $(curContentId).hide();
	                console.log("hide id:" + curContentId);
	            }
	            index++;
	            tabItem.on('click', function () {
	                var contentId = tabItem.attr("href");
	                var index = tabItem.attr("tab-index");
	                self.showContent(index, contentId);
	            });
	        });
	    };
	    Tab.prototype.showContent = function (index, contentId) {
	        this.tab.children("a").each(function () {
	            var tabItem = $(this);
	            var curIndex = tabItem.attr("tab-index");
	            if (curIndex == index)
	                return;
	            var curContentId = tabItem.attr("href");
	            $(curContentId).hide();
	        });
	        var contentObj = $(contentId);
	        contentObj.show();
	    };
	    return Tab;
	}());
	exports.default = Tab;


/***/ })
/******/ ]);
//# sourceMappingURL=tab-pack.js.map