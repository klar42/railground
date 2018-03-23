var bms = bms || {};
var angular = angular || {};

(function() {
  bms = window.parent.bmsapi(window.frameElement.getAttribute('sessionId'), window.frameElement.getAttribute('viewId'));
  angular = window.parent.angular;
})();
